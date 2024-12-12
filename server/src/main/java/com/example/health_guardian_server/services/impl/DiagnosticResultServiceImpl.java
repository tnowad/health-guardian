package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.diagnostic.CreateDiagnosticResultRequest;
import com.example.health_guardian_server.dtos.requests.diagnostic.ListDiagnosticResultRequest;
import com.example.health_guardian_server.dtos.responses.diagnostic.DiagnosticResultResponse;
import com.example.health_guardian_server.mappers.DiagnosticResultMapper;
import com.example.health_guardian_server.repositories.DiagnosticResultRepository;
import com.example.health_guardian_server.services.DiagnosticResultService;
import com.example.health_guardian_server.services.MinioClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DiagnosticResultServiceImpl implements DiagnosticResultService {
  private final DiagnosticResultRepository diagnosticResultRepository;
  private final DiagnosticResultMapper diagnosticResultMapper;
  private final MinioClientService minioClientService;

  @Override
  public Page<DiagnosticResultResponse> getAllDiagnosticResultsListDiagnosticResultsRequest(
      ListDiagnosticResultRequest request) {
    log.debug("Fetching all diagnostic results with request: {}", request);
    var diagnosticResults = diagnosticResultRepository
        .findAll(request.toSpecification(), request.toPageable())
        .map(diagnosticResultMapper::toResponse);
    diagnosticResults.forEach(
        diagnosticResult -> {
          String defaultFileUrl = "https://default-avatar-url.com";
          try {
            String fileUrl = minioClientService.getObjectUrl(diagnosticResult.getFile(), "files");
            log.debug(
                "File URL retrieved for diagnosticResult {}: {}",
                diagnosticResult.getId(),
                diagnosticResult.getFile());
            diagnosticResult.setFile(fileUrl);
          } catch (Exception e) {
            log.error(
                "Error when retrieving avatar URL for household: {}", diagnosticResult.getId(), e);
            diagnosticResult.setFile(defaultFileUrl); // Set URL mặc định nếu có lỗi
          }
        });

    log.info("Fetched {} diagnostic results", diagnosticResults.getTotalElements());
    return diagnosticResults;
  }

  @Override
  public DiagnosticResultResponse getDiagnosticResultById(String id) {
    log.debug("Fetching diagnostic result with id: {}", id);
    return diagnosticResultRepository
        .findById(id)
        .map(diagnosticResultMapper::toResponse)
        .orElseThrow(
            () -> {
              log.error("Diagnostic result not found with id: {}", id);
              return new ResourceNotFoundException("Diagnostic result not found with id: " + id);
            });
  }

  @Override
  public DiagnosticResultResponse createDiagnosticResult(CreateDiagnosticResultRequest request) {
    log.debug("Creating diagnostic result with request: {}", request);
    var diagnosticResult = diagnosticResultMapper.toDiagnosticResult(request);
    var savedDiagnosticResult = diagnosticResultRepository.save(diagnosticResult);
    log.info("Created diagnostic result with id: {}", savedDiagnosticResult.getId());
    return diagnosticResultMapper.toResponse(savedDiagnosticResult);
  }

  @Override
  public DiagnosticResultResponse updateDiagnosticResult(
      String id, CreateDiagnosticResultRequest request) {
    log.debug("Updating diagnostic result with id: {} and request: {}", id, request);
    var diagnosticResult = diagnosticResultRepository
        .findById(id)
        .orElseThrow(
            () -> {
              log.error("Diagnostic result not found with id: {}", id);
              return new ResourceNotFoundException(
                  "Diagnostic result not found with id: " + id);
            });
    var updatedDiagnosticResult = diagnosticResultRepository.save(diagnosticResult);
    log.info("Updated diagnostic result with id: {}", updatedDiagnosticResult.getId());
    return diagnosticResultMapper.toResponse(updatedDiagnosticResult);
  }

  @Override
  public void deleteDiagnosticResult(String id) {
    log.debug("Deleting diagnostic result with id: {}", id);
    diagnosticResultRepository.deleteById(id);
    log.info("Deleted diagnostic result with id: {}", id);
  }
}
