package com.example.health_guardian_server.services;

import com.example.health_guardian_server.dtos.requests.CreatePastConditionRequest;
import com.example.health_guardian_server.dtos.requests.ListPastConditionsRequest;
import com.example.health_guardian_server.dtos.responses.PastConditionResponse;
import org.springframework.data.domain.Page;

public interface PastConditionService {

  Page<PastConditionResponse> getAllPastConditions(ListPastConditionsRequest request);

  PastConditionResponse getPastConditionById(String id);

  PastConditionResponse createPastCondition(CreatePastConditionRequest createPastCondition);

  PastConditionResponse updatePastCondition(String id, CreatePastConditionRequest request);

  void deletePastCondition(String id);
}
