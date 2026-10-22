package com.bancocuscatlan.mcvs.coworking.reservas.services;

import com.bancocuscatlan.mcvs.coworking.reservas.dto.request.PaymantRequest;
import com.bancocuscatlan.mcvs.coworking.reservas.dto.request.ReservationRequest;
import com.bancocuscatlan.mcvs.coworking.reservas.dto.response.PaymantResponse;
import com.bancocuscatlan.mcvs.coworking.reservas.dto.response.ReservationResponse;

public interface IReservationService {

    ReservationResponse create(ReservationRequest request);

    PaymantResponse validarPago(PaymantRequest request);
    

}
