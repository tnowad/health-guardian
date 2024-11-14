package com.example.health_guardian_server.controllers;

import com.example.health_guardian_server.dtos.responses.GetListMedicationResponse;
import com.example.health_guardian_server.services.MedicationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medications")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class MedicationController {

  MedicationService medicationService;

  @GetMapping("/medications")
  public ResponseEntity<GetListMedicationResponse> getMedications() {
    return ResponseEntity.ok().body(medicationService.getMedications());
  }
}
