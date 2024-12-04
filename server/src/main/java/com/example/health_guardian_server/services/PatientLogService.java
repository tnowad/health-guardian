package com.example.health_guardian_server.services;

import com.example.health_guardian_server.dtos.requests.ListPatientLogRequest;
import com.example.health_guardian_server.dtos.requests.UpdatePatientLogRequest;
import com.example.health_guardian_server.dtos.responses.PatientLogResponse;
import com.example.health_guardian_server.entities.PatientLog;
import org.springframework.data.domain.Page;

public interface PatientLogService {
  // Define methods

  Page<PatientLogResponse> getAllPatientLogs(ListPatientLogRequest request);

  PatientLogResponse getPatientLogById(String id);

  PatientLogResponse createPatientLog(PatientLog patientLog);

  PatientLogResponse updatePatientLog(String id, UpdatePatientLogRequest patientLog);

  void deletePatientLog(String id);
}
