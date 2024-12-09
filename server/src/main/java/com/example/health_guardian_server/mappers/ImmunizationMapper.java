package com.example.health_guardian_server.mappers;

import com.example.health_guardian_server.dtos.requests.CreateImmunizationRequest;
import com.example.health_guardian_server.dtos.responses.ImmunizationResponse;
import com.example.health_guardian_server.entities.Immunization;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ImmunizationMapper {
  // Define methods

  @Mapping(source = "user.id", target = "userId")
  ImmunizationResponse toResponse(Immunization immunization);

  Immunization toImmunization(CreateImmunizationRequest immunization);
}
