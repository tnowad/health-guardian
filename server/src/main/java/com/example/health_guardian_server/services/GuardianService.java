package com.example.health_guardian_server.services;

import com.example.health_guardian_server.dtos.requests.ListGuardiansRequest;
import com.example.health_guardian_server.dtos.responses.GuardianResponse;
import org.springframework.data.domain.Page;

public interface GuardianService {

  Page<GuardianResponse> getAllGuardians(ListGuardiansRequest request);

  GuardianResponse getGuardianById(String id);

  GuardianResponse createGuardian(GuardianResponse guardianResponse);

  GuardianResponse updateGuardian(String id, GuardianResponse guardianResponse);

  void deleteGuardian(String id);
}
