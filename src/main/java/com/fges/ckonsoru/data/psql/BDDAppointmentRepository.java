package com.fges.ckonsoru.data.psql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import com.fges.ckonsoru.data.AppointmentRepository;
import com.fges.ckonsoru.models.Appointment;

public class BDDAppointmentRepository extends AppointmentRepository  {

    Connection db;

    public BDDAppointmentRepository(String url, String username, String password){
        try {
            this.db = DriverManager.getConnection(url, username, password);
        }
        catch(SQLException error){
            System.out.println(error.getMessage());
        }
    }

    /**
     * Get the list of appointments on the given date from the database
     * @param date LocalDate from which we wan the results
     * @return A collection of the fetched appointments
     */
    public Collection<Appointment> getAllAppointmentsByDate(LocalDate date){
        return new ArrayList<Appointment>();
    }

    /**
     * Create an appointment in the database
     * @param appointment appointment to create
     * @return true if the operation succeeds
     */
    public boolean registerAppointment(Appointment appointment){
        return false;
    }

    /**
     * Remove an appointment from the database by a {@link LocalDateTime} and a client name
     * @param datetime datetime of the appointment
     * @param clientName client of the appointment
     * @return true if the operation succeeds
     */
    public boolean removeAppointment(LocalDateTime datetime, String clientName){
        return false;
    }

    /**
     * Fetch the list of appointments of the given client
     * @param clientName name of the client
     * @return A {@link Collection} of {@link Appointment}
     */
    public Collection<Appointment> getAllAppointmentsByClient(String clientName){
        return new ArrayList<Appointment>();
    }

    /**
     * Fetch the database to know if the doctor has an appointment at the given time
     * @param datetime time of the appointment
     * @param doctorName name of the doctor
     * @return true if the doctor has no appointment
     */
    public boolean isFree(LocalDateTime datetime, String doctorName){
        return false;
    }

}