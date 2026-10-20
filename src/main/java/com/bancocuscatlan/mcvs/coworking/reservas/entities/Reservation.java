package com.bancocuscatlan.mcvs.coworking.reservas.entities;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity 
@Table(name = "reservaciones")
public class Reservation {

    @Id 
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "usuario_id",nullable = false)
    private Users usuarios;

    @ManyToOne 
    @JoinColumn (name = "espacio_id", nullable = false)
    private Space espacios;

    
    private LocalDateTime horaInicio;

    private LocalDateTime horaFin;



    public BigDecimal total(){
        
        Duration duracionHora = Duration.between(horaInicio, horaFin);

        Long horas = duracionHora.toHours();

        if(!duracionHora.equals(Duration.ofHours(horas))){
            horas ++;

        }

        return this.espacios.getTarifa().multiply(BigDecimal.valueOf(horas));
    }

}
