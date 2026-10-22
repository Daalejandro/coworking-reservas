package com.bancocuscatlan.mcvs.coworking.reservas.dto.response;

public class PaymantResponse {

    public PaymantResponse() {
    }


    public PaymantResponse(boolean aprobado, String mensaje) {
        this.aprobado = aprobado;
        this.mensaje = mensaje;
    }


    private boolean aprobado;
    private String mensaje;

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


    
}
