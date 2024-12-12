package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.physician_note.ListPhysicianNotesRequest;
import com.example.health_guardian_server.dtos.responses.physician_note.PhysicianNoteResponse;
import com.example.health_guardian_server.entities.PhysicianNote;
import com.example.health_guardian_server.mappers.PhysicianNoteMapper;
import com.example.health_guardian_server.repositories.PhysicianNoteRepository;
import com.example.health_guardian_server.services.PhysicianNoteService;
import com.example.health_guardian_server.specifications.AppointmentSpecification;
import com.example.health_guardian_server.specifications.PhysicianNoteSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PhysicianNoteServiceImpl implements PhysicianNoteService {

  private final PhysicianNoteRepository physicianNoteRepository;
  private final PhysicianNoteMapper physicianNoteMapper;

  @Override
  public Page<PhysicianNoteResponse> getAllPhysicianNotes(ListPhysicianNotesRequest request) {
    log.debug("Fetching all physician notes with request: {}", request);
    PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
    PhysicianNoteSpecification specification = new PhysicianNoteSpecification(request);

    var physicianNotes =
      physicianNoteRepository
        .findAll(specification, pageRequest)
        .map(physicianNoteMapper::toResponse);

    log.info("Fetched {} physician notes", physicianNotes.getTotalElements());
    return physicianNotes;
  }
}
