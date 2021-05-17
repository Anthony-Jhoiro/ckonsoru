package com.fges.ckonsoru.models;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Cancellation {
    private String client;
    private LocalDateTime timeslot;
    private String veterinary;
    private LocalTime delay;

    public Cancellation(String client, LocalDateTime timeslot, String veterinary, LocalTime delay) {
        this.client = client;
        this.timeslot = timeslot;
        this.veterinary = veterinary;
        this.delay = delay;
    }

    public String getClient() {
        return client;
    }

    public LocalDateTime getTimeslot() {
        return timeslot;
    }

    public String getVeterinary() {
        return veterinary;
    }

    public LocalTime getDelay() {
        return delay;
    }

    @Override
    public String toString(){
        DateTimeFormatter dateFormatter =
                DateTimeFormatter.ofPattern("dd/MM/yyyy " +
                        "HH:mm");
        DateTimeFormatter delayFormatter =
                DateTimeFormatter.ofPattern("HH:mm:ss");
        return this.client + " le " + this.timeslot.format(dateFormatter) + " (" + this.delay.format(delayFormatter) + " avant)";
    }
}
