package com.fges.ckonsoru.models;

import java.time.LocalDateTime;

public class Availability {
    LocalDateTime begin;
    LocalDateTime end;
    String veterinaryName;

    public Availability(LocalDateTime begin, LocalDateTime end, String veterinaryName) {
        this.begin = begin;
        this.end = end;
        this.veterinaryName = veterinaryName;
    }

    public LocalDateTime getBegin() {
        return begin;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public String getVeterinaryName() {
        return veterinaryName;
    }
}
