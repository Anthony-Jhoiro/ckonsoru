package com.fges.ckonsoru.data;

import com.fges.ckonsoru.models.Appointment;
import com.fges.ckonsoru.models.Client;
import com.fges.ckonsoru.models.TimeSlot;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

/**
 * Repository is an abstract class that represents a Repository for data persistence manager.
 * @author anthony
 */
public abstract class Repository {
    /**
     * Fetch a list of available time slots on a specific date
     * @param date the date we want to check
     * @return the fetchedList
     */
    abstract Collection<TimeSlot> getAvailableTimeSlotsOnSpecificDate(Date date);

    /**
     * Fetch all the appointments taken by the given client
     * @param clientId is of the client
     * @return the list of appointment
     */
    abstract Collection<Appointment> getAppointmentsByClientId(UUID clientId);

    /**
     * Assign a time slot to the given client
     * @param timeSlot Time slot to assign
     * @param client Client that takes the time slot
     * @return The created Appointment
     */
    abstract Appointment assignTimeSlot(TimeSlot timeSlot, Client client);

    /**
     * Remove an appointment and free the timeslot
     * @param appointment Appointment to remove
     */
    abstract void removeAppointment(Appointment appointment);
}
