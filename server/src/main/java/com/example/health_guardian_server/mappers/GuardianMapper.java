package com.example.health_guardian_server.mappers;

import com.example.health_guardian_server.dtos.responses.GuardianResponse;
import com.example.health_guardian_server.entities.Guardian;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GuardianMapper {

  GuardianResponse toGuardianResponse(Guardian guardian);

  Guardian toGuardian(GuardianResponse guardianResponse);
}
