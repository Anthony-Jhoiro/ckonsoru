/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fges.ckonsoru;

import java.util.Properties;
import java.util.Date;
import com.fges.ckonsoru.menu.Menu;
import com.fges.ckonsoru.useCase.ListAppointmentByDate;
import com.fges.ckonsoru.usecase.UseCase;
import com.fges.ckonsoru.usecase.UseCaseTest;
import com.fges.ckonsoru.data.xml.XMLAppointmentRepository;

/**
 * Launch the App
 * @author julie.jacques
 */
public class App {
    
    public static void main(String args[]){
        
        System.out.println("Bienvenue sur Clinique Konsoru !");
        String xmlfile = "src/main/java/com/fges/ckonsoru/xmlbdd/ckonsoru.xml";
        
        // chargement de la configuration de la persistence
        ConfigLoader cf = new ConfigLoader();
        Properties properties = cf.getProperties();
        System.out.println("Mode de persistence : "
                +properties.getProperty("persistence"));

        // initialating the menu
        String[] choices = {"Afficher les créneaux disponibles pour une date donnée", "Lister les rendez-vous passés, présent et à venir d un client", "Prendre un rendez-vous", "Supprimer un rendez-vous", "Quitter"};
        UseCase[] actions = {new ListAppointmentByDate(new XMLAppointmentRepository(xmlfile)), new UseCaseTest(), new UseCaseTest(), new UseCaseTest()};
        Menu menu = new Menu(choices, actions);

        


    }
    
}


