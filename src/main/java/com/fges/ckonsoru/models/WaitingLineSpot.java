package com.fges.ckonsoru.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class WaitingLineSpot {
    private String clientName;
    private String numTel;
    private LocalDate deadline;
    private Object proposedTimeslot; // TODO : type
    private LocalDateTime requestedDate;

    public WaitingLineSpot(String clientName, String numTel, LocalDate deadline, LocalDateTime requestedDate) {
        this.clientName = clientName;
        this.numTel = numTel;
        this.deadline = deadline;
        this.requestedDate = requestedDate;
    }

    public WaitingLineSpot(String clientName, String numTel, LocalDate deadline, Object proposedTimeslot, LocalDateTime requestedDate) {
        this.clientName = clientName;
        this.numTel = numTel;
        this.deadline = deadline;
        this.proposedTimeslot = proposedTimeslot;
        this.requestedDate = requestedDate;
    }

    public String getClientName() {
        return clientName;
    }

    public String getNumTel() {
        return numTel;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public Object getProposedTimeslot() {
        return proposedTimeslot;
    }

    public LocalDateTime getRequestedDate() {
        return requestedDate;
    }
}
