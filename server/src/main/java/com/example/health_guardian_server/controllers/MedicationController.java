package com.example.health_guardian_server.controllers;

import com.example.health_guardian_server.dtos.requests.CreateMedicationRequest;
import com.example.health_guardian_server.dtos.requests.ListMedicationRequest;
import com.example.health_guardian_server.dtos.requests.UpdateMedicationRequest;
import com.example.health_guardian_server.dtos.responses.MedicationResponse;
import com.example.health_guardian_server.services.MedicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medications")
@RequiredArgsConstructor
public class MedicationController {
  private final MedicationService medicationService;

  @GetMapping
  public ResponseEntity<Page<MedicationResponse>> listMedications(
      @ModelAttribute ListMedicationRequest request) {
    Page<MedicationResponse> response = medicationService.listMedications(request);
    return ResponseEntity.ok(response);
  }

  @PostMapping
  public ResponseEntity<MedicationResponse> createMedication(
      @RequestBody CreateMedicationRequest request) {
    MedicationResponse response = medicationService.createMedication(request);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{mediationId}")
  public ResponseEntity<MedicationResponse> getMedication(@PathVariable String medicationId) {
    MedicationResponse response = medicationService.getMedication(medicationId);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{medicationId}")
  public ResponseEntity<MedicationResponse> updateMedication(
      @PathVariable String medicationId, @RequestBody UpdateMedicationRequest request) {
    MedicationResponse response = medicationService.updateMedication(medicationId, request);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{medicationId}")
  public ResponseEntity<Void> deleteMedication(@PathVariable String medicationId) {
    medicationService.deleteMedication(medicationId);
    return ResponseEntity.noContent().build();
  }
}
