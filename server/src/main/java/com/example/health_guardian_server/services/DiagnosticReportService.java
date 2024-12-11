package com.example.health_guardian_server.services;

import com.example.health_guardian_server.dtos.requests.diagnostic.CreateDiagnosticReportRequest;
import com.example.health_guardian_server.dtos.requests.diagnostic.ListDiagnosticReportRequest;
import com.example.health_guardian_server.dtos.responses.diagnostic.DiagnosticReportResponse;
import org.springframework.data.domain.Page;

public interface DiagnosticReportService {

    Page<DiagnosticReportResponse> getAllDiagnosticReports(ListDiagnosticReportRequest request);

    DiagnosticReportResponse getDiagnosticReportById(String id);

    DiagnosticReportResponse createDiagnosticReport(CreateDiagnosticReportRequest request);

    DiagnosticReportResponse updateDiagnosticReport(String id, CreateDiagnosticReportRequest request);

    void deleteDiagnosticReport(String id);
}
