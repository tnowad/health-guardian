package com.example.health_guardian_server.controllers;

import com.example.health_guardian_server.entities.Prescription;
import com.example.health_guardian_server.services.PrescriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/prescriptions")
public class PrescriptionController {
  private final PrescriptionService prescriptionService;

  public PrescriptionController(PrescriptionService prescriptionService) {
    this.prescriptionService = prescriptionService;
  }
  // Define methods

  @GetMapping("/all")
  public ResponseEntity<List<Prescription>> getAllPrescriptions() {
    return new ResponseEntity<>(prescriptionService.getAllPrescriptions(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Prescription> getPrescriptionById(@PathVariable String id) {
    Prescription prescription = prescriptionService.getPrescriptionById(id);
    return new ResponseEntity<>(prescription, HttpStatus.OK);
  }

  @GetMapping("/patient/{patientId}")
  public ResponseEntity<List<Prescription>> getPrescriptionsByPatientId(@PathVariable String patientId) {
    return new ResponseEntity<>(prescriptionService.getPrescriptionsByPatientId(patientId), HttpStatus.OK);
  }



  @GetMapping("/medication/{medicationId}")
  public ResponseEntity<List<Prescription>> getPrescriptionsByMedicationId(@PathVariable String medicationId) {
    return new ResponseEntity<>(prescriptionService.getPrescriptionsByMedicationId(medicationId), HttpStatus.OK);
  }
  @GetMapping("/status/{status}")
  public ResponseEntity<List<Prescription>> getPrescriptionsByStatus(@PathVariable String status) {
    return new ResponseEntity<>(prescriptionService.getPrescriptionsByStatus(status), HttpStatus.OK);
  }

  @GetMapping("/patient/{patientId}/medication/{medicationId}")
  public ResponseEntity<List<Prescription>> getPrescriptionsByPatientIdAndMedicationId(@PathVariable String patientId, @PathVariable String medicationId) {
    return new ResponseEntity<>(prescriptionService.getPrescriptionsByPatientIdAndMedicationId(patientId, medicationId), HttpStatus.OK);
  }

  @GetMapping("/patient/{patientId}/date/{date}")
  public ResponseEntity<List<Prescription>> getPrescriptionsByPatientIdAndEndDate(@PathVariable String patientId, @PathVariable Date date) {
    return new ResponseEntity<>(prescriptionService.getPrescriptionsByPatientIdAndEndDate(patientId, date), HttpStatus.OK);
  }

  @GetMapping("/patient/{patientId}/status/{status}")
  public ResponseEntity<List<Prescription>> getPrescriptionsByPatientIdAndStatus(@PathVariable String patientId, @PathVariable String status) {
    return new ResponseEntity<>(prescriptionService.getPrescriptionsByPatientIdAndStatus(patientId, status), HttpStatus.OK);
  }


}
