package com.fges.ckonsoru.data;

import com.fges.ckonsoru.models.WaitingLineSpot;

import java.util.Collection;

public interface WaitingLineDAO {
    /**
     * Add a slot to the waiting line.
     * @param waitingLineSpot Slot to add.
     * @return true if it succeeded.
     */
    boolean addToWaitingLine(WaitingLineSpot waitingLineSpot);

    /**
     * Fetch all the waiting lines spots
     * @return a collection of WaitingLineSpot
     */
    Collection<WaitingLineSpot> getWaitingLineSpots();

    /**
     * Assign the timeslot to the user with the latest deadline. If 2 users are equal, the first one that asked
     * is the first one that ask gets it.
     * @param timeslot timeslot to assign
     * @return true if it succeeded.
     */
    boolean updateWaitingLine(Object timeslot); // TODO : type


}
