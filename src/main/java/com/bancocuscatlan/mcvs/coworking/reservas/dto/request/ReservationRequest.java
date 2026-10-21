package com.bancocuscatlan.mcvs.coworking.reservas.dto.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
public class ReservationRequest {

    @NotNull(message = "El espacio es obligatorio")
    UUID espacioId;

    @NotNull(message = "El espacio es obligatorio") //falta agreagra que valide el id con valid personalizada;
    UUID usuarioId;
    

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    @NotNull(message = "La hora de inicio es obligatoria")
    @Future(message = "La hora de inicio debe ser futura")
    LocalDateTime horaInicio;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    @NotNull(message = "La hora de fin es obligatoria")
    LocalDateTime horaFin;

}
