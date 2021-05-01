package com.fges.ckonsoru.data;

import java.time.LocalDate;
import java.util.Collection;

public abstract class AppointmentRepository{
    
    public abstract Collection<String> getAllAppointmentsByDate(LocalDate date);

    public abstract Boolean registerAppointment(LocalDate date, String doctor, String client);

    public abstract Boolean removeAppointment(LocalDate date, String client); 

}

