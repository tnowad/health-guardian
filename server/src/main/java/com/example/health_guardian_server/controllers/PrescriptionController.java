package com.example.health_guardian_server.controllers;

import com.example.health_guardian_server.dtos.requests.CreatePrescriptionRequest;
import com.example.health_guardian_server.dtos.requests.ListPrescriptionRequest;
import com.example.health_guardian_server.dtos.responses.PrescriptionResponse;
import com.example.health_guardian_server.entities.Prescription;
import com.example.health_guardian_server.mappers.PrescriptionMapper;
import com.example.health_guardian_server.services.PrescriptionService;
import java.util.Date;
import java.util.List;
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

//  @GetMapping
//  public ResponseEntity<Page<PrescriptionResponse>> getAllPrescriptions(
//      @ModelAttribute ListPrescriptionRequest request) {
//    return new ResponseEntity<>(prescriptionService.getAllPrescriptions(request), HttpStatus.OK);
//  }
//
//  @GetMapping("/{id}")
//  public ResponseEntity<PrescriptionResponse> getPrescriptionById(@PathVariable String id) {
//    Prescription prescription = prescriptionService.getPrescriptionById(id);
//    return new ResponseEntity<>(
//        prescriptionMapper.toPrescriptionResponse(prescription), HttpStatus.OK);
//  }
//
//  @GetMapping("/patient/{patientId}")
//  public ResponseEntity<List<Prescription>> getPrescriptionsByPatientId(
//      @PathVariable String patientId) {
//    return new ResponseEntity<>(
//        prescriptionService.getPrescriptionsByPatientId(patientId), HttpStatus.OK);
//  }
//
//  @GetMapping("/medication/{medicationId}")
//  public ResponseEntity<List<Prescription>> getPrescriptionsByMedicationId(
//      @PathVariable String medicationId) {
//    return new ResponseEntity<>(
//        prescriptionService.getPrescriptionsByMedicationId(medicationId), HttpStatus.OK);
//  }
//
//  @GetMapping("/status/{status}")
//  public ResponseEntity<List<Prescription>> getPrescriptionsByStatus(@PathVariable String status) {
//    return new ResponseEntity<>(
//        prescriptionService.getPrescriptionsByStatus(status), HttpStatus.OK);
//  }
//
//  @GetMapping("/patient/{patientId}/medication/{medicationId}")
//  public ResponseEntity<List<Prescription>> getPrescriptionsByPatientIdAndMedicationId(
//      @PathVariable String patientId, @PathVariable String medicationId) {
//    return new ResponseEntity<>(
//        prescriptionService.getPrescriptionsByPatientIdAndMedicationId(patientId, medicationId),
//        HttpStatus.OK);
//  }
//
//  @GetMapping("/patient/{patientId}/date/{date}")
//  public ResponseEntity<List<Prescription>> getPrescriptionsByPatientIdAndEndDate(
//      @PathVariable String patientId, @PathVariable Date date) {
//    return new ResponseEntity<>(
//        prescriptionService.getPrescriptionsByPatientIdAndEndDate(patientId, date), HttpStatus.OK);
//  }
//
//  @PostMapping("")
//  public ResponseEntity<PrescriptionResponse> createPrescription(
//      @RequestBody CreatePrescriptionRequest request) {
//    Prescription prescription = prescriptionService.createPrescription(request);
//    PrescriptionResponse prescriptionResponse =
//        prescriptionMapper.toPrescriptionResponse(prescription);
//    return new ResponseEntity<>(prescriptionResponse, HttpStatus.OK);
//  }
}
