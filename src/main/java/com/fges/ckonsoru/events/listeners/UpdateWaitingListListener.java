package com.fges.ckonsoru.events.listeners;

import com.fges.ckonsoru.data.WaitingLineDAO;
import com.fges.ckonsoru.events.Observer;

import java.time.LocalDateTime;

public class UpdateWaitingListListener implements Observer<LocalDateTime> {
    private final WaitingLineDAO waitingLineDAO;

    public UpdateWaitingListListener(WaitingLineDAO waitingLineDAO) {
        this.waitingLineDAO = waitingLineDAO;
    }

    @Override
    public void trigger(LocalDateTime date) {
        try{
            this.waitingLineDAO.updateWaitingLine(date);
        }
        catch(Exception error){
            System.out.println("problème dans la " +
                    "mise à jour de la liste " +
                    "d'attente");
        }
    }
}
