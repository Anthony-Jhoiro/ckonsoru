/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fges.ckonsoru.usecase;

/**
 * Interface for UseCases
 */
public class UseCaseTest implements UseCase {

    public UseCaseTest(){

    }

    public void trigger(){
        System.out.println("j'ai été appelé");
    }

}