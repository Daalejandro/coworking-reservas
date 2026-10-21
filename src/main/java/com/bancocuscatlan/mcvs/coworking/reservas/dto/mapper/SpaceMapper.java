package com.bancocuscatlan.mcvs.coworking.reservas.dto.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bancocuscatlan.mcvs.coworking.reservas.dto.request.SpaceRequest;
import com.bancocuscatlan.mcvs.coworking.reservas.dto.response.SpaceResponse;
import com.bancocuscatlan.mcvs.coworking.reservas.entities.Space;

@Mapper(componentModel = "spring")
public interface SpaceMapper {

    @Mapping(target = "id", ignore = true)
    Space toSpace(SpaceRequest spaceRequest);

    SpaceResponse toSpaceResponse(Space space);

    List<SpaceResponse> toResponseList(List<Space> spaces);

}
