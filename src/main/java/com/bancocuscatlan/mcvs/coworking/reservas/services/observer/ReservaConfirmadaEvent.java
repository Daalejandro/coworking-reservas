package com.bancocuscatlan.mcvs.coworking.reservas.services.observer;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
@AllArgsConstructor 
public class ReservaConfirmadaEvent {

    private UUID ID;
    private String correo;
}
