package com.example.health_guardian_server.mappers;

import org.mapstruct.Mapper;

import com.example.health_guardian_server.dtos.responses.RoleResponse;
import com.example.health_guardian_server.entities.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleResponse toRoleResponse(Role role);

}
