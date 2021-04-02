package com.fges.ckonsoru.models;

import com.fges.ckonsoru.data.Id;

import java.util.UUID;

/**
 * Represents a doctor. A doctor has a uniq identifier (UUID) and a name
 * @author anthony
 */
public class Doctor {
    /**
     * Uniq identifier of the doctor
     */
    protected UUID id;

    /**
     * Represents the name the doctor
     */
    private String name;

    /**
     * Create a doctor with a name and an id
     * @param id id of the doctor
     * @param name name of the doctor
     */
    public Doctor(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Create a Doctor, generate its id
     * @param name name given to the doctor
     */
    public Doctor(String name) {
        this(Id.makeId(), name);
    }

    /**
     * Get the doctor's uniq identifier
     * @return a UUID that represents the uniq identifier
     */
    public UUID getId() {
        return id;
    }

    /**
     * Get the doctor's name
     * @return a String that represents the doctor's name
     */
    public String getName() {
        return name;
    }
}
