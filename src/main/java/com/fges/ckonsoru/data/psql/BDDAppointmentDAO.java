package com.fges.ckonsoru.data.psql;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import com.fges.ckonsoru.data.AppointmentDAO;
import com.fges.ckonsoru.models.Appointment;

public class BDDAppointmentDAO implements AppointmentDAO {

    Properties props;

    /**
     * Set the object with the props property
     * @param props properties of the app to get bdd credentials
     */
    public BDDAppointmentDAO(Properties props){
        this.props = props;
    }

    /**
     * create a connetion between the app ans the bdd
     * @return Connection : a connection object to interact with the app
     */
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
     * Get the list of appointments on the given date from the database
     * @param date LocalDate from which we wan the results
     * @return A collection of the fetched appointments
     */
    public Collection<Appointment> getAllAppointmentsByDate(LocalDate date){

        Connection db = this.connectDb();

        if(db == null){
            return new ArrayList<>();
        }

        ArrayList<Appointment> appointments = new ArrayList<>();
        Appointment appRes;

        try {
            PreparedStatement stmt = db.prepareStatement("SELECT * FROM rendezvous WHERE rv_debut >= ?::date AND rv_debut < (?::date + '1 day'::interval)");
            Date sqlDate = Date.valueOf(date);
            stmt.setDate(1, sqlDate);
            stmt.setDate(2, sqlDate);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                appRes = new Appointment(rs.getObject("rv_debut", LocalDateTime.class), rs.getString("rv_client"), rs.getString("vet_nom"));
                appointments.add(appRes);
            }
            rs.close();
            stmt.close();
        }
        catch (SQLException error){
            System.out.println("appointment repo :");
            System.out.println(error.getMessage());
            return new ArrayList<>();
        }


        return appointments;
    }

    /**
     * Create an appointment in the database
     * @param appointment appointment to create
     * @return true if the operation succeeds
     */
    public boolean registerAppointment(Appointment appointment){
        Connection db = this.connectDb();

        if(db == null){
            return false;
        }

        int count;

        try {
            PreparedStatement stmt = db.prepareStatement("INSERT INTO rendezvous (vet_id, rv_debut, rv_client) VALUES((SELECT vet_id FROM veterinaire WHERE vet_nom = ?), ?, ?)");
            Timestamp sqlTime = Timestamp.valueOf(appointment.getBeginDateTime());
            stmt.setString(1, appointment.getVeterinaryName());
            stmt.setTimestamp(2, sqlTime);
            stmt.setString(3, appointment.getClientName());

            count = stmt.executeUpdate();


            stmt.close();
        }
        catch (SQLException error){
            System.out.println("appointment.registerAppointment :");
            System.out.println(error.getMessage());
            return false;
        }

        return count > 0;

    }

    /**
     * Remove an appointment from the database by a {@link LocalDateTime} and a client name
     * @param datetime datetime of the appointment
     * @param clientName client of the appointment
     * @return true if the operation succeeds
     */
    public boolean removeAppointment(LocalDateTime datetime, String clientName){
        Connection db = this.connectDb();

        if(db == null){
            return false;
        }

        int count;

        try {
            PreparedStatement stmt = db.prepareStatement("DELETE FROM rendezvous r WHERE r.rv_debut = ? AND r.rv_client = ?");

            Timestamp sqlTime = Timestamp.valueOf(datetime);
            stmt.setTimestamp(1, sqlTime);
            stmt.setString(2, clientName);

            count = stmt.executeUpdate();


            stmt.close();
        }
        catch (SQLException error){
            System.out.println("appointment.removeAppointment :");
            System.out.println(error.getMessage());
            return false;
        }

        return count > 0;
    }

    /**
     * Fetch the list of appointments of the given client
     * @param clientName name of the client
     * @return A {@link Collection} of {@link Appointment}
     */
    public Collection<Appointment> getAllAppointmentsByClient(String clientName){
        Connection db = this.connectDb();

        if(db == null){
            return new ArrayList<>();
        }

        ArrayList<Appointment> appointments = new ArrayList<>();
        Appointment appRes;

        try {
            PreparedStatement stmt = db.prepareStatement("SELECT * FROM rendezvous r INNER JOIN veterinaire v ON v.vet_id = r.vet_id WHERE r.rv_client = ?;");

            stmt.setString(1, clientName);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                appRes = new Appointment(rs.getObject("rv_debut", LocalDateTime.class), rs.getString("rv_client"), rs.getString("vet_nom"));
                appointments.add(appRes);
            }
            rs.close();
            stmt.close();
        }
        catch (SQLException error){
            System.out.println("appointment.getAllAppointmentsByClient :");
            System.out.println(error.getMessage());
            return new ArrayList<>();
        }


        return appointments;
    }

    /**
     * Fetch the database to know if the doctor has an appointment at the given time
     * @param datetime time of the appointment
     * @param doctorName name of the doctor
     * @return true if the doctor has no appointment
     */
    public boolean isFree(LocalDateTime datetime, String doctorName){
        Connection db = this.connectDb();

        if(db == null){
            return false;
        }

        int count = 1;

        try {
            PreparedStatement stmt = db.prepareStatement("SELECT COUNT(*) AS rowcount  FROM rendezvous r WHERE rv_debut = ? AND r.vet_id = (SELECT vet_id FROM veterinaire v WHERE v.vet_nom = ?)");
            Timestamp Timestamp = java.sql.Timestamp.valueOf(datetime);

            // set all the parameters
            stmt.setTimestamp(1, Timestamp);
            stmt.setString(2, doctorName);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                count = rs.getInt("rowcount");
            }

            rs.close();
            stmt.close();
        }
        catch (SQLException error){
            System.out.println("appointment.isFree :");
            System.out.println(error.getMessage());
            return false;
        }

        return count == 0;
    }

}