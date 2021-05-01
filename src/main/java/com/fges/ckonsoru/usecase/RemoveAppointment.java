package com.fges.ckonsoru.usecase;

import com.fges.ckonsoru.data.AppointmentRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class RemoveAppointment extends UseCase{
    protected AppointmentRepository appointmentRepository;

    public RemoveAppointment(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
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
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("d/MM/yyyy H:m");
        LocalDateTime date = LocalDateTime.parse(timeString, timeFormatter);

        System.out.println("Indiquer le nom du client");
        String clientName = answer.nextLine();

        if (this.appointmentRepository.removeAppointment(date, clientName)) {
            System.out.println("Un rendez-vous pour "+ clientName +" le  "+ timeString +" a été supprimé");
        } else {
            System.out.println("Une erreur est survenue pendant la supperssion du rendez-vous");
        }
    }
}
