package com.bancocuscatlan.mcvs.coworking.reservas.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bancocuscatlan.mcvs.coworking.reservas.clients.PaymantFeingClient;
import com.bancocuscatlan.mcvs.coworking.reservas.dto.request.PaymantRequest;
import com.bancocuscatlan.mcvs.coworking.reservas.dto.response.PaymantResponse;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@Service
public class PagoService {

    private PaymantFeingClient paymantFeingClient;

    private final Logger log = LoggerFactory.getLogger(PagoService.class);

    public PagoService(PaymantFeingClient paymantFeingClient) {
        this.paymantFeingClient = paymantFeingClient;
    }

    @CircuitBreaker(name = "items", fallbackMethod = "pagoFallback")
    @TimeLimiter(name = "items")
    public PaymantResponse validar(PaymantRequest request) {

        return paymantFeingClient.validar(request);

    }

    public PaymantResponse pagoFallback(
            PaymantRequest request,
            Throwable ex) {
        log.error(ex.getMessage());
        return new PaymantResponse(
            false,
            "Servicio de pago no disponible. "
                + "La reserva permanece pendiente de pago"
        );
    }


}
