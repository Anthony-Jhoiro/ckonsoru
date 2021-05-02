package com.fges.ckonsoru.models;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Availability {
    protected LocalTime begin;
    protected LocalTime end;
    protected String veterinaryName;

    public Availability(LocalTime begin, LocalTime end, String veterinaryName) {
        this.begin = begin;
        this.end = end;
        this.veterinaryName = veterinaryName;
    }

    public LocalTime getBegin() {
        return begin;
    }

    public LocalTime getEnd() {
        return end;
    }

    public String getVeterinaryName() {
        return veterinaryName;
    }
}
