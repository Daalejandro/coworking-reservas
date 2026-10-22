package com.bancocuscatlan.mcvs.coworking.reservas.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ReporteResponse {
    
    UUID espacioId;
    String nombre;
    BigDecimal porcentajeOcupacion;
}
