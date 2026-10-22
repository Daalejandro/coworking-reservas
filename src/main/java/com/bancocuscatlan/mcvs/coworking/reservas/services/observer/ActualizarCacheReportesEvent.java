package com.bancocuscatlan.mcvs.coworking.reservas.services.observer;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActualizarCacheReportesEvent {

    private final LocalDateTime fechaInicio;
    private final LocalDateTime fechaFin;

    public ActualizarCacheReportesEvent(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

}
