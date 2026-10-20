package com.bancocuscatlan.mcvs.coworking.reservas.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancocuscatlan.mcvs.coworking.reservas.dto.response.SpaceResponse;
import com.bancocuscatlan.mcvs.coworking.reservas.services.ISpaceService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


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
    

}
