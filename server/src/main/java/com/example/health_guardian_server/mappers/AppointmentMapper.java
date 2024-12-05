package com.example.health_guardian_server.mappers;

import com.example.health_guardian_server.dtos.requests.CreateAppointmentRequest;
import com.example.health_guardian_server.dtos.responses.AppointmentResponse;
import com.example.health_guardian_server.dtos.responses.SimpleResponse;
import com.example.health_guardian_server.entities.Appointment;
import com.example.health_guardian_server.entities.Medication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
  // Define methods
  @Mapping(source = "patient.id", target = "patientId")
  @Mapping(source = "doctor.id", target = "doctorId")
  AppointmentResponse toResponse(Appointment appointment);

  AppointmentResponse toAppointmentResponse(Appointment appointment);

  Appointment toAppointment(CreateAppointmentRequest createAppointmentRequest);

  static SimpleResponse toAppointmentSimpleResponse(Appointment appointment) {
    return SimpleResponse.builder()
      .id(appointment.getId())
      .message("MedicationId: " + appointment.getId())
      .build();
  }
}
