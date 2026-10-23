package com.damolina.msvc.pago.msvc.validacion.dto;

public class PaymentResponse {
    
    boolean aprobado;
    String mensaje;

    
    public boolean isAprobado() {
        return aprobado;
    }
    public void setAprobado(boolean aprobado) {
        this.aprobado = aprobado;
    }
    public String getMensaje() {
        return mensaje;
    }
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public PaymentResponse(boolean aprobado, String mensaje) {
        this.aprobado = aprobado;
        this.mensaje = mensaje;
    }

    
    

}
