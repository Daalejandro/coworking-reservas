package com.bancocuscatlan.mcvs.coworking.reservas.controllers;

import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancocuscatlan.mcvs.coworking.reservas.dto.request.UserRequest;
import com.bancocuscatlan.mcvs.coworking.reservas.dto.response.UserResponse;
import com.bancocuscatlan.mcvs.coworking.reservas.services.IUserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private IUserService service;

    public UserController(IUserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody UserRequest userRequest, BindingResult result) {

        if (result.hasFieldErrors()) {
            return validate(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(userRequest));
    }

    @PostMapping("/create")
    public ResponseEntity<?> registrarse(@Valid @RequestBody UserRequest userRequest, BindingResult result) {
        userRequest.setAdmin(false);
        return create(userRequest, result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid@RequestBody UserRequest user,BindingResult result,@PathVariable UUID id) {

        if (result.hasFieldErrors()) {
            return validate(result);
        }

        Optional<UserResponse> optionalUser  = service.update(id, user);
        if(optionalUser.isPresent()){
            return  ResponseEntity.status(HttpStatus.OK).body(optionalUser);
        }

         return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    public ResponseEntity<?> validate(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(e -> {
            errors.put(e.getField(), " El campo " + e.getField() + " " + e.getDefaultMessage());
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);

    }

}
