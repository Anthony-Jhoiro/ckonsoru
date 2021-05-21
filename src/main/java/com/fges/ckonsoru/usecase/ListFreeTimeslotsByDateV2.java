package com.fges.ckonsoru.usecase;

import com.fges.ckonsoru.data.TimeslotDAO;
import com.fges.ckonsoru.data.WaitingLineDAO;
import com.fges.ckonsoru.models.Timeslot;
import com.fges.ckonsoru.models.WaitingLineSpot;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Scanner;

public class ListFreeTimeslotsByDateV2 extends ListFreeTimeslotsByDate {

    protected WaitingLineDAO waitingLineDAO;

    public ListFreeTimeslotsByDateV2(TimeslotDAO timeslotDAO, WaitingLineDAO waitingLineDAO) {
        super(timeslotDAO);
        this.waitingLineDAO = waitingLineDAO;
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

        if (res.size() == 0) {
            this.registerWaitingLine(date);
            return;
        }

        for (Timeslot time : res) {
            System.out.println(time);
        }

    }


    public void registerWaitingLine(LocalDate date) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        System.out.println("Pas de disponibilités pour le" +
                " " + date.format(timeFormatter));


        System.out.println("Appuyez sur 1 pour vous inscrire en liste d'attente, 0 pour retourner au menu principal");
        Scanner answer = new Scanner(System.in);
        String response = answer.nextLine();
        while (!response.equals("1") && !response.equals(
                "0")) {
            System.out.println("Appuyez sur 1 pour vous inscrire en liste d'attente, 0 pour retourner au menu principal");
            response = answer.nextLine();
        }

        if (response.equals("0")) {
            return;
        }

        System.out.println("Indiquez votre nom (ex: P. Smith)");
        String clientName = answer.nextLine();
        System.out.println("Indiquez un numéro auquel on pourra vous rappeler (ex:+33612345678)");
        String clientNum = answer.nextLine();

        if (this.waitingLineDAO.addToWaitingLine(new WaitingLineSpot(clientName, clientNum, date, LocalDateTime.now()))) {
            System.out.println("vous avez été ajouté à la" +
                    " liste d'attente");
        }
    }
}
