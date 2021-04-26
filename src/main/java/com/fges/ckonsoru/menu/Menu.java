/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fges.ckonsoru.menu;

import java.util.Scanner;
import java.lang.Exception;
import java.lang.Integer;
import com.fges.useCase.UseCase;

/**
 * Menu class
 */
public class Menu {

    private String[] choices;
    private UseCase[] actions;
        
    public Menu(String[] choices, UseCase[] actions){

        if(choices.length != actions.length + 1){
            /* throw new  Exception ("le nombre de choix et le nombre d'action doivent être le même"); */
            System.out.println("le nombre de choix et le nombre d'action doivent être le même");
            return;
        }

        this.choices = choices;
        this.actions = actions;

        this.display();

    }

    private void display(){

        System.out.println("Actions disponibles :");

        for(int i = 0; i < this.choices.length; i++){
            System.out.println((i + 1) + ": " + this.choices[i]);
        }

        System.out.println("Entrer un numéro d action:");

        Scanner answer = new Scanner(System.in);
        String choice = answer.nextLine();
        int intChoice = Integer.parseInt(choice);

        while(intChoice < 1 || intChoice > this.actions.length){

            if(intChoice == this.actions.length + 1){
                System.out.println("Au revoir !");
                System.exit(0);
            }

            System.out.println("Entrer un numéro d action Valide:");

            answer = new Scanner(System.in);
            choice = answer.nextLine();
        }

        System.out.println("votre choix : " + choice);
        this.actions[intChoice - 1].trigger();

    }
    
}