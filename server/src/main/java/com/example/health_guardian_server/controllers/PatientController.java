package com.example.health_guardian_server.controllers;

import com.example.health_guardian_server.dtos.requests.CreatePatientRequest;
import com.example.health_guardian_server.dtos.requests.ListPatientRequest;
import com.example.health_guardian_server.dtos.responses.PatientResponse;
import com.example.health_guardian_server.entities.Patient;
import com.example.health_guardian_server.services.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

  private final PatientService patientService;

  @GetMapping
  public ResponseEntity<Page<PatientResponse>> getAllPatients(
      @ModelAttribute ListPatientRequest request) {
    Page<PatientResponse> patients = patientService.getAllPatients(request);

    return new ResponseEntity<>(patients, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<PatientResponse> getPatientById(String id) {
    PatientResponse patient = patientService.getPatientById(id);
    return new ResponseEntity<>(patient, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<PatientResponse> createPatient(@RequestBody CreatePatientRequest patient) {
    PatientResponse createdPatient = patientService.createPatient(patient);
    return new ResponseEntity<>(createdPatient, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<PatientResponse> updatePatient(String id, Patient patient) {
    PatientResponse updatedPatient = patientService.updatePatient(id, patient);
    return new ResponseEntity<>(updatedPatient, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePatient(String id) {
    patientService.deletePatient(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
