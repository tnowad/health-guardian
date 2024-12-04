package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.CreateReportedSideEffectRequest;
import com.example.health_guardian_server.dtos.requests.ListReportedSideEffectsRequest;
import com.example.health_guardian_server.dtos.requests.UpdateReportedSideEffectsRequest;
import com.example.health_guardian_server.dtos.responses.ReportedSideEffectResponse;
import com.example.health_guardian_server.services.ReportedSideEffectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@Slf4j // Enable logging for the class
public class ReportedSideEffectServiceImpl implements ReportedSideEffectService {

  @Override
  public Page<ReportedSideEffectResponse> listReportedSideEffects(
    ListReportedSideEffectsRequest request) {
    log.debug("Attempting to list reported side effects with request: {}", request);
    // Unimplemented method, throwing exception
    log.warn("Method 'listReportedSideEffects' is unimplemented.");
    throw new UnsupportedOperationException("Unimplemented method 'listReportedSideEffects'");
  }

  @Override
  public ReportedSideEffectResponse createReportedSideEffect(
    CreateReportedSideEffectRequest request) {
    log.debug("Attempting to create reported side effect with request: {}", request);
    // Unimplemented method, throwing exception
    log.warn("Method 'createReportedSideEffect' is unimplemented.");
    throw new UnsupportedOperationException("Unimplemented method 'createReportedSideEffect'");
  }

  @Override
  public ReportedSideEffectResponse getReportedSideEffect(String reportSideEffectId) {
    log.debug("Attempting to get reported side effect with id: {}", reportSideEffectId);
    // Unimplemented method, throwing exception
    log.warn("Method 'getReportedSideEffect' is unimplemented.");
    throw new UnsupportedOperationException("Unimplemented method 'getReportedSideEffect'");
  }

  @Override
  public ReportedSideEffectResponse updateReportedSideEffect(
    String reportedSideEffectId, UpdateReportedSideEffectsRequest request) {
    log.debug("Attempting to update reported side effect with id: {} and request: {}", reportedSideEffectId, request);
    // Unimplemented method, throwing exception
    log.warn("Method 'updateReportedSideEffect' is unimplemented.");
    throw new UnsupportedOperationException("Unimplemented method 'updateReportedSideEffect'");
  }

  @Override
  public void deleteReportedSideEffect(String reportedSideEffectId) {
    log.debug("Attempting to delete reported side effect with id: {}", reportedSideEffectId);
    // Unimplemented method, throwing exception
    log.warn("Method 'deleteReportedSideEffect' is unimplemented.");
    throw new UnsupportedOperationException("Unimplemented method 'deleteReportedSideEffect'");
  }
}
