package com.bancocuscatlan.mcvs.coworking.reservas.repositories;

import java.time.LocalDateTime;
import java.util.List;
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

        @Query("""
                        select r.espacios.id as espacioId,
                               r.horaInicio as horaInicio,
                               r.horaFin as horaFin
                        from Reservation r
                        where r.status = :status
                          and r.horaInicio < :hasta
                          and r.horaFin > :desde
                        """)
        List<OccupancionReservacion> buscarParaOcupacion(
                        @Param("desde") LocalDateTime desde,
                        @Param("hasta") LocalDateTime hasta,
                        @Param("status") ReservationStatus status);
}
