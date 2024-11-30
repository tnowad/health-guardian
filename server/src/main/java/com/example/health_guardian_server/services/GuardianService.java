package com.example.health_guardian_server.services;

import com.example.health_guardian_server.dtos.requests.CreateGuardianRequest;
import com.example.health_guardian_server.dtos.responses.GetListCommonResponse;
import com.example.health_guardian_server.dtos.responses.GuardianResponse;

public interface GuardianService {
  GuardianResponse createGuardian(CreateGuardianRequest guardianRequest);

  GuardianResponse updateGuardian(CreateGuardianRequest guardianRequest);

  void deleteGuardian(Long id);

  GetListCommonResponse<GuardianResponse> getListGuardian(int page, int pageSize);

  GetListCommonResponse<GuardianResponse> getListGuardianByPatientId(
      Long patientId, int page, int pageSize);
}
