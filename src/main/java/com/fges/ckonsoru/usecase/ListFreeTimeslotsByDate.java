package com.fges.ckonsoru.usecase;

import com.fges.ckonsoru.data.TimeslotDAO;
import com.fges.ckonsoru.models.Timeslot;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        LocalDate date = LocalDate.parse(dateString, timeFormatter);


        Collection<Timeslot> res =
                this.timeslotDAO.getFreeTimeslots(date);

        for (Timeslot time : res) {
            System.out.println(time);
        }

    }
}
