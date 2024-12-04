package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.ListPatientLogRequest;
import com.example.health_guardian_server.dtos.requests.UpdatePatientLogRequest;
import com.example.health_guardian_server.dtos.responses.PatientLogResponse;
import com.example.health_guardian_server.entities.PatientLog;
import com.example.health_guardian_server.exceptions.file.FileException;
import com.example.health_guardian_server.mappers.PatientLogMapper;
import com.example.health_guardian_server.repositories.PatientLogRepository;
import com.example.health_guardian_server.services.MinioClientService;
import com.example.health_guardian_server.services.PatientLogService;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PatientLogServiceImpl implements PatientLogService {
  PatientLogRepository patientLogRepository;
  PatientLogMapper patientLogMapper;
  MinioClientService minioClientService;

  @Override
  public Page<PatientLogResponse> getAllPatientLogs(ListPatientLogRequest request) {

    PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
    var patientLogs = patientLogRepository.findAll(pageRequest).map(patientLogMapper::toPatientLogResponse);

    return patientLogs;
  }

  @Override
  public PatientLogResponse getPatientLogById(String id) {
    var patientLog = patientLogRepository.findById(id).get();

    var patientLogResponse = patientLogMapper.toPatientLogResponse(patientLog);
    var urls = new ArrayList<String>();
    for (String p : patientLog.getFileNames()) {
      var url = "data:image/jpeg;base64,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";
      try {
        url = minioClientService.getObjectUrl(p, "patient-log-files");
      } catch (FileException e) {
        log.error("Error while getting file url", e);
      }
      urls.add(url);
    }
    patientLogResponse.setFileUrls(urls);

    return patientLogResponse;
  }

  @Override
  public PatientLogResponse createPatientLog(PatientLog patientLog) {
    PatientLog createdPatientLog = patientLogRepository.save(patientLog);
    return patientLogMapper.toPatientLogResponse(createdPatientLog);
  }

  @Override
  public PatientLogResponse updatePatientLog(String id, UpdatePatientLogRequest patientLog) {
    PatientLog existingPatientLog = patientLogRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("PatientLog not found with id " + id));

    if (existingPatientLog.getFileNames() != null) {
      existingPatientLog.setFileNames(
          Stream.concat(
              existingPatientLog.getFileNames().stream(), patientLog.getFileNames().stream())
              .distinct()
              .collect(Collectors.toList()));
    } else {
      existingPatientLog.setFileNames(patientLog.getFileNames());
    }
    existingPatientLog.setLogType(patientLog.getLogType());
    existingPatientLog.setMessage(patientLog.getMessage());

    PatientLog updatedPatientLog = patientLogRepository.save(existingPatientLog);
    return patientLogMapper.toPatientLogResponse(updatedPatientLog);
  }

  @Override
  public void deletePatientLog(String id) {
    patientLogRepository.deleteById(id);
  }
}
