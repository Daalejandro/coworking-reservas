package com.bancocuscatlan.mcvs.coworking.reservas.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


import org.springframework.stereotype.Service;

import com.bancocuscatlan.mcvs.coworking.reservas.dto.mapper.UserMapper;
import com.bancocuscatlan.mcvs.coworking.reservas.dto.request.UserRequest;
import com.bancocuscatlan.mcvs.coworking.reservas.dto.response.UserResponse;
import com.bancocuscatlan.mcvs.coworking.reservas.entities.Roles;
import com.bancocuscatlan.mcvs.coworking.reservas.entities.Users;
import com.bancocuscatlan.mcvs.coworking.reservas.repositories.RolRepository;
import com.bancocuscatlan.mcvs.coworking.reservas.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service 
public class UserServiceImpl implements IUserService {

    private RolRepository rolRepository;
    private UserRepository userRepositorio;
    private UserMapper userMapper;


    public UserServiceImpl(UserRepository userRepositorio,UserMapper userMapper,RolRepository rolRepository) {

        this.rolRepository = rolRepository;
        this.userMapper = userMapper;
        this.userRepositorio = userRepositorio;
    }

    @Transactional 
    @Override
    public List<UserResponse> findAll() {

        List<Users> usuarios = (List<Users>)userRepositorio.findAll();

        return userMapper.toUserResponseList(usuarios);
       
    }

    @Transactional 
    @Override
    public Optional<UserResponse> findById(UUID id) {
      
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public UserResponse save(UserRequest userRequest) {

        Users usuario = userMapper.toUser(userRequest);
        
        List<Roles> listaRoles = new ArrayList<>();
        Optional<Roles> optionalRolUser = rolRepository.findByNombre("ROLE_USER");
        optionalRolUser.ifPresent(r-> listaRoles.add(r));

        if(userRequest.isAdmin()){
                Optional<Roles> optionalRolAdmin = rolRepository.findByNombre("ROL_ADMIN");
                optionalRolAdmin.ifPresent(r-> listaRoles.add(r));
        }

        
        usuario.setRoles(listaRoles);

        System.out.println(usuario.toString());
        return userMapper.toUserResponse(userRepositorio.save(usuario));
    

    }

    @Override
    public Optional<UserResponse> update(UUID id, UserRequest userRequest) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(UUID id) {
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    


}
