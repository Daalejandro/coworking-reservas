package com.bancocuscatlan.mcvs.coworking.reservas.services;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import com.bancocuscatlan.mcvs.coworking.reservas.dto.mapper.ReservationMapper;
import com.bancocuscatlan.mcvs.coworking.reservas.dto.request.ReservationRequest;
import com.bancocuscatlan.mcvs.coworking.reservas.entities.Reservation;
import com.bancocuscatlan.mcvs.coworking.reservas.entities.ReservationStatus;
import com.bancocuscatlan.mcvs.coworking.reservas.entities.Space;
import com.bancocuscatlan.mcvs.coworking.reservas.entities.Users;
import com.bancocuscatlan.mcvs.coworking.reservas.exceptions.SpaceOccupiedException;
import com.bancocuscatlan.mcvs.coworking.reservas.repositories.EspacioRepositorio;
import com.bancocuscatlan.mcvs.coworking.reservas.repositories.ReservationRepository;
import com.bancocuscatlan.mcvs.coworking.reservas.repositories.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceImplTest {

        @Mock
        private ReservationRepository reservationRepository;

        @Mock
        private ReservationMapper reservationMapper;

        @Mock
        private ApplicationEventPublisher eventPublisher;

        @Mock
        private EspacioRepositorio spaceRepository;

        @Mock
        private UserRepository userRepository;

        @Mock
        private PagoService pagoService;

        @Mock
        private NotificaionService notificaionService;

        @InjectMocks
        private ReservationServiceImpl reservationService;

        @Test
        void debeRechazarReservaCuandoElEspacioEstaOcupado() {

                // Arrange: preparar datos
                UUID espacioId = UUID.randomUUID();
                String correo = "usuario@gmail.com";

                LocalDateTime inicio = LocalDateTime.of(2030, 10, 22, 10, 0);

                LocalDateTime fin = inicio.plusHours(2);

                ReservationRequest request = new ReservationRequest();
                request.setEspacioId(espacioId);
                request.setUsuario(correo);
                request.setHoraInicio(inicio);
                request.setHoraFin(fin);

                Space espacio = mock(Space.class);
                Users usuario = mock(Users.class);

                when(espacio.getId()).thenReturn(espacioId);

                when(spaceRepository.findById(espacioId))
                                .thenReturn(Optional.of(espacio));

                // Ahora buscamos al usuario por correo
                when(userRepository.findByCorreo(correo))
                                .thenReturn(Optional.of(usuario));

                // Simulamos que el espacio está ocupado
                when(reservationRepository.existeSolapamiento(
                                espacioId,
                                inicio,
                                fin,
                                ReservationStatus.CANCELLED)).thenReturn(true);

                // Act: ejecutar el método
                SpaceOccupiedException error = assertThrows(
                                SpaceOccupiedException.class,
                                () -> reservationService.create(request));

                // Assert: verificar resultado
                assertEquals(
                                "El espacio ya está reservado en el horario solicitado",
                                error.getMessage());

                // Verificar que nunca guardó la reserva
                verify(reservationRepository, never())
                                .save(any(Reservation.class));

                // Verificar que no se utilizó el mapper
                verifyNoInteractions(reservationMapper);
        }
}