package com.example.health_guardian_server.mappers;

import com.example.health_guardian_server.dtos.responses.AppointmentResponse;
import com.example.health_guardian_server.entities.Appointment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
  // Define methods

  AppointmentResponse toAppointmentResponse(Appointment appointment);

  Appointment toAppointment(AppointmentMapper appointmentResponse);
}
