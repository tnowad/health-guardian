package com.example.health_guardian_server.controllers;

import com.example.health_guardian_server.dtos.requests.diagnostic.CreateDiagnosticResultRequest;
import com.example.health_guardian_server.dtos.requests.diagnostic.ListDiagnosticResultRequest;
import com.example.health_guardian_server.dtos.responses.diagnostic.DiagnosticResultResponse;
import com.example.health_guardian_server.services.DiagnosticResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diagnostic-results")
public class DiagnosticResultController {
  private final DiagnosticResultService diagnosticResultService;

  @GetMapping
  public ResponseEntity<Page<DiagnosticResultResponse>> listDiagnosticResults(ListDiagnosticResultRequest request) {
    Page<DiagnosticResultResponse> diagnosticResults = diagnosticResultService.getAllDiagnosticResultsListDiagnosticResultsRequest(request);
    return ResponseEntity.ok(diagnosticResults);
  }

  @GetMapping("/{id}")
  public ResponseEntity<DiagnosticResultResponse> getDiagnosticResult(String id) {
    DiagnosticResultResponse diagnosticResult = diagnosticResultService.getDiagnosticResultById(id);
    return ResponseEntity.ok(diagnosticResult);
  }

  @PostMapping
  public ResponseEntity<DiagnosticResultResponse> createDiagnosticResult(CreateDiagnosticResultRequest request) {
    DiagnosticResultResponse diagnosticResult = diagnosticResultService.createDiagnosticResult(request);
    return ResponseEntity.ok(diagnosticResult);
  }

  @PutMapping("/{id}")
  public ResponseEntity<DiagnosticResultResponse> updateDiagnosticResult(String id, CreateDiagnosticResultRequest request) {
    DiagnosticResultResponse diagnosticResult = diagnosticResultService.updateDiagnosticResult(id, request);
    return ResponseEntity.ok(diagnosticResult);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteDiagnosticResult(String id) {
    diagnosticResultService.deleteDiagnosticResult(id);
    return ResponseEntity.noContent().build();
  }

}
