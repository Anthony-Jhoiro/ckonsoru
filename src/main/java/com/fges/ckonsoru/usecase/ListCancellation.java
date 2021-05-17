package com.fges.ckonsoru.usecase;

import com.fges.ckonsoru.data.CancellationDAO;
import com.fges.ckonsoru.models.Cancellation;

import java.util.Collection;

public class ListCancellation extends UseCase {

    CancellationDAO cancellationDAO;

    public ListCancellation(CancellationDAO cancellationDAO){
        this.cancellationDAO = cancellationDAO;
    }

    @Override
    public String getChoice() {
        return "Liste des annulations";
    }

    @Override
    public void trigger() {

        System.out.println("Liste des annulations");

        try {
            Collection<Cancellation> cancels = this.cancellationDAO.getAnnulations();

            for (Cancellation cancel : cancels) {
                System.out.println(cancel);
            }
        }
        catch (Exception error) {
            System.out.println("un problème est survenu " +
                    "lors de la recherche des annulations");
            System.out.println(error.getLocalizedMessage());
        }

    }
}
