package com.fges.ckonsoru.usecase;

import com.fges.ckonsoru.data.AppointmentDAO;
import com.fges.ckonsoru.data.AvailabilityDAO;
import com.fges.ckonsoru.data.TimeslotDAO;
import com.fges.ckonsoru.models.Appointment;
import com.fges.ckonsoru.models.Availability;
import com.fges.ckonsoru.models.Timeslot;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class ListFreeTimeslotsByDate implements UseCase {
    protected TimeslotDAO timeslotDAO;

    public ListFreeTimeslotsByDate(TimeslotDAO timeslotDAO) {
        this.timeslotDAO = timeslotDAO;
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
        try {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
            LocalDate date = LocalDate.parse(dateString, timeFormatter);

            try {
                Collection<Timeslot> res =
                        this.timeslotDAO.getFreeTimeslots(date);

                for(Timeslot time : res) {
                    System.out.println(time);
                }
            }
            catch (Exception error){
                System.out.println("un problème est survenu lors de la recherche de créneaux");
                System.out.println(error.getMessage());
            }
        }
        catch (DateTimeParseException error){
            System.out.println("la date que vous avez entrée n'est pas au bon format");
        }



    }
}
