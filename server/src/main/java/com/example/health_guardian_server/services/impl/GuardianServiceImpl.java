package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.CreateGuardianRequest;
import com.example.health_guardian_server.dtos.responses.GetListCommonResponse;
import com.example.health_guardian_server.dtos.responses.GuardianResponse;
import com.example.health_guardian_server.services.GuardianService;
import org.springframework.stereotype.Service;

@Service
public class GuardianServiceImpl implements GuardianService {

  @Override
  public GuardianResponse createGuardian(CreateGuardianRequest guardianRequest) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'createGuardian'");
  }

  @Override
  public GuardianResponse updateGuardian(CreateGuardianRequest guardianRequest) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateGuardian'");
  }

  @Override
  public void deleteGuardian(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteGuardian'");
  }

  @Override
  public GetListCommonResponse<GuardianResponse> getListGuardian(int page, int pageSize) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getListGuardian'");
  }

  @Override
  public GetListCommonResponse<GuardianResponse> getListGuardianByPatientId(
      Long patientId, int page, int pageSize) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getListGuardianByPatientId'");
  }
  // Implement methods
}
