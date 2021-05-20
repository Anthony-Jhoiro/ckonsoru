package com.fges.ckonsoru.usecase;

import com.fges.ckonsoru.data.AppointmentDAO;
import com.fges.ckonsoru.data.AvailabilityDAO;
import com.fges.ckonsoru.models.Appointment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class TakeAppointment implements UseCase {

    protected AppointmentDAO appointmentDAO;

    protected AvailabilityDAO availabilityDAO;

    public TakeAppointment(AvailabilityDAO availabilityDAO, AppointmentDAO appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
        this.availabilityDAO = availabilityDAO;
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
        try {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("d/MM/yyyy H:m");
            LocalDateTime date = LocalDateTime.parse(timeString, timeFormatter);

            System.out.println("Indiquer le nom du vétérinaire");
            String veterinaryName = answer.nextLine();
            // check veterinaryName

            System.out.println("Indiquer le nom du client");
            String clientName = answer.nextLine();
            // check clientName


            // check available
            boolean available = availabilityDAO.isAvailable(date, veterinaryName);
            if (!available) {
                System.out.println("The veterinary is not available or does not exists");
                return;
            }

            // Check free
            boolean free = this.appointmentDAO.isFree(date, veterinaryName);
            if (!free) {
                System.out.println("The timeslot is taken.");
                return;
            }

            Appointment appointment = new Appointment(date, clientName, veterinaryName);
            if(this.appointmentDAO.registerAppointment(appointment)){
                System.out.println("Un rendez-vous pour " + clientName + " avec " + veterinaryName + " a été reservé le " + appointment.toStringDateFormat());
            }
        }
        catch(Exception e){
            System.out.println("problème de parsing de la date :");
            System.out.println(e.getMessage());
        }




    }
}
