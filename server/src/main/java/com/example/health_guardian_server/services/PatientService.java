package com.example.health_guardian_server.services;

import com.example.health_guardian_server.dtos.requests.ListPatientRequest;
import com.example.health_guardian_server.dtos.responses.PatientResponse;
import com.example.health_guardian_server.entities.Patient;
import org.springframework.data.domain.Page;

public interface PatientService {

  Patient createPatient(String userId, String firstName, String lastName);

  Page<PatientResponse> getAllPatients(ListPatientRequest request);

  PatientResponse getPatientById(String id);

  PatientResponse updatePatient(String id, Patient patient);

  void deletePatient(String id);

}

