package com.bancocuscatlan.mcvs.coworking.reservas.dto.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bancocuscatlan.mcvs.coworking.reservas.dto.request.UserRequest;
import com.bancocuscatlan.mcvs.coworking.reservas.dto.response.UserResponse;
import com.bancocuscatlan.mcvs.coworking.reservas.entities.Users;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    Users toUser(UserRequest userRequest);

    UserResponse toUserResponse(Users users);

    List<UserResponse> toUserResponseList(List<Users> users);
}
