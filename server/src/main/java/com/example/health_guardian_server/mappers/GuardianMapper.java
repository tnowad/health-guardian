package com.example.health_guardian_server.mappers;

import com.example.health_guardian_server.dtos.responses.GuardianResponse;
import com.example.health_guardian_server.entities.Guardian;
import com.example.health_guardian_server.entities.Patient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GuardianMapper {
  // Define methods

  GuardianResponse toGuardianResponse(Guardian guardian);

  Guardian toGuardian(GuardianResponse guardianResponse);
}
