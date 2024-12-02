package com.example.health_guardian_server.services;

import com.example.health_guardian_server.dtos.requests.CreateReportedSideEffectRequest;
import com.example.health_guardian_server.dtos.requests.ListReportedSideEffectsRequest;
import com.example.health_guardian_server.dtos.requests.UpdateReportedSideEffectsRequest;
import com.example.health_guardian_server.dtos.responses.ReportedSideEffectResponse;
import org.springframework.data.domain.Page;

public interface ReportedSideEffectService {

  Page<ReportedSideEffectResponse> listReportedSideEffects(ListReportedSideEffectsRequest request);

  ReportedSideEffectResponse getReportedSideEffect(String reportSideEffectId);

  ReportedSideEffectResponse updateReportedSideEffect(
      String reportedSideEffectId, UpdateReportedSideEffectsRequest request);

  void deleteReportedSideEffect(String reportedSideEffectId);

  ReportedSideEffectResponse createReportedSideEffect(CreateReportedSideEffectRequest request);
}
