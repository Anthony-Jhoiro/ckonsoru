package com.fges.ckonsoru.data.psql;

import com.fges.ckonsoru.data.TimeslotDAO;
import com.fges.ckonsoru.models.Timeslot;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

public class BDDTimeslotDAO implements TimeslotDAO {

    BDDAdapterSingleton adapterSingleton;

    public BDDTimeslotDAO(BDDAdapterSingleton bddAdapterSingleton) {
        this.adapterSingleton = bddAdapterSingleton;
    }

    /**
     * List all the free timeslots for a given date
     *
     * @param date : the date we want the free timeslots
     * @return a {@link Collection} of {@link Timeslot}
     */
    @Override
    public Collection<Timeslot> getFreeTimeslots(LocalDate date) {

        ArrayList<Object> params = new ArrayList<>();
        params.add(date);
        params.add(date);
        params.add(date);
        params.add(date);
        params.add(date);

        ArrayList<Timeslot> timeslots =
                new ArrayList<>();
        Timeslot timeRes;


        ResultSet rs;
        try {
            rs = this.adapterSingleton.find("WITH " +
                    "creneauxDisponibles AS (SELECT vet_nom, " +
                    "generate_series(?::date+dis_debut, " +
                    "?::date+dis_fin-'00:20:00'::time, '20 " +
                    "minutes'::interval) debut FROM " +
                    "disponibilite INNER JOIN veterinaire ON veterinaire.vet_id = disponibilite.vet_id WHERE dis_jour = EXTRACT('DOW' FROM ?::date) ORDER BY vet_nom, dis_id), creneauxReserves AS (SELECT vet_nom, rv_debut debut  FROM rendezvous INNER JOIN veterinaire ON veterinaire.vet_id = rendezvous.vet_id WHERE rv_debut BETWEEN ?::date AND ?::date +'23:59:59'::time), creneauxRestants AS (SELECT * FROM creneauxDisponibles EXCEPT SELECT * FROM creneauxReserves) SELECT * FROM creneauxRestants ORDER BY vet_nom, debut", params);

            while (rs.next()) {
                timeRes = new Timeslot(rs.getObject("debut",
                        LocalDateTime.class), rs.getString("vet_nom"));
                timeslots.add(timeRes);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return timeslots;
    }

}
