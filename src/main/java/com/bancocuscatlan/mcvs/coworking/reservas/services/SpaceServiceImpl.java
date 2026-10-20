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
import com.bancocuscatlan.mcvs.coworking.reservas.repositories.EspacioRepositorio;


@Service 
public class SpaceServiceImpl implements ISpaceService {

    private SpaceMapper spaceMapper;
    private EspacioRepositorio repositorio;

    public SpaceServiceImpl(SpaceMapper spaceMapper, EspacioRepositorio repositorio) {
        this.spaceMapper = spaceMapper;
        this.repositorio = repositorio;
    }

    @Override
    public List<SpaceResponse> findAll() {
        List<Space> list = (List<Space>) repositorio.findAll();

        return spaceMapper.toResponseList(list);
    }

    @Override
    public Optional<SpaceResponse> findById(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public SpaceResponse save(SpaceRequest espacios) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public Optional<SpaceResponse> update(UUID id, SpaceRequest space) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }



/* 
            Space sP = optionalSpace.orElseThrow();
            sP.setNombre(space.getNombre());
            sP.setTipo(space.getTipo());
            sP.setCapacidad(space.getCapacidad());
            sP.setUbicacion(space.getUbicacion());
            sP.setDisponible(space.isDisponible());

            return Optional.of(repositorio.save(sP));
 */

}
