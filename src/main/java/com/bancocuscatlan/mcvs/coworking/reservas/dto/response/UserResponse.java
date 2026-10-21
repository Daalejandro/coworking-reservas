package com.bancocuscatlan.mcvs.coworking.reservas.dto.response;


import java.util.List;
import java.util.UUID;


import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
public class UserResponse {

    private UUID id;

    private String nombre;

    private String correo;

    private List<RoleResponse> roles;
    
  
}
