package com.fges.ckonsoru.usecase;

import com.fges.ckonsoru.data.AppointmentDAO;
import com.fges.ckonsoru.data.CancellationDAO;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class RemoveAppointmentV2 extends RemoveAppointment{

    CancellationDAO cancellationDAO;

    public RemoveAppointmentV2(AppointmentDAO appointmentDAO, CancellationDAO cancellationDAO) {
        super(appointmentDAO);
        this.cancellationDAO = cancellationDAO;
    }

    @Override
    public void trigger() {
        System.out.println("Suppression du rendez-vous");

        System.out.println("Indiquer une date et heure de début au format JJ/MM/AAAA HH:MM (ex: 18/03/2021 15:00)");

        // ask appointment datetime
        Scanner answer = new Scanner(System.in);
        String timeString = answer.nextLine();
        // check timeString
        try {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("d/MM/yyyy H:m");
            LocalDateTime date = LocalDateTime.parse(timeString, timeFormatter);

            // ask client name
            System.out.println("Indiquer le nom du client");
            String clientName = answer.nextLine();

            // remove the appointment
            if (this.appointmentDAO.removeAppointment(date, clientName)) {
                System.out.println("Un rendez-vous pour "+ clientName +" le  "+ timeString +" a été supprimé");

                // if it is removed 24h before or less,
                // we note the cancellation
                if(Duration.between(LocalDateTime.now(),
                        date).toSeconds() < 24 * 3600){
                    try {
                        if(this.cancellationDAO.setCancellation(date, clientName)){
                            System.out.println("l'annulation " +
                                    "ayant été effectuée " +
                                    "moins de 24h avant le " +
                                    "rendez-vous, nous avons" +
                                    " tracé cette annulation");
                        }
                        else {
                            System.out.println("Une erreur " +
                                    "est survenue lors du " +
                                    "traçage de l'annulation");
                        };
                    }
                    catch(SQLException error) {
                        System.out.println("Une erreur " +
                                "est survenue lors du " +
                                "traçage de l'annulation");
                    }
                }

            } else {
                System.out.println("Une erreur est " +
                        "survenue pendant la suppression " +
                        "du rendez-vous");
            }
        }
        catch(Exception e){
            System.out.println("problème de parsing de la date");
        }
    }
}
