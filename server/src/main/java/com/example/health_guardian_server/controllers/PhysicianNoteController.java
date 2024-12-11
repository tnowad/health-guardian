package com.example.health_guardian_server.controllers;

import com.example.health_guardian_server.dtos.requests.appointment.ListAppointmentRequest;
import com.example.health_guardian_server.dtos.requests.physician_note.ListPhysicianNotesRequest;
import com.example.health_guardian_server.dtos.responses.appointment.AppointmentResponse;
import com.example.health_guardian_server.dtos.responses.physician_note.PhysicianNoteResponse;
import com.example.health_guardian_server.entities.PhysicianNote;
import com.example.health_guardian_server.services.PhysicianNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/physician-notes")
@RequiredArgsConstructor
public class PhysicianNoteController {

  private final PhysicianNoteService physicianNoteService;

  @GetMapping
  public ResponseEntity<Page<PhysicianNoteResponse>> getAllPhysicianNotes(
    @ModelAttribute ListPhysicianNotesRequest request) {
    Page<PhysicianNoteResponse> physicianNotes = physicianNoteService.getAllPhysicianNotes(request);
    return new ResponseEntity<>(physicianNotes, HttpStatus.OK);
  }
}
