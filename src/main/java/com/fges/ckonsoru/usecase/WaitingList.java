package com.fges.ckonsoru.usecase;

import com.fges.ckonsoru.data.WaitingLineDAO;
import com.fges.ckonsoru.models.WaitingLineSpot;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

public class WaitingList implements UseCase {

    WaitingLineDAO waitingLineDAO;

    public WaitingList(WaitingLineDAO waitingLineDAO) {
        this.waitingLineDAO = waitingLineDAO;
    }

    @Override
    public String getChoice() {
        return "Liste d'attente";
    }

    @Override
    public void trigger()  {
        System.out.println("Affichage de la liste d'attente");
        System.out.println("nom client (n°téléphone), créneau proposé, vétérinaire proposé");

        try {
            Collection<WaitingLineSpot> waitingLineSpots = waitingLineDAO.getWaitingLineSpots();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm");

            for (WaitingLineSpot waitingLineSpot: waitingLineSpots) {
                System.out.print(
                        waitingLineSpot.getClientName() + "(" +
                                waitingLineSpot.getNumTel() + "), ");

                if (waitingLineSpot.getProposedTimeslot() != null) {
                    System.out.println(formatter.format(waitingLineSpot.getProposedTimeslot()) + ", " + waitingLineSpot.getVeterinaryName());
                } else {
                    System.out.println("-");
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error");
        }


    }

}
