package com.bancocuscatlan.mcvs.coworking.reservas.services;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bancocuscatlan.mcvs.coworking.reservas.dto.mapper.ReservationMapper;
import com.bancocuscatlan.mcvs.coworking.reservas.dto.request.ReservationRequest;
import com.bancocuscatlan.mcvs.coworking.reservas.dto.response.ReservationResponse;
import com.bancocuscatlan.mcvs.coworking.reservas.entities.Reservation;
import com.bancocuscatlan.mcvs.coworking.reservas.entities.ReservationStatus;
import com.bancocuscatlan.mcvs.coworking.reservas.entities.Space;
import com.bancocuscatlan.mcvs.coworking.reservas.entities.Users;
import com.bancocuscatlan.mcvs.coworking.reservas.exceptions.SpaceNotFoudException;
import com.bancocuscatlan.mcvs.coworking.reservas.exceptions.SpaceOccupiedException;
import com.bancocuscatlan.mcvs.coworking.reservas.repositories.EspacioRepositorio;
import com.bancocuscatlan.mcvs.coworking.reservas.repositories.ReservationRepository;
import com.bancocuscatlan.mcvs.coworking.reservas.repositories.UserRepository;

@Service
public class ReservationServiceImpl implements IReservationService {

    private ReservationRepository reservationRepository;
    private ReservationMapper reservationMapper;
    private EspacioRepositorio spaceRepository;
    private UserRepository userRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository, ReservationMapper reservationMapper,
            EspacioRepositorio spaceRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.spaceRepository = spaceRepository;
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
    }

    @Transactional 
    @Override
    public ReservationResponse create(ReservationRequest request) {


        if (!request.getHoraFin().isAfter(request.getHoraInicio())) {
            throw new IllegalArgumentException(
                    "La hora de fin debe ser posterior a la hora de inicio");
        }

        Space espacio = spaceRepository.findById(request.getEspacioId())
                .orElseThrow(() -> new SpaceNotFoudException("El espacio no existe"));

        Users usuario = userRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("El usuario no existe"));

        boolean ocupado = reservationRepository.existeSolapamiento(
                espacio.getId(),
                request.getHoraInicio(),
                request.getHoraFin(),
                ReservationStatus.CANCELLED);

        if (ocupado) {
            throw new SpaceOccupiedException(
                    "El espacio ya está reservado en el horario solicitado");
        }

        Reservation reservation = reservationMapper.toReservation(request);

        reservation.setEspacios(espacio);
        reservation.setUsuarios(usuario);
        reservation.setStatus(ReservationStatus.PENDING_PAYMENT);

        reservation.setTotal(calcularTotal(
                request.getHoraInicio(),
                request.getHoraFin(),
                espacio.getTarifa()));

        Reservation guardada = reservationRepository.save(reservation);

        return reservationMapper.toResponse(guardada);
    }

    private BigDecimal calcularTotal(
            LocalDateTime inicio,
            LocalDateTime fin,
            BigDecimal tarifaPorHora) {

        Duration duracion = Duration.between(inicio, fin);

        long horas = duracion.toHours();

        if (!duracion.equals(Duration.ofHours(horas))) {
            horas++;
        }

        return tarifaPorHora.multiply(BigDecimal.valueOf(horas));
    }
}
