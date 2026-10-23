package com.bancocuscatlan.mcvs.coworking.reservas.services;

import java.util.List;
import java.util.UUID;

import com.bancocuscatlan.mcvs.coworking.reservas.dto.request.PaymantRequest;
import com.bancocuscatlan.mcvs.coworking.reservas.dto.request.ReservationRequest;
import com.bancocuscatlan.mcvs.coworking.reservas.dto.response.PaymantResponse;
import com.bancocuscatlan.mcvs.coworking.reservas.dto.response.ReservationResponse;

public interface IReservationService {

    ReservationResponse create(ReservationRequest request);

    PaymantResponse validarPago(PaymantRequest request);

     List<ReservationResponse> findAll();

    List<ReservationResponse> reservacionesPorCorre(String correo);
    
    ReservationResponse actualizar(String correo,UUID idReserva);

    void deleteReservation(UUID id);

}
