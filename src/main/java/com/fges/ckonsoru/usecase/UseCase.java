/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fges.ckonsoru.usecase;

import java.sql.SQLException;

/**
 * Represents a USECase used by the Menu class as an action. A UseCase has a choice string and a trigger method.
 */
public interface UseCase {

    /**
     * Get the use case choice string
     * @return a string representing the UseCase choice.
     */
    String getChoice();

    /**
     * Called to trigger the UseCase
     */
    void trigger() ;

}