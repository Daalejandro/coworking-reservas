package com.bancocuscatlan.mcvs.coworking.reservas.services;

import com.bancocuscatlan.mcvs.coworking.reservas.dto.mapper.SpaceMapper;
import com.bancocuscatlan.mcvs.coworking.reservas.dto.request.SpaceRequest;
import com.bancocuscatlan.mcvs.coworking.reservas.dto.response.SpaceResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bancocuscatlan.mcvs.coworking.reservas.entities.Space;
import com.bancocuscatlan.mcvs.coworking.reservas.exceptions.SpaceNotFoudException;
import com.bancocuscatlan.mcvs.coworking.reservas.repositories.EspacioRepositorio;

@Service
public class SpaceServiceImpl implements ISpaceService {

    private SpaceMapper spaceMapper;
    private EspacioRepositorio repositorio;

    public SpaceServiceImpl(SpaceMapper spaceMapper, EspacioRepositorio repositorio) {
        this.spaceMapper = spaceMapper;
        this.repositorio = repositorio;
    }

    @Transactional(readOnly = true)
    @Override
    public List<SpaceResponse> findAll() {
        List<Space> list = (List<Space>) repositorio.findAll();

        return spaceMapper.toResponseList(list);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<SpaceResponse> findById(UUID id) {

        Space optionalspace =repositorio.findById(id).orElseThrow(()-> new SpaceNotFoudException("No se encuentra el espacio"));

        return  Optional.of(spaceMapper.toSpaceResponse(optionalspace));

        

    }

    @Override
    public SpaceResponse save(SpaceRequest espacios) {

        Space space = repositorio.save(spaceMapper.toSpace(espacios));
        return spaceMapper.toSpaceResponse(space);

    }

    @Override
    public Optional<SpaceResponse> update(UUID id, SpaceRequest space) {

        Optional<Space> optionalSpace = repositorio.findById(id);

        if (optionalSpace.isPresent()) {
            Space spaceUpdate = optionalSpace.orElseThrow();
            spaceUpdate.setNombre(space.getNombre());
            spaceUpdate.setTipo(space.getTipo());
            spaceUpdate.setCapacidad(space.getCapacidad());
            spaceUpdate.setUbicacion(space.getUbicacion());
            spaceUpdate.setTarifa(space.getTarifa());
            spaceUpdate.setDisponible(space.getDisponible());

            return Optional.of(spaceMapper.toSpaceResponse(repositorio.save(spaceUpdate)));

        }

        return optionalSpace.map(s-> spaceMapper.toSpaceResponse(s));
    }

    


    @Override
    public void delete(UUID id) {
      
        repositorio.deleteById(id);
    }


}
