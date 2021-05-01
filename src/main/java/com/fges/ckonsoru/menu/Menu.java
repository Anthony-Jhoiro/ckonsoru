/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fges.ckonsoru.menu;

import com.fges.ckonsoru.data.AppointmentRepository;
import com.fges.ckonsoru.usecase.UseCase;

import java.util.Scanner;

/**
 * Represents a Menu
 */
public class Menu {

    /**
     * UseCases displayed by the menu
     */
    private final UseCase[] actions;

    /**
     * Create a menu with the given usecases
     * @param actions usecases that defines the options
     */
    public Menu(UseCase[] actions) {
        this.actions = actions;
    }

    /**
     * Display the menu
     */
    public void display() {

        int choiceIndex;

        do {
            System.out.println("Actions disponibles :");
            int i;
            for (i = 0; i < this.actions.length; i++) {
                System.out.println((i + 1) + ": " + this.actions[i].getChoice());
            }
            System.out.println("9. Quitter");

            Scanner answer = new Scanner(System.in);

            do {
                System.out.println("Entrer un numéro d action:");
                String choice = answer.nextLine();
                choiceIndex = Integer.parseInt(choice);
            } while ((choiceIndex < 1 || choiceIndex > this.actions.length) && choiceIndex != 9);


            if (choiceIndex != 9) {
                System.out.println("votre choix : " + choiceIndex);
                this.actions[choiceIndex - 1].trigger();
            }
        // If the choice is 9 end the loop
        } while (choiceIndex != 9);
        System.out.println("Au revoir !");

    }

}