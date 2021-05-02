package com.fges.ckonsoru.models;

import java.time.LocalDateTime;

public class Appointment {
    protected LocalDateTime beginDateTime;
    protected String clientName;
    protected String veterinaryName;

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

    public String toStringForClient(){
        return this.toStringDateFormat() + " avec " + this.veterinaryName;
    }

    public String toStringDateFormat(){
        return String.format("%02d",this.beginDateTime.getDayOfMonth()) + "/" + String.format("%02d", this.beginDateTime.getMonth().getValue()) + "/" + this.beginDateTime.getYear() + " " + String.format("%02d", this.beginDateTime.getHour()) + ":" + String.format("%02d", this.beginDateTime.getMinute());
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "beginDateTime=" + beginDateTime +
                ", clientName='" + clientName + '\'' +
                ", veterinaryName='" + veterinaryName + '\'' +
                '}'; // TODO string syntax
    }


}