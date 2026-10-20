package com.bancocuscatlan.mcvs.coworking.reservas.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
public class SpaceResponse {

    private UUID id;

    private String nombre;

    private String tipo;

    private Integer capacidad;

    private String ubicacion;

    private BigDecimal tarifa;

    private boolean disponible;
    
}
