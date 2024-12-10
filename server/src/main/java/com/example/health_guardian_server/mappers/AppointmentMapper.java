package com.example.health_guardian_server.mappers;

import com.example.health_guardian_server.dtos.requests.CreateAppointmentRequest;
import com.example.health_guardian_server.dtos.responses.AppointmentResponse;
import com.example.health_guardian_server.dtos.responses.SimpleResponse;
import com.example.health_guardian_server.entities.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
  // Define methods
  @Mapping(source = "user.id", target = "userId")
  AppointmentResponse toResponse(Appointment appointment);

  AppointmentResponse toAppointmentResponse(Appointment appointment);

  Appointment toAppointment(CreateAppointmentRequest createAppointmentRequest);

  static SimpleResponse toAppointmentSimpleResponse(Appointment appointment) {
    return SimpleResponse.builder()
        .id(appointment.getId())
        .message("AppointmentId: " + appointment.getId())
        .build();
  }
}
