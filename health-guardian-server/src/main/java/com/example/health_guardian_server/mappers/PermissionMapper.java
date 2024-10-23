package com.example.health_guardian_server.mappers;

import org.mapstruct.Mapper;

import com.example.health_guardian_server.dtos.responses.PermissionResponse;
import com.example.health_guardian_server.entities.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    PermissionResponse toPermissionResponse(Permission permission);

}
