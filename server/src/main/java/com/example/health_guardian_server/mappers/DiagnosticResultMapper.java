package com.example.health_guardian_server.mappers;

import com.example.health_guardian_server.dtos.requests.diagnostic.CreateDiagnosticResultRequest;
import com.example.health_guardian_server.dtos.responses.diagnostic.DiagnosticResultResponse;
import com.example.health_guardian_server.entities.DiagnosticResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DiagnosticResultMapper {
    // Define methods
  @Mapping(source = "user.id", target = "userId")
  DiagnosticResultResponse toResponse(DiagnosticResult diagnosticResult);

  DiagnosticResult toDiagnosticResult(CreateDiagnosticResultRequest diagnosticResult);
}
