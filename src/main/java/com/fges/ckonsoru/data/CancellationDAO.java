package com.fges.ckonsoru.data;

import com.fges.ckonsoru.models.Cancellation;

import java.time.LocalDateTime;
import java.util.Collection;

public interface CancellationDAO {

    /**
     * get all the cancellations
     *
     * @return the list of all {@link Cancellation}
     * @throws Exception
     */
    Collection<Cancellation> getAnnulations() throws Exception;

    /**
     * note a cancellation
     *
     * @param date date of the appointment
     * @param clientName client who's removing
     * @return true if the cancellation worked, false
     * otherwise
     * @throws Exception
     */
    boolean setCancellation(LocalDateTime date, String clientName) throws Exception;

}
