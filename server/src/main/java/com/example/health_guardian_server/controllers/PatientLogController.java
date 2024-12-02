package com.example.health_guardian_server.controllers;

import com.example.health_guardian_server.dtos.requests.ListPatientLogRequest;
import com.example.health_guardian_server.dtos.responses.PatientLogResponse;
import com.example.health_guardian_server.entities.PatientLog;
import com.example.health_guardian_server.services.PatientLogService;
import com.example.health_guardian_server.services.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patient-log")
@RequiredArgsConstructor
public class PatientLogController {
  PatientLogService patientLogService;

  // Define methods
  @GetMapping("/all")
  public ResponseEntity<Page<PatientLogResponse>> getAllPatientLogs(@ModelAttribute ListPatientLogRequest request) {
    Page<PatientLogResponse> patientLogs = patientLogService.getAllPatientLogs(request);

    return new ResponseEntity<>(patientLogs, HttpStatus.OK);
  }

  @GetMapping("/id/{id}")
  public ResponseEntity<PatientLogResponse> getPatientLogById(String id) {
    PatientLogResponse patientLog = patientLogService.getPatientLogById(id);
    return new ResponseEntity<>(patientLog, HttpStatus.OK);
  }

  @PostMapping("/create")
  public ResponseEntity<PatientLogResponse> createPatientLog(@RequestBody PatientLog patientLog) {
    PatientLogResponse createdPatientLog = patientLogService.createPatientLog(patientLog);
    return new ResponseEntity<>(createdPatientLog, HttpStatus.CREATED);
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<PatientLogResponse> updatePatientLog(String id, PatientLog patientLog) {
    PatientLogResponse updatedPatientLog = patientLogService.updatePatientLog(id, patientLog);
    return new ResponseEntity<>(updatedPatientLog, HttpStatus.OK);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> deletePatientLog(String id) {
    patientLogService.deletePatientLog(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }


}
