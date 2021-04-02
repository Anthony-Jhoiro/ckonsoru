package com.fges.ckonsoru.models;

import com.fges.ckonsoru.data.Id;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * This class represents an TimeSlot
 * @author anthony
 */
public class TimeSlot {

    protected UUID id;
    protected Date startDate;
    protected Date endDate;
    protected Doctor doctor;

    /**
     * Create a TimeSlot with a specific id
     * @param _startDate starting date
     * @param _endDate ending date
     * @param _doctor doctor for this timeslot
     * @param _id id of the timeslot
     */
    public TimeSlot(Date _startDate, Date _endDate, Doctor _doctor, UUID _id) {
        this.id = _id;
        this.startDate = _startDate;
        this.endDate = _endDate;
        this.doctor = _doctor;

    }

    /**
     * Create a TimeSlot and generate the id
     * @param _startDate starting date
     * @param _endDate ending date
     */
    public TimeSlot(Date _startDate, Date _endDate, Doctor doctor) {
        this(_startDate, _endDate, doctor, Id.makeId());
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return doctor.getName() + " : " + dateFormat.format(startDate);
    }
}
