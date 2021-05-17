package com.fges.ckonsoru.models;

import java.time.LocalDateTime;

public class Cancellation {
    private String client;
    private LocalDateTime timeslot;
    private String veterinary;
    private long delay;

    public Cancellation(String client, LocalDateTime timeslot, String veterinary, long delay) {
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

    public long getDelay() {
        return delay;
    }
}
