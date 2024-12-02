package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.CreateReportedSideEffectRequest;
import com.example.health_guardian_server.dtos.requests.ListReportedSideEffectsRequest;
import com.example.health_guardian_server.dtos.requests.UpdateReportedSideEffectsRequest;
import com.example.health_guardian_server.dtos.responses.ReportedSideEffectResponse;
import com.example.health_guardian_server.services.ReportedSideEffectService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ReportedSideEffectServiceImpl implements ReportedSideEffectService {

  @Override
  public Page<ReportedSideEffectResponse> listReportedSideEffects(
      ListReportedSideEffectsRequest request) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'listReportedSideEffects'");
  }

  @Override
  public ReportedSideEffectResponse createReportedSideEffect(
      CreateReportedSideEffectRequest request) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'createReportedSideEffect'");
  }

  @Override
  public ReportedSideEffectResponse getReportedSideEffect(String reportSideEffectId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getReportedSideEffect'");
  }

  @Override
  public ReportedSideEffectResponse updateReportedSideEffect(
      String reportedSideEffectId, UpdateReportedSideEffectsRequest request) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateReportedSideEffect'");
  }

  @Override
  public void deleteReportedSideEffect(String reportedSideEffectId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteReportedSideEffect'");
  }
}
