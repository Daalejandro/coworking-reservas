package com.bancocuscatlan.mcvs.coworking.reservas.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bancocuscatlan.mcvs.coworking.reservas.dto.request.ReservationRequest;
import com.bancocuscatlan.mcvs.coworking.reservas.dto.response.ReservationResponse;
import com.bancocuscatlan.mcvs.coworking.reservas.entities.Reservation;

@Mapper(componentModel = "spring")

public interface ReservationMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuarios", ignore = true)
    @Mapping(target = "espacios", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "total", ignore = true)
    Reservation toReservation(ReservationRequest request);

    @Mapping(target = "usuarioId", source = "usuarios.id")
    @Mapping(target = "nombreId", source = "usuarios.nombre")
    @Mapping(target = "espacioId", source = "espacios.id") 
    ReservationResponse toResponse(Reservation reservation);

}
