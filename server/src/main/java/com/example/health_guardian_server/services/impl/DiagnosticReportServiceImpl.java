package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.diagnostic.CreateDiagnosticReportRequest;
import com.example.health_guardian_server.dtos.requests.diagnostic.ListDiagnosticReportRequest;
import com.example.health_guardian_server.dtos.responses.diagnostic.DiagnosticReportResponse;
import com.example.health_guardian_server.entities.DiagnosticReport;
import com.example.health_guardian_server.entities.User;
import com.example.health_guardian_server.mappers.DiagnosticReportMapper;
import com.example.health_guardian_server.repositories.DiagnosticReportRepository;
import com.example.health_guardian_server.repositories.UserRepository;
import com.example.health_guardian_server.services.DiagnosticReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiagnosticReportServiceImpl implements DiagnosticReportService {
  private final DiagnosticReportRepository diagnosticReportRepository;
  private final DiagnosticReportMapper diagnosticReportMapper;
  private final UserRepository userRepository;

  @Override
  public Page<DiagnosticReportResponse> getAllDiagnosticReports(ListDiagnosticReportRequest request) {
    log.debug("Fetching all diagnostic reports with request: {}", request);
    var diagnosticReports = diagnosticReportRepository.findAll(request.toSpecification(), request.toPageable())
      .map(diagnosticReportMapper::toResponse);
    log.info("Fetched {} diagnostic reports", diagnosticReports.getTotalElements());
    return diagnosticReports;
  }

  @Override
  public DiagnosticReportResponse getDiagnosticReportById(String id) {
    log.debug("Fetching diagnostic report with id: {}", id);
    return diagnosticReportRepository.findById(id)
      .map(diagnosticReportMapper::toResponse)
      .orElseThrow(() -> {
        log.error("Diagnostic report not found with id: {}", id);
        return new ResourceNotFoundException("Diagnostic report not found with id: " + id);
      });

  }

  @Override
  public DiagnosticReportResponse createDiagnosticReport(CreateDiagnosticReportRequest request) {
    log.debug("Creating diagnostic report with request: {}", request);
    var diagnosticReport = diagnosticReportMapper.toDiagnosticReport(request);
    User user = userRepository.findById(request.getUserId())
      .orElseThrow(() -> {
      log.error("User not found with id: {}", request.getUserId());
      return new ResourceNotFoundException("User not found with id: " + request.getUserId());
    });
    diagnosticReport.setUser(user);
    var savedDiagnosticReport = diagnosticReportRepository.save(diagnosticReport);
    log.info("Created diagnostic report with id: {}", savedDiagnosticReport.getId());
    return diagnosticReportMapper.toResponse(savedDiagnosticReport);
  }

  @Override
  public DiagnosticReportResponse updateDiagnosticReport(String id, CreateDiagnosticReportRequest request) {
    log.debug("Updating diagnostic report with id: {} and request: {}", id, request);
    var diagnosticReport = diagnosticReportRepository.findById(id)
      .orElseThrow(() -> {
        log.error("Diagnostic report not found with id: {}", id);
        return new ResourceNotFoundException("Diagnostic report not found with id: " + id);
      });
    var updatedDiagnosticReport = diagnosticReportRepository.save(diagnosticReport);
    log.info("Updated diagnostic report with id: {}", updatedDiagnosticReport.getId());
    return diagnosticReportMapper.toResponse(updatedDiagnosticReport);
  }

  @Override
  public void deleteDiagnosticReport(String id) {
    log.debug("Deleting diagnostic report with id: {}", id);
    diagnosticReportRepository.deleteById(id);
    log.info("Deleted diagnostic report with id: {}", id);
  }
}
