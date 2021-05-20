package com.fges.ckonsoru.usecase;

import com.fges.ckonsoru.data.AppointmentDAO;
import com.fges.ckonsoru.data.AvailabilityDAO;
import com.fges.ckonsoru.data.TimeslotDAO;
import com.fges.ckonsoru.models.Appointment;
import com.fges.ckonsoru.models.Availability;
import com.fges.ckonsoru.models.Timeslot;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class InitWeek implements UseCase {

    protected AppointmentDAO appointmentDAO;
    protected TimeslotDAO timeslotDAO;

    protected List<String> nomsClients =
            Arrays.asList("Ackerman", "Arlelt","Blouse","Braun","Hoover",
                    "Jager","Kirschtein","Lenz","Reiss","Springer","Zoe",
                    "Braus", "Smith","Bahner","Zacharias","Bossard","Langner");
    protected List<String> prenomsClients =
            Arrays.asList("A","B","C","D","E","F","G","H","I","J","K","L","M",
                    "N","O","P","Q","R","S","T","U","V","W","X","Y","Z");

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public InitWeek(AppointmentDAO appointmentDAO, TimeslotDAO timeslotDAO) {
        this.appointmentDAO = appointmentDAO;
        this.timeslotDAO = timeslotDAO;
    }

    @Override
    public String getChoice() {
        return "Initialiser une semaine complète [DEV]";
    }

    @Override
    public void trigger() {
        System.out.println("Remplissage automatique de semaine");
        System.out.println("Indiquer une date qui correspond à un lundi au format JJ/MM/AAAA (ex: 18/03/2021");
        Scanner scanner = new Scanner(System.in);
        String sDate = scanner.nextLine();
        LocalDate date = LocalDate.parse(sDate, dateFormatter);
        // remplissage de 7 jours consécutifs...
        for(int i=0;i<7;i++){
            try {
                Collection<Timeslot> dispos = timeslotDAO.getFreeTimeslots(date);
                // crée un rdv pour chaque dispo
                for (Timeslot dispo : dispos ){
                    Appointment rdv = new Appointment(dispo.getDebut(), genereClient(), dispo.getVeterinaryName());
                    appointmentDAO.registerAppointment(rdv);
                }
                date = date.plus(1, ChronoUnit.DAYS);
            } catch (SQLException e) {
                System.err.println("Database error");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private String genereClient(){
        String nom;
        nom = prenomsClients.get((int) (Math.random()*(prenomsClients.size()-1)));
        nom += ". " + nomsClients.get((int) (Math.random()*(nomsClients.size()-1)));
        return nom;
    }
}
