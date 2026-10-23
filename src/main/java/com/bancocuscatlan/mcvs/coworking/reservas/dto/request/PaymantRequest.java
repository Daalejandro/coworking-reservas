package com.bancocuscatlan.mcvs.coworking.reservas.dto.request;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
public class PaymantRequest {

 
    private UUID idReserva;
  
    private String metodoPago;

    private BigDecimal total;
}
