package com.example.health_guardian_server.services;

import com.example.health_guardian_server.dtos.requests.appointment.ListAppointmentRequest;
import com.example.health_guardian_server.dtos.requests.physician_note.ListPhysicianNotesRequest;
import com.example.health_guardian_server.dtos.responses.appointment.AppointmentResponse;
import com.example.health_guardian_server.dtos.responses.physician_note.PhysicianNoteResponse;
import com.example.health_guardian_server.entities.PhysicianNote;
import org.springframework.data.domain.Page;

public interface PhysicianNoteService {
  Page<PhysicianNoteResponse> getAllPhysicianNotes(ListPhysicianNotesRequest request);
}
