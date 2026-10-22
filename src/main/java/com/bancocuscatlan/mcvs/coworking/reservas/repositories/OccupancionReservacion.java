package com.bancocuscatlan.mcvs.coworking.reservas.repositories;

import java.time.LocalDateTime;
import java.util.UUID;




public interface OccupancionReservacion {
    
    UUID getEspacioId(); 
    
    LocalDateTime getHoraInicio(); 

    LocalDateTime getHoraFin();
}
