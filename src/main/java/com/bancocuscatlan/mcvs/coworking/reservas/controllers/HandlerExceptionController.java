package com.bancocuscatlan.mcvs.coworking.reservas.controllers;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.bancocuscatlan.mcvs.coworking.reservas.exceptions.Error;
import com.bancocuscatlan.mcvs.coworking.reservas.exceptions.ReservedNotFoundException;
import com.bancocuscatlan.mcvs.coworking.reservas.exceptions.SpaceNotFoudException;
import com.bancocuscatlan.mcvs.coworking.reservas.exceptions.SpaceOccupiedException;

import tools.jackson.databind.exc.InvalidFormatException;

@RestControllerAdvice
public class HandlerExceptionController {

    @ExceptionHandler(InvalidFormatException.class) // Valida un valor con formato invalido en formato json
    public ResponseEntity<Error> badrequest(InvalidFormatException e) {

        String campo = e.getPath().get(0).getPropertyName();

        Error error = new Error();
        error.setDate(new Date());
        error.setError("El campo '" + campo + "' tiene un formato inválido");
        error.setMessage("El valor enviado no es válido para este campo");
        error.setStatus(HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class) // Valida un valor con formato invalido en la url
    public ResponseEntity<Error> parametroIncorrecto(
            MethodArgumentTypeMismatchException e) {

        Error error = new Error();
        error.setDate(new Date());
        error.setError("El parámetro '" + e.getName() + "' tiene un formato inválido");
        error.setMessage("El valor enviado no corresponde al tipo de dato requerido");
        error.setStatus(HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(NoResourceFoundException.class) // validamos las rutas existan
    public ResponseEntity<Error> notFoundEx(NoResourceFoundException e) {
        Error error = new Error();
        error.setDate(new Date());
        error.setError("Api rest no encontrada");
        error.setMessage(e.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(error);

    }

    @ExceptionHandler(SpaceNotFoudException.class)
    public ResponseEntity<Error> SpaceNotFounException(Exception e) {
        Error error = new Error();
        error.setDate(new Date());
        error.setError("Espacio no encontrado");
        error.setMessage(e.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(error);

    }

    @ExceptionHandler(ReservedNotFoundException.class)
    public ResponseEntity<Error> ReservedNotFoundException(Exception e) {
        Error error = new Error();
        error.setDate(new Date());
        error.setError("Reserva no encontrado");
        error.setMessage(e.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(error);

    }

    @ExceptionHandler(SpaceOccupiedException.class)
    public ResponseEntity<Error> SpaceOccupiedException(Exception e) {
        Error error = new Error();
        error.setDate(new Date());
        error.setError("Espacio no disponible");
        error.setMessage(e.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(error);

    }

}
