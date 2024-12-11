package com.example.health_guardian_server.services;

import com.example.health_guardian_server.dtos.requests.family_history.ListFamilyHistoryRequest;
import com.example.health_guardian_server.dtos.responses.family_history.FamilyHistoryResponse;
import org.springframework.data.domain.Page;

public interface FamilyHistoryService {
  Page<FamilyHistoryResponse> getAllFamilyHistories(ListFamilyHistoryRequest request);
}
