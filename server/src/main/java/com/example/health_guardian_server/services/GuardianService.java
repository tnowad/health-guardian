package com.example.health_guardian_server.services;


import com.example.health_guardian_server.dtos.requests.ListGuardianRequest;

import com.example.health_guardian_server.dtos.responses.GuardianResponse;
import org.springframework.data.domain.Page;

public interface GuardianService {

  // Define methods

  Page<GuardianResponse> getAllGuardians(ListGuardianRequest request);

  GuardianResponse getGuardianById(String id);

  GuardianResponse createGuardian(GuardianResponse guardianResponse);

  GuardianResponse updateGuardian(String id, GuardianResponse guardianResponse);

  void deleteGuardian(String id);


}
