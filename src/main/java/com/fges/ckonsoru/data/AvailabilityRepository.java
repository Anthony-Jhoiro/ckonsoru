package com.fges.ckonsoru.data;

import com.fges.ckonsoru.models.Availability;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Represents a repository to manage veterinaries Availabilities
 */
public abstract class AvailabilityRepository {
    /**
     * Fetch Availabilities from the database for a given day
     * @param day day from which we want the availabilities
     * @return a collection of Availabilities representing the fetched data
     */
    public abstract Collection<Availability> getAvailabilityByDay(DayOfWeek day);

    /**
     * Fetch the database to know if the veterinary is available at the given time
     * @param datetime time to look for
     * @param veterinaryName veterinary name
     * @return true if the veterinary is available
     */
    public abstract boolean isAvailable(LocalDateTime datetime, String veterinaryName);
}
