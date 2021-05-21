package com.fges.ckonsoru.events.listeners;

import com.fges.ckonsoru.data.CancellationDAO;
import com.fges.ckonsoru.data.WaitingLineDAO;
import com.fges.ckonsoru.events.Observer;
import com.fges.ckonsoru.models.Appointment;

import java.time.Duration;
import java.time.LocalDateTime;

public class UpdateWaitingListListener implements Observer<Appointment> {
    private final WaitingLineDAO waitingLineDAO;
    private final CancellationDAO cancellationDAO;

    public UpdateWaitingListListener(WaitingLineDAO waitingLineDAO, CancellationDAO cancellationDAO) {
        this.waitingLineDAO = waitingLineDAO;
        this.cancellationDAO = cancellationDAO;
    }

    @Override
    public void trigger(Appointment appointment) {

        // if it is removed 24h before or less,
        // we note the cancellation
        if (Duration.between(LocalDateTime.now(),
                appointment.getBeginDateTime()).toSeconds() < 24 * 3600) {

            if (this.cancellationDAO.setCancellation(appointment.getBeginDateTime(), appointment.getClientName())) {
                System.out.println("l'annulation " +
                        "ayant été effectuée " +
                        "moins de 24h avant le " +
                        "rendez-vous, nous avons" +
                        " tracé cette annulation");
            } else {
                System.out.println("Une erreur " +
                        "est survenue lors du " +
                        "traçage de l'annulation");
            }

        }
        this.waitingLineDAO.updateWaitingLine(appointment.getBeginDateTime());

    }
}
