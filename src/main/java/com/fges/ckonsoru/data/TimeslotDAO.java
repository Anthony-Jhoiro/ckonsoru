package com.fges.ckonsoru.data;

import com.fges.ckonsoru.models.Timeslot;

import java.time.LocalDate;
import java.util.Collection;

public interface TimeslotDAO {

    Collection<Timeslot> getFreeTimeslots(LocalDate date);

}
