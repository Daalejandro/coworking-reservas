package com.bancocuscatlan.mcvs.coworking.reservas.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.bancocuscatlan.mcvs.coworking.reservas.dto.response.ReporteResponse;
import com.bancocuscatlan.mcvs.coworking.reservas.entities.ReservationStatus;
import com.bancocuscatlan.mcvs.coworking.reservas.entities.Space;
import com.bancocuscatlan.mcvs.coworking.reservas.repositories.EspacioRepositorio;
import com.bancocuscatlan.mcvs.coworking.reservas.repositories.OccupancionReservacion;
import com.bancocuscatlan.mcvs.coworking.reservas.repositories.ReservationRepository;

@Service
public class ReporteService {

        private final ReservationRepository reservationRepository;
        private final EspacioRepositorio spaceRepository;

        public ReporteService(
                        ReservationRepository reservationRepository,
                        EspacioRepositorio spaceRepository) {

                this.reservationRepository = reservationRepository;
                this.spaceRepository = spaceRepository;
        }

        @Cacheable(cacheNames = "ocupacion", key = "#desde.getYear() + '_' + #desde.getMonthValue()") //agrupamos en cache ej 2026_12
        @Transactional(readOnly = true)
        public List<ReporteResponse> obtenerOcupacion( LocalDateTime desde,LocalDateTime hasta) {

                if (desde == null || hasta == null || !desde.isBefore(hasta)) {
                        throw new ResponseStatusException(
                                        HttpStatus.BAD_REQUEST,
                                        "La fecha desde debe ser anterior a la fecha hasta");
                }

                List<Space> espacios = (List<Space>) spaceRepository.findAll();

                List<OccupancionReservacion> reservas = reservationRepository.buscarParaOcupacion(
                                desde,
                                hasta,
                                ReservationStatus.CONFIRMED);

                double segundosDisponibles = segundosEntre(desde, hasta);

                Map<UUID, Double> ocupacionPorEspacio = new HashMap<>();

                for (OccupancionReservacion reserva : reservas) {

                        LocalDateTime inicio = reserva.getHoraInicio().isBefore(desde)
                                        ? desde
                                        : reserva.getHoraInicio();

                        LocalDateTime fin = reserva.getHoraFin().isAfter(hasta)
                                        ? hasta
                                        : reserva.getHoraFin();

                        double segundosOcupados = segundosEntre(inicio, fin);

                        ocupacionPorEspacio.merge(
                                        reserva.getEspacioId(),
                                        segundosOcupados,
                                        Double::sum);
                }

              



                return espacios.stream()
                                .map(espacio -> {

                                        double ocupados = ocupacionPorEspacio.getOrDefault(
                                                        espacio.getId(),
                                                        0.0);

                                        BigDecimal porcentaje = BigDecimal
                                                        .valueOf(ocupados / segundosDisponibles * 100)
                                                        .setScale(2, RoundingMode.HALF_UP);

                                        return new ReporteResponse(
                                                        espacio.getId(),
                                                        espacio.getNombre(),
                                                        porcentaje);
                                })
                                .toList();
        }

        private double segundosEntre(
                        LocalDateTime inicio,
                        LocalDateTime fin) {

                Duration duracion = Duration.between(inicio, fin);

                return duracion.getSeconds()
                                + duracion.getNano() / 1_000_000_000.0;
        }
}