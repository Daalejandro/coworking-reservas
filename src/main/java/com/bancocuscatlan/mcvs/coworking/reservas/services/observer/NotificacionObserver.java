package com.bancocuscatlan.mcvs.coworking.reservas.services.observer;

import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.bancocuscatlan.mcvs.coworking.reservas.services.NotificaionService;

@Service
public class NotificacionObserver {

    private final NotificaionService notificationService;

    public NotificacionObserver(NotificaionService notificationService) {
        this.notificationService = notificationService;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void alConfirmarReserva(ReservaConfirmadaEvent evento) {
        notificationService.enviarCorreo(evento.getCorreo());
    }
}
