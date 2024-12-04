package com.example.health_guardian_server.controllers;

import com.example.health_guardian_server.dtos.requests.CreateMedicationRequest;
import com.example.health_guardian_server.dtos.requests.ListMedicationRequest;
import com.example.health_guardian_server.dtos.requests.UpdateMedicationRequest;
import com.example.health_guardian_server.dtos.responses.MedicationResponse;
import com.example.health_guardian_server.dtos.responses.SimpleResponse;
import com.example.health_guardian_server.entities.Medication;
import com.example.health_guardian_server.mappers.MedicationMapper;
import com.example.health_guardian_server.services.MedicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
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
@Slf4j
public class MedicationController {
  private final MedicationService medicationService;

  @Autowired
  private final MedicationMapper medicationMapper;

  @GetMapping
  public ResponseEntity<Page<MedicationResponse>> listMedications(
      @ModelAttribute ListMedicationRequest request) {
    Page<MedicationResponse> medications = medicationService.listMedications(request);
    return new ResponseEntity<>(medications, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<MedicationResponse> createMedication(
      @RequestBody CreateMedicationRequest request) {
    MedicationResponse response = medicationService.createMedication(request);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/{medicationId}")
  public ResponseEntity<MedicationResponse> getMedication(@PathVariable String medicationId) {
    MedicationResponse response = medicationService.getMedication(medicationId);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{medicationId}")
  public ResponseEntity<MedicationResponse> updateMedication(
      @PathVariable String medicationId, @RequestBody UpdateMedicationRequest request) {
    MedicationResponse response = medicationService.updateMedication(medicationId, request);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @DeleteMapping("/{medicationId}")
  public ResponseEntity<SimpleResponse> deleteMedication(@PathVariable String medicationId) {
    Medication medication = medicationService.deleteMedication(medicationId);
    SimpleResponse simpleResponse = MedicationMapper.toMedicationSimpleResponse(medication);
    return new ResponseEntity<>(simpleResponse, HttpStatus.OK);
  }
}
