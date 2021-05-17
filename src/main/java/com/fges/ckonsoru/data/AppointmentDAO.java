package com.fges.ckonsoru.data;

import com.fges.ckonsoru.models.Appointment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Represents a Repository to manage appointments
 */
public interface AppointmentDAO{

    /**
     * Get the list of appointments on the given date from the XML database
     * @param date LocalDate from which we wan the results
     * @return A collection of the fetched appointments
     */
    Collection<Appointment> getAllAppointmentsByDate(LocalDate date) throws Exception;

    /**
     * Create an appointment in the database
     * @param appointment appointment to create
     * @return true if the operation succeeds
     */
    boolean registerAppointment(Appointment appointment) throws Exception;

    /**
     * Remove an appointment from the database by a {@link LocalDateTime} and a client name
     * @param datetime datetime of the appointment
     * @param clientName client of the appointment
     * @return true if the operation succeeds
     */
    boolean removeAppointment(LocalDateTime datetime, String clientName) throws Exception;

    /**
     * Fetch the list of appointments of the given client
     * @param clientName name of the client
     * @return A {@link Collection} of {@link Appointment}
     */
    Collection<Appointment> getAllAppointmentsByClient(String clientName) throws Exception;

    /**
     * Fetch the database to know if the doctor has an appointment at the given time
     * @param datetime time of the appointment
     * @param doctorName name of the doctor
     * @return true if the doctor has no appointment
     */
    boolean isFree(LocalDateTime datetime, String doctorName) throws Exception;



}

