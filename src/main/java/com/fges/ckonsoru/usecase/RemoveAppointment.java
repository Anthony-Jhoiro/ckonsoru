package com.fges.ckonsoru.usecase;

import com.fges.ckonsoru.data.AppointmentDAO;
import com.fges.ckonsoru.data.CancellationDAO;
import com.fges.ckonsoru.data.WaitingLineDAO;
import com.fges.ckonsoru.events.Observable;
import com.fges.ckonsoru.models.Appointment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class RemoveAppointment extends Observable<Appointment> implements UseCase {

    protected CancellationDAO cancellationDAO;
    protected WaitingLineDAO waitingLineDAO;
    protected AppointmentDAO appointmentDAO;

    public RemoveAppointment(AppointmentDAO appointmentDAO, CancellationDAO cancellationDAO, WaitingLineDAO waitingLineDAO) {
        this.appointmentDAO = appointmentDAO;
        this.cancellationDAO = cancellationDAO;
        this.waitingLineDAO = waitingLineDAO;
    }


    @Override
    public String getChoice() {
        return "Supprimer un rendez-vous";
    }

    @Override
    public void trigger() {
        System.out.println("Suppression du rendez-vous");

        System.out.println("Indiquer une date et heure de début au format JJ/MM/AAAA HH:MM (ex: 18/03/2021 15:00)");

        // ask appointment datetime
        Scanner answer = new Scanner(System.in);
        String timeString = answer.nextLine();
        // check timeString

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("d/MM/yyyy H:m");
        LocalDateTime date = LocalDateTime.parse(timeString, timeFormatter);

        // ask client name
        System.out.println("Indiquer le nom du client");
        String clientName = answer.nextLine();

        Appointment appointmentToRemove = appointmentDAO.getAppointmentByClientNameAndDate(clientName, date);

        // Stop the function if no appointment has been found
        if (appointmentToRemove == null) {
            System.out.println("Aucun rendez-vous n'a été trouvé.");
            return;
        }

        // remove the appointment
        if (this.appointmentDAO.removeAppointment(appointmentToRemove.getBeginDateTime(), appointmentToRemove.getClientName())) {
            System.out.println("Un rendez-vous pour " + clientName + " le  " + timeString + " a été supprimé");


            this.emit(appointmentToRemove);

        } else {
            System.out.println("Une erreur est " +
                    "survenue pendant la suppression " +
                    "du rendez-vous");
        }
    }
}
