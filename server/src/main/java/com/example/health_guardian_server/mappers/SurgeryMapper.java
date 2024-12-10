package com.example.health_guardian_server.mappers;

import com.example.health_guardian_server.dtos.requests.CreateSurgeryRequest;
import com.example.health_guardian_server.dtos.responses.SurgeryResponse;
import com.example.health_guardian_server.entities.Surgery;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SurgeryMapper {

  @Mapping(source = "user.id", target = "userId")
  SurgeryResponse toResponse(Surgery surgery);

  Surgery toSurgery(CreateSurgeryRequest surgery);
}
