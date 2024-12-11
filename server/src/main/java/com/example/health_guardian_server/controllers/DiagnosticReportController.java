package com.example.health_guardian_server.controllers;

import com.example.health_guardian_server.dtos.requests.diagnostic.CreateDiagnosticReportRequest;
import com.example.health_guardian_server.dtos.requests.diagnostic.ListDiagnosticReportRequest;
import com.example.health_guardian_server.dtos.responses.diagnostic.DiagnosticReportResponse;
import com.example.health_guardian_server.services.DiagnosticReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diagnostic-responses")
public class DiagnosticReportController {
  private final DiagnosticReportService diagnosticReportService;

  @GetMapping
  public ResponseEntity<Page<DiagnosticReportResponse>> listDiagnosticReports(ListDiagnosticReportRequest request) {
    Page<DiagnosticReportResponse> diagnosticReports = diagnosticReportService.getAllDiagnosticReports(request);
    return ResponseEntity.ok(diagnosticReports);
  }

  @GetMapping("/{id}")
  public ResponseEntity<DiagnosticReportResponse> getDiagnosticReport(String id) {
    DiagnosticReportResponse diagnosticReport = diagnosticReportService.getDiagnosticReportById(id);
    return ResponseEntity.ok(diagnosticReport);
  }

  @PostMapping
  public ResponseEntity<DiagnosticReportResponse> createDiagnosticReport(CreateDiagnosticReportRequest request) {
    DiagnosticReportResponse diagnosticReport = diagnosticReportService.createDiagnosticReport(request);
    return ResponseEntity.ok(diagnosticReport);
  }

  @PutMapping("/{id}")
  public ResponseEntity<DiagnosticReportResponse> updateDiagnosticReport(String id, CreateDiagnosticReportRequest request) {
    DiagnosticReportResponse diagnosticReport = diagnosticReportService.updateDiagnosticReport(id, request);
    return ResponseEntity.ok(diagnosticReport);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteDiagnosticReport(String id) {
    diagnosticReportService.deleteDiagnosticReport(id);
    return ResponseEntity.noContent().build();
  }
}
