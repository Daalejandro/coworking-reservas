package com.bancocuscatlan.mcvs.coworking.reservas.dto.request;

import java.time.LocalDateTime;
import java.util.UUID;


import com.fasterxml.jackson.annotation.JsonFormat;


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
