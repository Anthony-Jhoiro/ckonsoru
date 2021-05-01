package com.fges.ckonsoru.models;

import java.time.LocalDateTime;

public class Appointment {
    
    LocalDateTime beginDateTime;
    String clientName;
    String veterinaryName;

    public Appointment(LocalDateTime beginDateTime, String clientName, String veterinaryName) {
        this.beginDateTime = beginDateTime;
        this.clientName = clientName;
        this.veterinaryName = veterinaryName;
    }

    public LocalDateTime getBeginDateTime() {
        return beginDateTime;
    }

    public String getClientName() {
        return clientName;
    }

    public String getVeterinaryName() {
        return veterinaryName;
    }
}