/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fges.ckonsoru;

import java.util.Properties;

import com.fges.ckonsoru.data.*;
import com.fges.ckonsoru.data.psql.*;
import com.fges.ckonsoru.data.xml.XMLAdapterSingleton;
import com.fges.ckonsoru.data.xml.XMLAppointmentDAO;
import com.fges.ckonsoru.data.xml.XMLAvailabilityDAO;
import com.fges.ckonsoru.menu.Menu;
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

        AppointmentDAO appointmentDAO = null;
        AvailabilityDAO availabilityDAO = null;
        TimeslotDAO timeslotDAO = null;
        WaitingLineDAO waitingLineDAO = null;
        CancellationDAO cancellationDAO = null;


        if(percistence.equals("bdd")){
            BDDAdapterSingleton adapterSingleton = BDDAdapterSingleton.getInstance();
            adapterSingleton.init(properties.getProperty("bdd.url"), properties.getProperty("bdd.login"), properties.getProperty("bdd.mdp"));
            appointmentDAO = new BDDAppointmentDAO(adapterSingleton);
            availabilityDAO = new BDDAvaibilityDAO(adapterSingleton);
            timeslotDAO = new BDDTimeslotDAO(adapterSingleton);
            waitingLineDAO = new BDDWaitingLineDAO(adapterSingleton);
            cancellationDAO = new BDDCancellationDAO();

        }
        else if(percistence.equals("xml")){
            XMLAdapterSingleton.init(properties);
            appointmentDAO = new XMLAppointmentDAO();
            availabilityDAO = new XMLAvailabilityDAO();
        }
        else {
            System.out.println("le mode de persistence ne peut être que 'xml' ou 'bdd', or, il est égal à |" + percistence + "|");
            System.exit(0);
        }

        // initialating the menu
        UseCase[] actions = {
                new InitWeek(appointmentDAO, timeslotDAO),
                new ListFreeTimeslotsByDate(timeslotDAO),
                new ListAppointments(appointmentDAO),
                new TakeAppointment(availabilityDAO, appointmentDAO),
                new RemoveAppointment(appointmentDAO),
                new ListCancellation(cancellationDAO),
                new WaitingList(waitingLineDAO)
        };

        Menu menu = new Menu(actions);
        menu.display();

    }
    
}


