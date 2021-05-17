package com.fges.ckonsoru.usecase;

import com.fges.ckonsoru.data.AppointmentDAO;
import com.fges.ckonsoru.data.AvailabilityDAO;
import com.fges.ckonsoru.models.Appointment;
import com.fges.ckonsoru.models.Availability;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class InitWeek extends UseCase{

    protected AppointmentDAO appointmentDAO;
    protected AvailabilityDAO availabilityDAO;
    protected List<String> nomsClients =
            Arrays.asList("Ackerman", "Arlelt","Blouse","Braun","Hoover",
                    "Jager","Kirschtein","Lenz","Reiss","Springer","Zoe",
                    "Braus", "Smith","Bahner","Zacharias","Bossard","Langner");
    protected List<String> prenomsClients =
            Arrays.asList("A","B","C","D","E","F","G","H","I","J","K","L","M",
                    "N","O","P","Q","R","S","T","U","V","W","X","Y","Z");

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public InitWeek(AppointmentDAO appointmentDAO, AvailabilityDAO availabilityDAO) {
        this.appointmentDAO = appointmentDAO;
        this.availabilityDAO = availabilityDAO;
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
                Collection<Availability> dispos = availabilityDAO.getAvailabilityByDay(date.getDayOfWeek());
                // crée un rdv pour chaque dispo
                for (Availability dispo : dispos ){
                    System.out.println(dispo.getBegin());
                    LocalDateTime dateTime = LocalDateTime.of(date, dispo.getBegin());
                    Appointment rdv = new Appointment(dateTime, genereClient(), dispo.getVeterinaryName());
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
