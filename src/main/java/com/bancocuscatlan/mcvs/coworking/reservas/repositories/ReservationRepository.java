package com.bancocuscatlan.mcvs.coworking.reservas.repositories;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.bancocuscatlan.mcvs.coworking.reservas.entities.Reservation;
import com.bancocuscatlan.mcvs.coworking.reservas.entities.ReservationStatus;

public interface ReservationRepository extends CrudRepository<Reservation, UUID> {

    @Query("""
            SELECT COUNT(r) > 0
            FROM Reservation r
            WHERE r.espacios.id = :espacioId
              AND r.status <> :estadoCancelado
              AND r.horaInicio < :horaFin
              AND r.horaFin > :horaInicio
            """)
    boolean existeSolapamiento(
            @Param("espacioId") UUID espacioId,
            @Param("horaInicio") LocalDateTime horaInicio,
            @Param("horaFin") LocalDateTime horaFin,
            @Param("estadoCancelado") ReservationStatus estadoCancelado);

}
