package com.fges.ckonsoru.usecase;

import com.fges.ckonsoru.data.AppointmentRepository;
import com.fges.ckonsoru.data.AvailabilityRepository;
import com.fges.ckonsoru.models.Appointment;
import com.fges.ckonsoru.models.Availability;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Scanner;

public class ListFreeTimeslotsByDate extends UseCase {
    protected AvailabilityRepository availabilityRepository;
    protected AppointmentRepository appointmentRepository;

    public ListFreeTimeslotsByDate(AvailabilityRepository availabilityRepository, AppointmentRepository appointmentRepository) {
        this.availabilityRepository = availabilityRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public String getChoice() {
        return "Afficher les créneaux disponibles pour une date donnée";
    }

    @Override
    public void trigger() {
        System.out.println("Entrer une date au format JJ/MM/AAAA (ex: 18/03/2021)");

        Scanner answer = new Scanner(System.in);
        String dateString = answer.nextLine();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        LocalDate date = LocalDate.parse(dateString, timeFormatter);

        DayOfWeek day = date.getDayOfWeek();

        Collection<Availability> availabilities = availabilityRepository.getAvailabilityByDay(day);

        Collection<Appointment> appointments = appointmentRepository.getAllAppointmentsByDate(date);

        for (Availability availability: availabilities) {
            LocalTime endTime = availability.getEnd();
            for (LocalTime time = availability.getBegin(); time.isBefore(endTime); time = time.plusMinutes(20)){
                boolean shouldDisplay = true;
                for (Appointment appointment: appointments) {
                    if (
                            appointment.getBeginDateTime().toLocalTime().equals(time)
                                    && appointment.getVeterinaryName().equals(availability.getVeterinaryName())
                    ) {
                        shouldDisplay = false;
                        break;
                    }
                }
                if (shouldDisplay) {
                    System.out.println(availability.getVeterinaryName() +
                            " : " +
                            dateString +
                            " " +
                            time.getHour() +
                            ":" +
                            time.getMinute());
                }

            }
        }

    }
}
