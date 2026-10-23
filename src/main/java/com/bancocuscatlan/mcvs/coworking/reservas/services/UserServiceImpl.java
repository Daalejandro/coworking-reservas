package com.bancocuscatlan.mcvs.coworking.reservas.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import com.bancocuscatlan.mcvs.coworking.reservas.dto.mapper.UserMapper;
import com.bancocuscatlan.mcvs.coworking.reservas.dto.request.SpaceRequest;
import com.bancocuscatlan.mcvs.coworking.reservas.dto.request.UserRequest;
import com.bancocuscatlan.mcvs.coworking.reservas.dto.response.SpaceResponse;
import com.bancocuscatlan.mcvs.coworking.reservas.dto.response.UserResponse;
import com.bancocuscatlan.mcvs.coworking.reservas.entities.Roles;
import com.bancocuscatlan.mcvs.coworking.reservas.entities.Space;
import com.bancocuscatlan.mcvs.coworking.reservas.entities.Users;
import com.bancocuscatlan.mcvs.coworking.reservas.repositories.RolRepository;
import com.bancocuscatlan.mcvs.coworking.reservas.repositories.UserRepository;

import org.springframework.transaction.annotation.Transactional;

@Service 
public class UserServiceImpl implements IUserService {

    private RolRepository rolRepository;
    private UserRepository userRepositorio;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepositorio,UserMapper userMapper,RolRepository rolRepository,
        PasswordEncoder passwordEncoder) {
        
            this.passwordEncoder=passwordEncoder;
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

    @Transactional 
    @Override
    public UserResponse save(UserRequest userRequest) {

        Users usuario = userMapper.toUser(userRequest);
        
        List<Roles> listaRoles = new ArrayList<>();
        Optional<Roles> optionalRolUser = rolRepository.findByNombre("ROLE_USER");
        optionalRolUser.ifPresent(r-> listaRoles.add(r));

        if(userRequest.isAdmin()){
                Optional<Roles> optionalRolAdmin = rolRepository.findByNombre("ROLE_ADMIN");
                optionalRolAdmin.ifPresent(r-> listaRoles.add(r));
        }

        
        usuario.setRoles(listaRoles);
        usuario.setContrasenia(passwordEncoder.encode(userRequest.getContrasenia()));
        

        System.out.println(usuario.toString());
        return userMapper.toUserResponse(userRepositorio.save(usuario));
    

    }
    @Transactional
    @Override
    public Optional<UserResponse> update(UUID id, UserRequest user) {

        Optional<Users> optionaUser = userRepositorio.findById(id);

        if (optionaUser.isPresent()) {
            Users userUpdate = optionaUser.orElseThrow();
            userUpdate.setCorreo(userUpdate.getCorreo());
            userUpdate.setContrasenia(user.getContrasenia());
            userUpdate.setNombre(user.getNombre());

            return Optional.of(userMapper.toUserResponse(userRepositorio.save(userUpdate)));

        }

        return optionaUser.map(u-> userMapper.toUserResponse(u));
    }

    @Transactional 
    @Override
    public void delete(UUID id) {
      
        userRepositorio.deleteById(id);
    }

}
