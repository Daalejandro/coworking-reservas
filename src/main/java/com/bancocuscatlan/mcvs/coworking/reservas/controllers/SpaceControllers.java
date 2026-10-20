package com.bancocuscatlan.mcvs.coworking.reservas.controllers;

import com.bancocuscatlan.mcvs.coworking.reservas.repositories.EspacioRepositorio;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancocuscatlan.mcvs.coworking.reservas.dto.request.SpaceRequest;
import com.bancocuscatlan.mcvs.coworking.reservas.dto.response.SpaceResponse;
import com.bancocuscatlan.mcvs.coworking.reservas.services.ISpaceService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController 
@RequestMapping ("/api/space")
public class SpaceControllers {


  
    private ISpaceService service;

    
    public SpaceControllers(ISpaceService service) {
        this.service = service;
      
    }



    @GetMapping()
    public List<SpaceResponse> findAll() {
        return service.findAll();
    }
    
    @GetMapping("/{id}")
    public Optional<SpaceResponse> findAll(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PostMapping()
    public SpaceResponse postMethodName(@RequestBody SpaceRequest space) {
        
        return service.save(space);
    }

    @PutMapping("/{id}")
    public Optional<SpaceResponse> update(@PathVariable UUID id,@RequestBody SpaceRequest space){
        return service.update(id, space);
    }

    @DeleteMapping("/{id}") 
    public void delete(@PathVariable UUID id){
         service.delete(id);
    }
    


}
