package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.ListPatientLogRequest;
import com.example.health_guardian_server.dtos.responses.PatientLogResponse;
import com.example.health_guardian_server.entities.PatientLog;
import com.example.health_guardian_server.mappers.PatientLogMapper;
import com.example.health_guardian_server.repositories.PatientLogRepository;
import com.example.health_guardian_server.services.PatientLogService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientLogServiceImpl implements PatientLogService {
  // Implement methods
   PatientLogRepository patientLogRepository;
   PatientLogMapper patientLogMapper;

  @Override
  public Page<PatientLogResponse> getAllPatientLogs(ListPatientLogRequest request) {

    PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
    var patientLogs = patientLogRepository.findAll(pageRequest).map(patientLogMapper::toPatientLogResponse);

    return patientLogs;
  }

  @Override
  public PatientLogResponse getPatientLogById(String id) {
    return patientLogRepository.findById(id)
      .map(patientLogMapper::toPatientLogResponse)
      .orElseThrow(() -> new ResourceNotFoundException("PatientLog not found with id " + id));
  }

  @Override
  public PatientLogResponse createPatientLog(PatientLog patientLog){
    PatientLog createdPatientLog = patientLogRepository.save(patientLog);
    return patientLogMapper.toPatientLogResponse(createdPatientLog);
  }

  @Override
  public PatientLogResponse updatePatientLog(String id, PatientLog patientLog) {
    PatientLog existingPatientLog = patientLogRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("PatientLog not found with id " + id));

    existingPatientLog.setPatient(patientLog.getPatient());
    existingPatientLog.setLogType(patientLog.getLogType());
    existingPatientLog.setMessage(patientLog.getMessage());
    existingPatientLog.setCreatedAt(patientLog.getCreatedAt());

    PatientLog updatedPatientLog = patientLogRepository.save(existingPatientLog);
    return patientLogMapper.toPatientLogResponse(updatedPatientLog);
  }

  @Override
  public void deletePatientLog(String id) {
    patientLogRepository.deleteById(id);
  }
}
