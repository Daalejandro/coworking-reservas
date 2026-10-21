package com.bancocuscatlan.mcvs.coworking.reservas.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancocuscatlan.mcvs.coworking.reservas.dto.request.SpaceRequest;
import com.bancocuscatlan.mcvs.coworking.reservas.dto.response.SpaceResponse;
import com.bancocuscatlan.mcvs.coworking.reservas.services.ISpaceService;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/space")
public class SpaceControllers {

    private ISpaceService service;

    public SpaceControllers(ISpaceService service) {
        this.service = service;

    }

    @GetMapping()
    public ResponseEntity<List<SpaceResponse>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<SpaceResponse>> findAll(@PathVariable UUID id) {

        Optional<SpaceResponse> optionalSpace = service.findById(id);

        if (optionalSpace.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(optionalSpace);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody SpaceRequest space, BindingResult result) {

        if(result.hasFieldErrors()){
           return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(space));
    }

    @PutMapping("/{id}")
    public Optional<SpaceResponse> update(@PathVariable UUID id, @RequestBody SpaceRequest space) {
        return service.update(id, space);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo: " + err.getField() + " " + err.getDefaultMessage());
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    

}
