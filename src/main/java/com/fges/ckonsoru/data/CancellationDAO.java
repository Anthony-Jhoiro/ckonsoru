package com.fges.ckonsoru.data;

import com.fges.ckonsoru.models.Cancellation;

import java.time.LocalDateTime;
import java.util.Collection;

public interface CancellationDAO {

    /**
     * get all the cancellations
     *
     * @return the list of all {@link Cancellation}
     */
    Collection<Cancellation> getAnnulations();

    /**
     * note a cancellation
     *
     * @param date date of the appointment
     * @param clientName client who's removing
     * @return true if the cancellation worked, false
     */
    boolean setCancellation(LocalDateTime date, String clientName);

}
