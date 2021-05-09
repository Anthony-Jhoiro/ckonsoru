package com.fges.ckonsoru.usecase;

import com.fges.ckonsoru.data.AppointmentDAO;
import com.fges.ckonsoru.models.Appointment;

import java.util.Collection;
import java.util.Scanner;

public class ListAppointments extends UseCase {

    protected AppointmentDAO appointmentDAO;

    public ListAppointments(AppointmentDAO appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
    }

    @Override
    public String getChoice() {
        return "Lister les rendez-vous passés, présent et à venir d un client";
    }

    @Override
    public void trigger() {
        System.out.println("Indiquer le nom du client");

        Scanner answer = new Scanner(System.in);
        String clientName = answer.nextLine();
        // Check name with regex

        Collection<Appointment> appointments = this.appointmentDAO.getAllAppointmentsByClient(clientName);

        System.out.println(appointments.size() + " rendez-vous trouvé(s) pour " + clientName);

        for(Appointment appointment: appointments) {
            System.out.println(appointment.toStringForClient());
        }
    }
}
