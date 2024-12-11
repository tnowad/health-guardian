package com.example.health_guardian_server.controllers;

import com.example.health_guardian_server.dtos.requests.prescription.CreatePrescriptionRequest;
import com.example.health_guardian_server.dtos.requests.prescription.ListPrescriptionRequest;
import com.example.health_guardian_server.dtos.responses.prescription.PrescriptionResponse;
import com.example.health_guardian_server.entities.Prescription;
import com.example.health_guardian_server.mappers.PrescriptionMapper;
import com.example.health_guardian_server.services.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/prescriptions")
public class PrescriptionController {
  private final PrescriptionService prescriptionService;

  private final PrescriptionMapper prescriptionMapper;

  @GetMapping
  public ResponseEntity<Page<PrescriptionResponse>> getAllPrescriptions(
      @ModelAttribute ListPrescriptionRequest request) {
    return new ResponseEntity<>(prescriptionService.getAllPrescriptions(request), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<PrescriptionResponse> getPrescriptionById(@PathVariable String id) {
    Prescription prescription = prescriptionService.getPrescriptionById(id);
    return new ResponseEntity<>(
        prescriptionMapper.toPrescriptionResponse(prescription), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<PrescriptionResponse> createPrescription(
      @RequestBody CreatePrescriptionRequest request) {
    Prescription prescription = prescriptionService.createPrescription(request);
    PrescriptionResponse prescriptionResponse =
        prescriptionMapper.toPrescriptionResponse(prescription);
    return new ResponseEntity<>(prescriptionResponse, HttpStatus.OK);
  }
}
