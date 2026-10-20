package com.bancocuscatlan.mcvs.coworking.reservas.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.bancocuscatlan.mcvs.coworking.reservas.dto.request.SpaceRequest;
import com.bancocuscatlan.mcvs.coworking.reservas.dto.response.SpaceResponse;



public interface ISpaceService {

    List<SpaceResponse> findAll();
    
    Optional<SpaceResponse> findById(UUID id);

    SpaceResponse save(SpaceRequest espacios);

    Optional<SpaceResponse> update(UUID id, SpaceRequest space);

    void delete(UUID id);

}
