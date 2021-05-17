package com.fges.ckonsoru.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Timeslot {

    public LocalDateTime debut;
    public String veterinaryName;

    public Timeslot(LocalDateTime debut, String veterinaryName){
        this.debut = debut;
        this.veterinaryName = veterinaryName;
    }

    @Override
    public String toString(){
        DateTimeFormatter timeFormatter =
                DateTimeFormatter.ofPattern("dd/MM/yyyy " +
                        "HH:mm");
        return this.veterinaryName + " : " + this.debut.format(timeFormatter);
    }

}
