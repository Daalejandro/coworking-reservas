package com.bancocuscatlan.mcvs.coworking.reservas.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.bancocuscatlan.mcvs.coworking.reservas.entities.ReservationStatus;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
public class ReservationResponse {
    
        UUID id;
        UUID usuarioId;
        String nombreId;
        UUID espacioId;
        LocalDateTime horaInicio;
        LocalDateTime horaFin;
        ReservationStatus status;
        BigDecimal total;
}
