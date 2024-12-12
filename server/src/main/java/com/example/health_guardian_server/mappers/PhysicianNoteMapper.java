package com.example.health_guardian_server.mappers;

import com.example.health_guardian_server.dtos.responses.appointment.AppointmentResponse;
import com.example.health_guardian_server.dtos.responses.physician_note.PhysicianNoteResponse;
import com.example.health_guardian_server.entities.Appointment;
import com.example.health_guardian_server.entities.PhysicianNote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PhysicianNoteMapper {

  @Mapping(source = "user.id", target = "userId")
  PhysicianNoteResponse toResponse(PhysicianNote physicianNote);
}
