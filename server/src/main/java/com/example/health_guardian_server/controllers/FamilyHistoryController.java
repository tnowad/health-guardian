package com.example.health_guardian_server.controllers;

import com.example.health_guardian_server.dtos.requests.appointment.ListAppointmentRequest;
import com.example.health_guardian_server.dtos.requests.family_history.ListFamilyHistoryRequest;
import com.example.health_guardian_server.dtos.responses.appointment.AppointmentResponse;
import com.example.health_guardian_server.dtos.responses.family_history.FamilyHistoryResponse;
import com.example.health_guardian_server.services.FamilyHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/family-histories")
@RequiredArgsConstructor
public class FamilyHistoryController {

  private final FamilyHistoryService familyHistoryService;

  @GetMapping
  public ResponseEntity<Page<FamilyHistoryResponse>> getAllAppointments(
    @ModelAttribute ListFamilyHistoryRequest request) {
    Page<FamilyHistoryResponse> familyHistories = familyHistoryService.getAllFamilyHistories(request);
    return new ResponseEntity<>(familyHistories, HttpStatus.OK);
  }
}
