package com.fges.ckonsoru.usecase;

import com.fges.ckonsoru.data.AppointmentRepository;
import com.fges.ckonsoru.data.AvailabilityRepository;
import com.fges.ckonsoru.models.Appointment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class TakeAppointment extends UseCase {

    protected AppointmentRepository appointmentRepository;

    protected AvailabilityRepository availabilityRepository;

    public TakeAppointment(AvailabilityRepository availabilityRepository, AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
        this.availabilityRepository = availabilityRepository;
    }

    @Override
    public String getChoice() {
        return "Prendre un rendez-vous";
    }

    @Override
    public void trigger() {
        System.out.println("Prise de rendez-vous");
        System.out.println("Indiquer une date et heure de début au format JJ/MM/AAAA HH:MM (ex: 18/03/2021 15:00)");

        Scanner answer = new Scanner(System.in);
        String timeString = answer.nextLine();
        // check timeString
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("d/MM/yyyy H:m");
        LocalDateTime date = LocalDateTime.parse(timeString, timeFormatter);

        System.out.println("Indiquer le nom du vétérinaire");
        String veterinaryName = answer.nextLine();
        // check veterinaryName

        System.out.println("Indiquer le nom du client");
        String clientName = answer.nextLine();
        // check clientName


        // check available
        boolean available = availabilityRepository.isAvailable(date, veterinaryName);
        if (!available) {
            System.out.println("The veterinary is not available or does not exists");
            return;
        }

        // Check free
        boolean free = this.appointmentRepository.isFree(date, veterinaryName);
        if (!free) {
            System.out.println("The timeslot is taken.");
            return;
        }

        Appointment appointment = new Appointment(date, clientName, veterinaryName);
        this.appointmentRepository.registerAppointment(appointment);




    }
}
