package com.fges.ckonsoru.data.psql;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import com.fges.ckonsoru.data.AvailabilityDAO;
import com.fges.ckonsoru.models.Availability;

public class BDDAvaibilityDAO implements AvailabilityDAO {

    Properties props;
    String[] jours = {"lundi", "mardi", "mercredi", "jeudi", "vendredi", "samedi", "dimannche"};

    public BDDAvaibilityDAO(Properties props){
        this.props = props;
    }

    private Connection connectDb(){
        Connection db = null;
        try {
            db = DriverManager.getConnection(this.props.getProperty("bdd.url"), this.props.getProperty("bdd.login"), this.props.getProperty("bdd.mdp"));
        }
        catch(SQLException error){
            System.out.println(error.getMessage());
        }
        return db;
    }

    /**
     * Fetch Availabilities from the database for a given day
     * @param day day from which we want the availabilities
     * @return a collection of Availabilities representing the fetched data
     */
    @Override
    public Collection<Availability> getAvailabilityByDay(DayOfWeek day) {
        Connection db = this.connectDb();

        if(db == null){
            return new ArrayList<>();
        }

        ArrayList<Availability> availabilities = new ArrayList<>();
        Availability avRes;

        try {
            PreparedStatement stmt = db.prepareStatement("SELECT * FROM disponibilite INNER JOIN veterinaire ON veterinaire.vet_id = disponibilite.vet_id INNER JOIN jour ON disponibilite.dis_jour = jour.jou_id WHERE jou_libelle = ?");
            stmt.setString(1, this.jours[day.getValue() - 1]);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){

                avRes = new Availability(rs.getObject("dis_debut", LocalTime.class), rs.getObject("dis_fin", LocalTime.class), rs.getString("vet_nom"));
                availabilities.add(avRes);
            }
            rs.close();
            stmt.close();
        }
        catch (SQLException error){
            System.out.println("avaibality.getAvailabilityByDay :");
            System.out.println(error.getMessage());
            return new ArrayList<>();
        }


        return availabilities;
    }

    /**
     * Fetch the database to know if the veterinary is available at the given time
     * @param datetime time to look for
     * @param veterinaryName veterinary name
     * @return true if the veterinary is available
     */
    @Override
    public boolean isAvailable(LocalDateTime datetime, String veterinaryName) {

        Connection db = this.connectDb();

        if(db == null){
            return false;
        }

        int count = 0;

        try {
            PreparedStatement stmt = db.prepareStatement("SELECT COUNT(*) AS rowcount FROM disponibilite d WHERE d.dis_debut <= ? AND d.dis_fin >= ?  AND d.vet_id = (SELECT vet_id FROM veterinaire v WHERE v.vet_nom = ?) AND d.dis_jour = (SELECT jou_id FROM jour j WHERE j.jou_libelle = ?)");
            Time sqlTime = Time.valueOf(datetime.toLocalTime());
            DayOfWeek day = datetime.getDayOfWeek();
            String sqlDay = this.jours[day.getValue() - 1];

            // set all the parameters
            stmt.setTime(1, sqlTime);
            stmt.setTime(2, sqlTime);
            stmt.setString(3, veterinaryName);
            stmt.setString(4, sqlDay);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                count = rs.getInt("rowcount");
            }

            rs.close();
            stmt.close();
        }
        catch (SQLException error){
            System.out.println("avaibility.isAvailable :");
            System.out.println(error.getMessage());
            return false;
        }

        return count != 0;
    }
    
}
