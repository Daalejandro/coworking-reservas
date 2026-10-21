package com.bancocuscatlan.mcvs.coworking.reservas.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.bancocuscatlan.mcvs.coworking.reservas.entities.Roles;

public interface RolRepository extends CrudRepository<Roles,Long> {

        Optional<Roles> findByNombre(String nombre);

}
