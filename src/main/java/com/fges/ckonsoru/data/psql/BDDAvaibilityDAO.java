package com.fges.ckonsoru.data.psql;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.Properties;

import com.fges.ckonsoru.data.AvailabilityDAO;
import com.fges.ckonsoru.models.Availability;

import javax.xml.transform.Result;

public class BDDAvaibilityDAO implements AvailabilityDAO {

    BDDAdapterSingleton adapterSingleton;

    public BDDAvaibilityDAO(BDDAdapterSingleton bddAdapterSingleton){
        this.adapterSingleton = bddAdapterSingleton;
    }


    /**
     * Fetch Availabilities from the database for a given day
     * @param day day from which we want the availabilities
     * @return a collection of Availabilities representing the fetched data
     */
    @Override
    public Collection<Availability> getAvailabilityByDay(DayOfWeek day) throws SQLException {


        ArrayList<Availability> availabilities = new ArrayList<>();
        Availability avRes;

        ArrayList<Object> params = new ArrayList<Object>();
        params.add(day.getDisplayName(TextStyle.FULL, Locale.FRANCE));

        ResultSet rs = this.adapterSingleton.find("SELECT * FROM disponibilite INNER JOIN veterinaire ON veterinaire.vet_id = disponibilite.vet_id INNER JOIN jour ON disponibilite.dis_jour = jour.jou_id WHERE jou_libelle = ?::varchar", params);

        while (rs.next()){

            avRes = new Availability(rs.getObject("dis_debut", LocalTime.class), rs.getObject("dis_fin", LocalTime.class), rs.getString("vet_nom"));
            availabilities.add(avRes);
        }
        rs.close();



        return availabilities;
    }

    /**
     * Fetch the database to know if the veterinary is available at the given time
     * @param datetime time to look for
     * @param veterinaryName veterinary name
     * @return true if the veterinary is available
     */
    @Override
    public boolean isAvailable(LocalDateTime datetime, String veterinaryName) throws SQLException {

        ArrayList<Object> params = new ArrayList<Object>();
        params.add(datetime.toLocalTime());
        params.add(datetime.toLocalTime());
        params.add(veterinaryName);
        params.add(datetime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.FRANCE));

        int count = 0;

        ResultSet rs = this.adapterSingleton.find("SELECT COUNT(*) AS rowcount FROM disponibilite d WHERE d.dis_debut <= ?::time AND d.dis_fin >= ?::time" +
                "  AND d.vet_id = (SELECT vet_id FROM veterinaire v WHERE v.vet_nom = ?::varchar) AND d.dis_jour = (SELECT jou_id FROM jour j WHERE j.jou_libelle = ?::varchar)", params);

        if(rs.next()){
            count = rs.getInt("rowcount");
        }

        rs.close();


        return count != 0;
    }
    
}
