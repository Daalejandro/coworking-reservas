package com.bancocuscatlan.mcvs.coworking.reservas.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.bancocuscatlan.mcvs.coworking.reservas.dto.request.UserRequest;
import com.bancocuscatlan.mcvs.coworking.reservas.dto.response.UserResponse;

public interface IUserService {

    List<UserResponse> findAll();
    
    Optional<UserResponse> findById(UUID id);

    UserResponse save(UserRequest userRequest);

    Optional<UserResponse> update(UUID id, UserRequest userRequest);

    void delete(UUID id);

}
