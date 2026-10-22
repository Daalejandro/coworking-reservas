package com.bancocuscatlan.mcvs.coworking.reservas.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.bancocuscatlan.mcvs.coworking.reservas.entities.Users;

public interface UserRepository  extends CrudRepository<Users,UUID>{


    Optional<Users> findByCorreo(String username);
}
