package com.example.health_guardian_server.mappers;

import com.example.health_guardian_server.dtos.responses.appointment.AppointmentResponse;
import com.example.health_guardian_server.dtos.responses.family_history.FamilyHistoryResponse;
import com.example.health_guardian_server.entities.Appointment;
import com.example.health_guardian_server.entities.FamilyHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FamilyHistoryMapper {

  @Mapping(source = "user.id", target = "userId")
  FamilyHistoryResponse toResponse(FamilyHistory familyHistory);
}
