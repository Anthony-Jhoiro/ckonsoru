/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fges.ckonsoru;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Properties;

import com.fges.ckonsoru.data.AppointmentRepository;
import com.fges.ckonsoru.data.AvailabilityRepository;
import com.fges.ckonsoru.data.xml.XMLAdapter;
import com.fges.ckonsoru.data.xml.XMLAvailabilityRepository;
import com.fges.ckonsoru.menu.Menu;
import com.fges.ckonsoru.models.Appointment;
import com.fges.ckonsoru.data.xml.XMLAppointmentRepository;
import com.fges.ckonsoru.usecase.*;

/**
 * Launch the App
 * @author julie.jacques
 */
public class App {
    
    public static void main(String[] args){

        System.out.println("Bienvenue sur Clinique Konsoru !");
        String xmlfile = "src/main/java/com/fges/ckonsoru/xmlbdd/ckonsoru.xml";
        
        // chargement de la configuration de la persistence
        ConfigLoader cf = new ConfigLoader();
        Properties properties = cf.getProperties();
        System.out.println("Mode de persistence : "
                +properties.getProperty("persistence"));

        XMLAdapter adapter = new XMLAdapter(xmlfile);
        AppointmentRepository appointmentRepository = new XMLAppointmentRepository(adapter);
        AvailabilityRepository availabilityRepository = new XMLAvailabilityRepository(adapter);

        // initialating the menu
        UseCase[] actions = {
                new ListFreeTimeslotsByDate(availabilityRepository, appointmentRepository),
                new ListAppointments(appointmentRepository),
                new TakeAppointment(availabilityRepository, appointmentRepository),
                new RemoveAppointment(appointmentRepository)
        };

        Menu menu = new Menu(actions);
        menu.display();

    }
    
}


