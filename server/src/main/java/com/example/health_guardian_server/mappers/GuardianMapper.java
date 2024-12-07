package com.example.health_guardian_server.mappers;


import com.example.health_guardian_server.dtos.requests.CreateAppointmentRequest;
import com.example.health_guardian_server.dtos.requests.CreateGuardianRequest;
import com.example.health_guardian_server.dtos.responses.GuardianResponse;
import com.example.health_guardian_server.entities.Guardian;
import com.example.health_guardian_server.entities.Patient;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GuardianMapper {

  GuardianResponse toGuardianResponse(Guardian guardian);
  Guardian toGuardian(CreateGuardianRequest createGuardianRequest);
}
