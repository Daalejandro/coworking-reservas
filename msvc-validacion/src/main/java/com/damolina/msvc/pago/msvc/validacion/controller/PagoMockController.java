package com.damolina.msvc.pago.msvc.validacion.controller;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.damolina.msvc.pago.msvc.validacion.PagoConfiguraciones;
import com.damolina.msvc.pago.msvc.validacion.dto.PaymentRequest;
import com.damolina.msvc.pago.msvc.validacion.dto.PaymentResponse;

@RestController
public class PagoMockController {

    private final PagoConfiguraciones properties;

    public PagoMockController(PagoConfiguraciones properties) {
        this.properties = properties;
    }

    @PostMapping("/validate")
    public PaymentResponse validar(@RequestBody PaymentRequest request)
            throws InterruptedException {

        String metodo = request.getMetodoPago();

        if (!"TARJETA".equals(metodo)
                && !"TRANSFERENCIA".equals(metodo)) {

            return new PaymentResponse(
                    false, "Método de pago no soportado");
        }
 
      
        var escenario = properties.getEscenario();

        if (escenario == PagoConfiguraciones.Escenario.ERROR) {
            throw new ResponseStatusException(
                    HttpStatus.SERVICE_UNAVAILABLE,
                    "Servicio de pago no disponible");
        }

        if (escenario == PagoConfiguraciones.Escenario.LENTO) {
            Thread.sleep(properties.getDemoraMs());
        }

        if (escenario == PagoConfiguraciones.Escenario.RECHAZAR) {
            return new PaymentResponse(
                    false, "Pago rechazado por el proveedor");
        }

        return new PaymentResponse(true, "Pago aprobado");
    }
}