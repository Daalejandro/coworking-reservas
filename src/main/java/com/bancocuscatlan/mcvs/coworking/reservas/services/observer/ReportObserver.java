package com.bancocuscatlan.mcvs.coworking.reservas.services.observer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class ReportObserver {

   private final Logger log = LoggerFactory.getLogger(ReportObserver.class);

    
    @Caching(evict = {
        //Borra la caché del mes donde inicia la reserva (ej: Diciembre)
        @CacheEvict(cacheNames = "ocupacion", key = "#evento.fechaInicio.year + '_' + #evento.fechaInicio.monthValue"),
        // Borra la caché del mes donde termina la reserva (ej: Enero)
        @CacheEvict(cacheNames = "ocupacion", key = "#evento.fechaFin.year + '_' + #evento.fechaFin.monthValue")
    })
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void cambioEstadoReserva(ActualizarCacheReportesEvent evento) {
        log.info("Eliminando cache para ese periodo ");
    }

}
