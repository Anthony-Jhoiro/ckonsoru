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

    BDDAdapterSingleton adapterSingleton;

    /**
     * Set the object with the props property
     * @param bddAdapterSingleton adapter to make requests in the database
     */
    public BDDAppointmentDAO(BDDAdapterSingleton bddAdapterSingleton){
        this.adapterSingleton = bddAdapterSingleton;
    }

    /**
     * Get the list of appointments on the given date from the database
     * @param date LocalDate from which we wan the results
     * @return A collection of the fetched appointments
     */
    public Collection<Appointment> getAllAppointmentsByDate(LocalDate date){

        ArrayList<Appointment> appointments = new ArrayList<>();
        Appointment appRes;

        ArrayList<Object> params = new ArrayList<Object>();
        params.add(date);

        try {
            ResultSet rs = this.adapterSingleton.find("SELECT * FROM rendezvous WHERE rv_debut >= ?::date AND rv_debut < (?::date + '1 day'::interval)", params);
            while (rs.next()){
                appRes = new Appointment(rs.getObject("rv_debut", LocalDateTime.class), rs.getString("rv_client"), rs.getString("vet_nom"));
                appointments.add(appRes);
            }
            rs.close();
        }
        catch (SQLException error){
            // TODO : no print inside service
            System.out.println("appointment.getAllAppointmentByDate :");
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

        ArrayList<Object> params = new ArrayList<Object>();
        params.add(appointment.getVeterinaryName());
        params.add(appointment.getBeginDateTime());
        params.add(appointment.getClientName());

        try {
            return this.adapterSingleton.update("INSERT INTO rendezvous (vet_id, rv_debut, rv_client) VALUES((SELECT vet_id FROM veterinaire WHERE vet_nom = ?::varchar), ?::timestamp, ?::varchar)", params);
        }
        catch (SQLException error){
            // TODO : no print inside service
            System.out.println("appointment.registerAppointment :");
            System.out.println(error.getMessage());
            return false;
        }

    }

    /**
     * Remove an appointment from the database by a {@link LocalDateTime} and a client name
     * @param datetime datetime of the appointment
     * @param clientName client of the appointment
     * @return true if the operation succeeds
     */
    public boolean removeAppointment(LocalDateTime datetime, String clientName){

        ArrayList<Object> params = new ArrayList<Object>();
        params.add(datetime);
        params.add(clientName);

        try {
            return this.adapterSingleton.update("DELETE FROM rendezvous r WHERE r.rv_debut = ?::timestamp AND r.rv_client = ?::varchar", params);
        }
        catch (SQLException error){
            // TODO : no print inside service
            System.out.println("appointment.removeAppointment :");
            System.out.println(error.getMessage());
            return false;
        }
    }

    /**
     * Fetch the list of appointments of the given client
     * @param clientName name of the client
     * @return A {@link Collection} of {@link Appointment}
     */
    public Collection<Appointment> getAllAppointmentsByClient(String clientName){
        ArrayList<Appointment> appointments = new ArrayList<>();
        Appointment appRes;

        ArrayList<Object> params = new ArrayList<Object>();
        params.add(clientName);

        try {
            ResultSet rs = this.adapterSingleton.find("SELECT * FROM rendezvous r INNER JOIN veterinaire v ON v.vet_id = r.vet_id WHERE r.rv_client = ?::varchar;", params);

            while (rs.next()){
                appRes = new Appointment(rs.getObject("rv_debut", LocalDateTime.class), rs.getString("rv_client"), rs.getString("vet_nom"));
                appointments.add(appRes);
            }
            rs.close();
        }
        catch (SQLException error){
            // TODO : no print inside service
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

        int count = 1;

        ArrayList<Object> params = new ArrayList<Object>();
        params.add(datetime);
        params.add(doctorName);

        try {
            ResultSet rs = this.adapterSingleton.find("SELECT COUNT(*) AS rowcount  FROM rendezvous r WHERE rv_debut = ?::timestamp AND r.vet_id = (SELECT vet_id FROM veterinaire v WHERE v.vet_nom = ?::varchar)", params);

            if(rs.next()){
                count = rs.getInt("rowcount");
            }

            rs.close();
        }
        catch (SQLException error){
            // TODO : no print inside service
            System.out.println("appointment.isFree :");
            System.out.println(error.getMessage());
            return false;
        }

        return count == 0;
    }

}