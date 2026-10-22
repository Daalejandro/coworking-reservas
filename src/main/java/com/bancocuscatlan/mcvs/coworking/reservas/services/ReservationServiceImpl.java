package com.bancocuscatlan.mcvs.coworking.reservas.services;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;


import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.bancocuscatlan.mcvs.coworking.reservas.dto.mapper.ReservationMapper;
import com.bancocuscatlan.mcvs.coworking.reservas.dto.request.PaymantRequest;
import com.bancocuscatlan.mcvs.coworking.reservas.dto.request.ReservationRequest;
import com.bancocuscatlan.mcvs.coworking.reservas.dto.response.PaymantResponse;
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
import com.bancocuscatlan.mcvs.coworking.reservas.services.observer.ActualizarCacheReportesEvent;
import com.bancocuscatlan.mcvs.coworking.reservas.services.observer.ReservaConfirmadaEvent;



@Service
public class ReservationServiceImpl implements IReservationService {


    private final ApplicationEventPublisher eventPublisher;
    private PagoService pagoService;
    private NotificaionService notificaionService;

    private ReservationRepository reservationRepository;
    private ReservationMapper reservationMapper;
    private EspacioRepositorio spaceRepository;
    private UserRepository userRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository, ReservationMapper reservationMapper,ApplicationEventPublisher eventPublisher,
            EspacioRepositorio spaceRepository, UserRepository userRepository,PagoService pagoService,NotificaionService notificaionService) {
        this.userRepository = userRepository;
        this.spaceRepository = spaceRepository;
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
        this.pagoService=pagoService;
        this.notificaionService=notificaionService;
        this.eventPublisher=eventPublisher;
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

    @Transactional
    @Override
    public PaymantResponse validarPago(PaymantRequest request) {

        Reservation reservation = reservationRepository
                .findById(request.getIdReserva())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "La reserva no existe"));

        if (reservation.getStatus() != ReservationStatus.PENDING_PAYMENT) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Solo se pueden confirmar reservas pendientes de pago");
        }

        PaymantRequest solicitudPago = new PaymantRequest();

        solicitudPago.setIdReserva(reservation.getId());
        solicitudPago.setMetodoPago(request.getMetodoPago());
        solicitudPago.setTotal(reservation.getTotal());

        PaymantResponse respuesta = pagoService.validar(solicitudPago);

        if (respuesta.isAprobado()) {
            reservation.setStatus(ReservationStatus.CONFIRMED);
            reservationRepository.save(reservation);

            eventPublisher.publishEvent(new ReservaConfirmadaEvent(reservation.getId(),reservation.getUsuarios().getCorreo()));
            eventPublisher.publishEvent(new ActualizarCacheReportesEvent(reservation.getHoraInicio(),reservation.getHoraFin()));        //llamamos al observador para eliminar cache


            return new PaymantResponse(true,"Pago aprobado. Reserva confirmada");
              
        }

        return respuesta;
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
