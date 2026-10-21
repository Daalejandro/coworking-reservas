package com.bancocuscatlan.mcvs.coworking.reservas.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpaceRequest {

    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no debe superar los 100 caracteres")
    private String nombre;

    @NotBlank(message = "El tipo es obligatorio")
    @Size(max = 50, message = "El tipo no debe superar los 50 caracteres")
    private String tipo;

    @NotNull(message = "La capacidad es obligatoria")
    @Positive(message = "La capacidad debe ser mayor que cero")
    private Integer capacidad;

    @NotBlank(message = "La ubicación es obligatoria")
    @Size(max = 200, message = "La ubicación no debe superar los 200 caracteres")
    private String ubicacion;

    @NotNull(message = "La tarifa es obligatoria")
    @DecimalMin(value = "0.0", inclusive = false, message = "La tarifa debe ser mayor que cero")
    @Digits(integer = 8, fraction = 2, message = "La tarifa admite hasta 8 enteros y 2 decimales")
    private BigDecimal tarifa;

    @NotNull(message = "Debes indicar si el espacio está disponible")
    private Boolean disponible;

}
