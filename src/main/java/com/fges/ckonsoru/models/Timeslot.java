package com.fges.ckonsoru.models;

import java.time.LocalDateTime;

public class Timeslot {

    public LocalDateTime debut;
    public String veterinaryName;

    public Timeslot(LocalDateTime debut, String veterinaryName){
        this.debut = debut;
        this.veterinaryName = veterinaryName;
    }

}
