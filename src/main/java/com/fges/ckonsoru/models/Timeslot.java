package com.fges.ckonsoru.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Timeslot {

    private LocalDateTime debut;
    private String veterinaryName;

    public Timeslot(LocalDateTime debut, String veterinaryName){
        this.debut = debut;
        this.veterinaryName = veterinaryName;
    }

    public LocalDateTime getDebut() {
        return debut;
    }

    public String getVeterinaryName() {
        return veterinaryName;
    }

    @Override
    public String toString(){
        DateTimeFormatter timeFormatter =
                DateTimeFormatter.ofPattern("dd/MM/yyyy " +
                        "HH:mm");
        return this.veterinaryName + " : " + this.debut.format(timeFormatter);
    }

}
