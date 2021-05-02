/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fges.ckonsoru;

import java.util.Properties;

import com.fges.ckonsoru.data.AppointmentRepository;
import com.fges.ckonsoru.data.AvailabilityRepository;
import com.fges.ckonsoru.data.psql.BDDAppointmentRepository;
import com.fges.ckonsoru.data.psql.BDDAvaibilityRepository;
import com.fges.ckonsoru.data.xml.XMLAdapter;
import com.fges.ckonsoru.data.xml.XMLAvailabilityRepository;
import com.fges.ckonsoru.menu.Menu;
import com.fges.ckonsoru.data.xml.XMLAppointmentRepository;
import com.fges.ckonsoru.usecase.*;

/**
 * Launch the App
 * @author julie.jacques
 */
public class App {
    
    public static void main(String[] args){

        System.out.println("Bienvenue sur Clinique Konsoru !");
        
        // chargement de la configuration de la persistence
        ConfigLoader cf = new ConfigLoader();
        Properties properties = cf.getProperties();
        String percistence = properties.getProperty("persistence");
        System.out.println("Mode de persistence : "
                +percistence);

        AppointmentRepository appointmentRepository = null;
        AvailabilityRepository availabilityRepository = null;

        if(percistence.equals("bdd")){
            appointmentRepository = new BDDAppointmentRepository(properties);
            availabilityRepository = new BDDAvaibilityRepository(properties);
        }
        else if(percistence.equals("xml")){
            String xmlfile = "src/main/java/com/fges/ckonsoru/xmlbdd/ckonsoru.xml";
            XMLAdapter adapter = new XMLAdapter(xmlfile);
            appointmentRepository = new XMLAppointmentRepository(adapter);
            availabilityRepository = new XMLAvailabilityRepository(adapter);
        }
        else {
            System.out.println("le mode de persistence ne peut être que 'xml' ou 'bdd', or, il est égal à |" + percistence + "|");
            System.exit(0);
        }

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


