package com.fges.ckonsoru.usecase;

import com.fges.ckonsoru.data.AppointmentDAO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class RemoveAppointment implements UseCase {
    protected AppointmentDAO appointmentDAO;

    public RemoveAppointment(AppointmentDAO appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
    }

    @Override
    public String getChoice() {
        return "Supprimer un rendez-vous";
    }

    @Override
    public void trigger() {
        System.out.println("Suppression du rendez-vous");

        System.out.println("Indiquer une date et heure de début au format JJ/MM/AAAA HH:MM (ex: 18/03/2021 15:00)");

        Scanner answer = new Scanner(System.in);
        String timeString = answer.nextLine();
        // check timeString
        try {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("d/MM/yyyy H:m");
            LocalDateTime date = LocalDateTime.parse(timeString, timeFormatter);

            System.out.println("Indiquer le nom du client");
            String clientName = answer.nextLine();

            if (this.appointmentDAO.removeAppointment(date, clientName)) {
                System.out.println("Un rendez-vous pour "+ clientName +" le  "+ timeString +" a été supprimé");
            } else {
                System.out.println("Une erreur est survenue pendant la supperssion du rendez-vous");
            }
        }
        catch(Exception e){
            System.out.println("problème de parsing de la date :");
            System.out.println(e.getMessage());
        }
    }
}
