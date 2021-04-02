package com.fges.ckonsoru.models;

import com.fges.ckonsoru.data.Id;

import java.util.UUID;

/**
 * This class represents a Client
 * @author anthony
 */
public class Client {

    /**
     * Represents the client uniq id
     */
    protected UUID id;

    /**
     * Represents the client firstname first letter
     */
    protected char firstNameInitial;

    /**
     * Represents the client lastName
     */
    protected String lastName;

    /**
     * Create a Client with an Id, a firstNameInitial and a lastName
     * @param id client id
     * @param firstNameInitial client firstname initial
     * @param lastName client lastName initial
     */
    public Client(UUID id, char firstNameInitial, String lastName) {
        this.id = id;
        this.firstNameInitial = firstNameInitial;
        this.lastName = lastName;
    }

    /**
     * Create a Client with a firstNameInitial and a lastName. Generate the id
     * @param firstNameInitial client firstname initial
     * @param lastName client lastName initial
     */
    public Client(char firstNameInitial, String lastName) {
        this(Id.makeId(), firstNameInitial, lastName);
    }


    /**
     * Get the client id
     * @return UUID that represents the client id
     */
    public UUID getId() {
        return id;
    }

    /**
     * Get the client firstname initial
     * @return char that represents the client firstname initial
     */
    public char getFirstNameInitial() {
        return firstNameInitial;
    }


    /**
     * Get the client lastname
     * @return String that represents the client lastname
     */
    public String getLastName() {
        return lastName;
    }
}
