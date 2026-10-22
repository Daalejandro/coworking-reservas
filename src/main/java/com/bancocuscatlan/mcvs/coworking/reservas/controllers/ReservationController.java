package com.bancocuscatlan.mcvs.coworking.reservas.controllers;

import com.bancocuscatlan.mcvs.coworking.reservas.services.ReservationServiceImpl;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancocuscatlan.mcvs.coworking.reservas.dto.request.PaymantRequest;
import com.bancocuscatlan.mcvs.coworking.reservas.dto.request.ReservationRequest;
import com.bancocuscatlan.mcvs.coworking.reservas.dto.response.PaymantResponse;
import com.bancocuscatlan.mcvs.coworking.reservas.dto.response.ReservationResponse;
import com.bancocuscatlan.mcvs.coworking.reservas.services.IReservationService;

import jakarta.validation.Valid;
import kotlin.uuid.Uuid;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

    private final ReservationServiceImpl reservationServiceImpl;
    private IReservationService reservationService;

    public ReservationController(IReservationService reservationService,
            ReservationServiceImpl reservationServiceImpl) {
        this.reservationService = reservationService;
        this.reservationServiceImpl = reservationServiceImpl;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ReservationRequest reservationRequest, BindingResult result) {

        if (result.hasFieldErrors()) {
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.create(reservationRequest));

    }

    @GetMapping()
    public ResponseEntity<List<ReservationResponse>> lisReservation() {

        List<ReservationResponse> reservas = reservationServiceImpl.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(reservas);
    }

    @GetMapping("/reservas")
    public ResponseEntity<List<ReservationResponse>> lisReservationUser(Principal principal) {

        List<ReservationResponse> reservasUsuario = reservationServiceImpl.reservacionesPorCorre(principal.getName());

        return ResponseEntity.status(HttpStatus.OK).body(reservasUsuario);
    }

    @PutMapping("/cancelar/{id}")
    public String reservedCancelled(Principal principal, @PathVariable Uuid id ){

        
        
        return entity;
    }


    @PostMapping("/validate")
    public ResponseEntity<PaymantResponse> validarPago(
            @Valid @RequestBody PaymantRequest request) {

        return ResponseEntity.ok(
                reservationService.validarPago(request));
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo: " + err.getField() + " " + err.getDefaultMessage());
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

}
