package com.example.health_guardian_server.services;

import com.example.health_guardian_server.dtos.requests.CreateGuardianRequest;
import com.example.health_guardian_server.dtos.requests.ListGuardiansRequest;
import com.example.health_guardian_server.dtos.requests.UpdateGuardianRequest;
import com.example.health_guardian_server.dtos.responses.GuardianResponse;
import org.springframework.data.domain.Page;

public interface GuardianService {

  Page<GuardianResponse> listGuardians(ListGuardiansRequest request);

  GuardianResponse createGuardian(CreateGuardianRequest request);

  GuardianResponse getGuardian(String guardianId);

  void deleteGuardian(String guardianId);

  GuardianResponse updateGuardian(String guardianId, UpdateGuardianRequest request);
}
