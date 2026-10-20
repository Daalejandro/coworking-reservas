package com.bancocuscatlan.mcvs.coworking.reservas.dto.request;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
public class SpaceRequest {


    private String nombre;

    private String tipo;

    private Integer capacidad;

    private String ubicacion;

    private BigDecimal tarifa;

    private boolean disponible;



}
