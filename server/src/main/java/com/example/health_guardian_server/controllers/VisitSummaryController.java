package com.example.health_guardian_server.controllers;

import com.example.health_guardian_server.dtos.requests.appointment.CreateAppointmentRequest;
import com.example.health_guardian_server.dtos.requests.appointment.ListAppointmentRequest;
import com.example.health_guardian_server.dtos.requests.appointment.UpdateAppointmentRequest;
import com.example.health_guardian_server.dtos.requests.visit_summary.CreateVisitSummaryRequest;
import com.example.health_guardian_server.dtos.requests.visit_summary.ListVisitSummaryRequest;
import com.example.health_guardian_server.dtos.requests.visit_summary.UpdateVisitSummaryRequest;
import com.example.health_guardian_server.dtos.responses.SimpleResponse;
import com.example.health_guardian_server.dtos.responses.appointment.AppointmentResponse;
import com.example.health_guardian_server.dtos.responses.visit_summary.VisitSummaryResponse;
import com.example.health_guardian_server.entities.Appointment;
import com.example.health_guardian_server.mappers.AppointmentMapper;
import com.example.health_guardian_server.services.VisitSummaryService;
import com.example.health_guardian_server.services.impl.VisitSummaryServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("visit-summaries")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class VisitSummaryController {

  private VisitSummaryServiceImpl visitSummaryService;

  @GetMapping
  public ResponseEntity<Page<VisitSummaryResponse>> getAllVisitSummaries(
    @ModelAttribute ListVisitSummaryRequest request) {
    Page<VisitSummaryResponse> visitSummaries = visitSummaryService.getAllVisitSummaries(request);
    return new ResponseEntity<>(visitSummaries, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<VisitSummaryResponse> getVisitSummaryById(@PathVariable String id) {
    VisitSummaryResponse visitSummary = visitSummaryService.getVisitSummaryById(id);
    return new ResponseEntity<>(visitSummary, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<VisitSummaryResponse> createVisitSummary(
    @RequestBody CreateVisitSummaryRequest request) {
    VisitSummaryResponse visitSummary = visitSummaryService.createVisitSummary(request);
    return new ResponseEntity<>(visitSummary, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<VisitSummaryResponse> updateAppointment(
    @PathVariable String id, @RequestBody UpdateVisitSummaryRequest request) {
    VisitSummaryResponse visitSummary = visitSummaryService.updateVisitSummary(id, request);
    return new ResponseEntity<>(visitSummary, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<SimpleResponse> deleteAppointment(@PathVariable String id) {
    return new ResponseEntity<>(visitSummaryService.deleteVisitSummary(id), HttpStatus.OK);
  }
}
