package com.fges.ckonsoru.data.psql;

import com.fges.ckonsoru.data.WaitingLineDAO;
import com.fges.ckonsoru.models.WaitingLineSpot;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

public class BDDWaitingLineDAO implements WaitingLineDAO {


    BDDAdapterSingleton adapterSingleton;

    /**
     * Set the object with the props property
     *
     * @param bddAdapterSingleton adapter to make requests in the database
     */
    public BDDWaitingLineDAO(BDDAdapterSingleton bddAdapterSingleton) {
        this.adapterSingleton = bddAdapterSingleton;
    }


    @Override
    public boolean addToWaitingLine(WaitingLineSpot waitingLineSpot) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(waitingLineSpot.getClientName());
        params.add(waitingLineSpot.getNumTel());
        params.add(waitingLineSpot.getDeadline());
        params.add(waitingLineSpot.getRequestedDate());


        try {
            return this.adapterSingleton.update("INSERT INTO " +
                    "listeattente (la_client, la_numtel, " +
                    "la_dateauplustard, la_datedemande) " +
                    "VALUES (?::varchar, ?::varchar, " +
                    "?::timestamp, ?::timestamp)", params);
        } catch (SQLException throwables) {
            System.err.println("Database Error");
            return false;
        }
    }

    @Override
    public Collection<WaitingLineSpot> getWaitingLineSpots() {
        ArrayList<WaitingLineSpot> availabilities = new ArrayList<>();
        WaitingLineSpot waitingLineSpot;

        ResultSet rs;
        try {
            rs = this.adapterSingleton.find("SELECT la_id, la_client, la_numtel, la_dateauplustard,\n" +
                    "la_datedemande, la_creneaupropose, vet_nom\n" +
                    "FROM listeAttente\n" +
                    "LEFT JOIN disponibilite\n" +
                    "ON dis_jour = EXTRACT('DOW' FROM la_creneaupropose)\n" +
                    "LEFT JOIN veterinaire\n" +
                    "ON veterinaire.vet_id = disponibilite.vet_id\n" +
                    "AND NOT EXISTS(\n" +
                    "SELECT 1 FROM rendezvous\n" +
                    "WHERE vet_id = disponibilite.vet_id\n" +
                    "AND la_creneauPropose = rv_debut)\n" +
                    "WHERE la_creneaupropose is NULL\n" +
                    "OR vet_nom IS NOT NULL;", new ArrayList<>());

            while (rs.next()) {
                String client = rs.getString("la_client");
                String numTel = rs.getString("la_numtel");
                LocalDate deadline = rs.getObject("la_dateauplustard", LocalDate.class);
                LocalDateTime requestDate = rs.getObject("la_datedemande", LocalDateTime.class);
                LocalDateTime proposedDate = rs.getObject("la_creneaupropose", LocalDateTime.class);
                String vetName = rs.getString("vet_nom");

                waitingLineSpot = new WaitingLineSpot(client, numTel, deadline, proposedDate, requestDate, vetName);
                availabilities.add(waitingLineSpot);
            }
            rs.close();

        } catch (SQLException throwable) {
            System.err.println("Database Error");
        }


        return availabilities;
    }

    @Override
    public boolean updateWaitingLine(LocalDateTime localDateTime) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(localDateTime);

        // Get client id
        ResultSet rs;
        try {
            rs = this.adapterSingleton.find("SELECT" +
                    " listeAttente.la_id " +
                    "FROM listeAttente " +
                    "WHERE ?::date <= la_dateAuPlusTard " +
                    "AND la_creneauPropose IS NULL " +
                    "ORDER BY la_dateDemande ASC " +
                    "LIMIT 1;", params);


        if (rs.next()) {
            // A user has been found, update it
            String id = rs.getString("la_id");

            params.add(id);

            return this.adapterSingleton.update("UPDATE listeAttente\n" +
                    "\tSET la_creneauPropose = ?::timestamp\n" +
                    "WHERE listeAttente.la_id = " +
                    "?::integer", params);
        }
        } catch (SQLException throwables) {
            System.err.println("Database Error");
        }

        return false;

    }
}
