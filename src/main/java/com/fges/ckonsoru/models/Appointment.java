package com.fges.ckonsoru.models;

/**
 * This class represents an Appointment
 * @author anthony
 */
public class Appointment {
    /**
     * Timeslot of the appointment
     */
    protected TimeSlot timeslot;

    /**
     * Client that reserved the appointment
     */
    protected Client client;

    /**
     * Create an appointment from a timeslot and a client
     * @param timeslot Timeslot of the appointment
     * @param client Client that reserved the appointment
     */
    public Appointment(TimeSlot timeslot, Client client) {
        this.timeslot = timeslot;
        this.client = client;
    }
}
