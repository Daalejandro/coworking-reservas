package com.bancocuscatlan.mcvs.coworking.reservas.controllers;

import com.bancocuscatlan.mcvs.coworking.reservas.repositories.ReservationRepository;
import com.bancocuscatlan.mcvs.coworking.reservas.services.ReservationServiceImpl;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
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

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

    private IReservationService reservationService;

    public ReservationController(IReservationService reservationService,
            ReservationServiceImpl reservationServiceImpl, ReservationRepository reservationRepository) {
        this.reservationService = reservationService;

    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ReservationRequest reservationRequest, BindingResult result,Principal primari) {

        if (result.hasFieldErrors()) {
            return validation(result);
        }
        reservationRequest.setUsuario(primari.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.create(reservationRequest));

    }

    @PostMapping("/validate")
    public ResponseEntity<PaymantResponse> validarPago(@Valid @RequestBody PaymantRequest request) {

        return ResponseEntity.ok(reservationService.validarPago(request));
    }

    @GetMapping()
    public ResponseEntity<List<ReservationResponse>> lisReservation() {

        List<ReservationResponse> reservas = reservationService.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(reservas);
    }

    @GetMapping("/list")
    public ResponseEntity<List<ReservationResponse>> lisReservationUser(Principal principal) {

        List<ReservationResponse> reservasUsuario = reservationService.reservacionesPorCorre(principal.getName());

        return ResponseEntity.status(HttpStatus.OK).body(reservasUsuario);
    }

    @PutMapping("/cancelar/{id}")
    public ResponseEntity<?> reservedCancelled(Principal principal, @PathVariable UUID id) {

        ReservationResponse reservation = reservationService.actualizar(principal.getName(), id);

        return ResponseEntity.status(HttpStatus.OK).body(reservation);
    }

    @DeleteMapping ("/{id}")
    public void deleteReservation(@PathVariable UUID id){

        reservationService.deleteReservation(id);
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo: " + err.getField() + " " + err.getDefaultMessage());
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

}
