package com.example.health_guardian_server.services;

import com.example.health_guardian_server.dtos.requests.diagnostic.CreateDiagnosticResultRequest;
import com.example.health_guardian_server.dtos.requests.diagnostic.ListDiagnosticResultRequest;
import com.example.health_guardian_server.dtos.responses.diagnostic.DiagnosticResultResponse;
import org.springframework.data.domain.Page;

public interface DiagnosticResultService {

  Page<DiagnosticResultResponse> getAllDiagnosticResultsListDiagnosticResultsRequest(ListDiagnosticResultRequest request);

  DiagnosticResultResponse getDiagnosticResultById(String id);

  DiagnosticResultResponse createDiagnosticResult(CreateDiagnosticResultRequest request);

  DiagnosticResultResponse updateDiagnosticResult(String id, CreateDiagnosticResultRequest request);

  void deleteDiagnosticResult(String id);
}
