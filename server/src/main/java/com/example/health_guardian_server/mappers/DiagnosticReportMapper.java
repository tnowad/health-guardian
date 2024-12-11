package com.example.health_guardian_server.mappers;

import com.example.health_guardian_server.dtos.requests.diagnostic.CreateDiagnosticReportRequest;
import com.example.health_guardian_server.dtos.responses.diagnostic.DiagnosticReportResponse;
import com.example.health_guardian_server.entities.DiagnosticReport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DiagnosticReportMapper {
  @Mapping(source = "user.id", target = "userId")
  DiagnosticReportResponse toResponse(DiagnosticReport diagnosticReport);

  DiagnosticReport toDiagnosticReport(CreateDiagnosticReportRequest diagnosticReport);
}
