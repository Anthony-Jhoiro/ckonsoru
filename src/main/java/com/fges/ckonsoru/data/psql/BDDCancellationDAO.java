package com.fges.ckonsoru.data.psql;

import com.fges.ckonsoru.data.CancellationDAO;
import com.fges.ckonsoru.models.Cancellation;

import java.sql.ResultSet;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;

public class BDDCancellationDAO implements CancellationDAO {

    BDDAdapterSingleton bddAdapterSingleton;

    public BDDCancellationDAO() {
        this.bddAdapterSingleton =
                BDDAdapterSingleton.getInstance();
    }


    /**
     * get all the cancellations
     *
     * @return the list of all {@link Cancellation}
     * @throws Exception
     */
    @Override
    public Collection<Cancellation> getAnnulations() throws Exception {

        ArrayList<Cancellation> cancels = new ArrayList<>();
        Cancellation resCancel;

        ResultSet rs = this.bddAdapterSingleton.find(
                "SELECT ann_client, ann_creneau, vet_nom, ann_delai " +
                "FROM annulation " +
                "LEFT JOIN veterinaire " +
                "ON veterinaire.vet_id = annulation.vet_id", new ArrayList<>());

        while(rs.next()){
            resCancel = new Cancellation(
                    rs.getString("ann_client"),
                    rs.getObject("ann_creneau",
                            LocalDateTime.class),
                    rs.getString("vet_nom"),
                    rs.getObject("ann_delai",
                            LocalTime.class)
            );
            cancels.add(resCancel);
        }

        return cancels;

    }
    /**
     * note a cancellation
     *
     * @param date date of the appointment
     * @param clientName client who's removing
     * @return true if the cancellation worked, false
     * otherwise
     * @throws Exception
     */
    @Override
    public boolean setCancellation(LocalDateTime date, String clientName) throws Exception {

        Duration delay =
                Duration.between(LocalDateTime.now(), date);

        ArrayList<Object> params = new ArrayList<>();
        params.add(clientName);
        params.add(date);
        params.add(date);
        params.add(clientName);
        params.add(LocalTime.of(
                       (int) delay.toHours(),
                (int) delay.toMinutes() % 60,
                (int) delay.toSeconds() % 60));

        return this.bddAdapterSingleton.update(
                "INSERT INTO annulation (ann_client, " +
                        "ann_creneau, vet_id, ann_delai) " +
                        "VALUES (?::varchar, ?::timestamp" +
                        ", (SELECT vet_id FROM " +
                        "veterinaire WHERE vet_nom = " +
                        "(SELECT vet_nom FROM rendezvous " +
                        "r WHERE r.rv_debut = " +
                        "?::timestamp AND r.rv_client = ?::varchar)) ,?::time);", params);


    }
}
