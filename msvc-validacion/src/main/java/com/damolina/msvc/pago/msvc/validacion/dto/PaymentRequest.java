package com.damolina.msvc.pago.msvc.validacion.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class PaymentRequest {

    private UUID idReserva;
    private String metodoPago;
    private BigDecimal total;
    
    public UUID getIdReserva() {
        return idReserva;
    }
    public void setIdReserva(UUID idReserva) {
        this.idReserva = idReserva;
    }
    public String getMetodoPago() {
        return metodoPago;
    }
    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
    public BigDecimal getTotal() {
        return total;
    }
    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    
    

}
