package com.example.health_guardian_server.services;

import com.example.health_guardian_server.dtos.requests.CreateGuardianRequest;
import com.example.health_guardian_server.dtos.requests.ListGuardiansRequest;
import com.example.health_guardian_server.dtos.responses.GuardianResponse;
import org.springframework.data.domain.Page;

public interface GuardianService {

  Page<GuardianResponse> getAllGuardians(ListGuardiansRequest request);

  GuardianResponse getGuardianById(String id);

  GuardianResponse createGuardian(CreateGuardianRequest createGuardian);

  GuardianResponse updateGuardian(String id, CreateGuardianRequest guardianResponse);

  void deleteGuardian(String id);
}
