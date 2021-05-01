package com.fges.ckonsoru.useCase;

import com.fges.ckonsoru.data.AppointmentRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ListAppointmentByDate implements UseCase {

    private AppointmentRepository repo;

    public ListAppointmentByDate(AppointmentRepository repo){
        this.repo = repo;
    }

    public void trigger(){
        LocalDate date = getDateFromUser();
        System.out.println(date);
    }

    private LocalDate getDateFromUser(){
        System.out.println("Entrer une date au format JJ/MM/AAAA (ex: 18/03/2021) :");

        Scanner answer = new Scanner(System.in);
        String choice = answer.nextLine();

        // TODO : handle format errors
        
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime date = LocalDateTime.parse(choice, timeFormatter);

        return date.toLocalDate();
    }
}