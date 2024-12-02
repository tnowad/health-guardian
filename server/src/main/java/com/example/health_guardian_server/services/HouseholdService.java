package com.example.health_guardian_server.services;

import com.example.health_guardian_server.dtos.requests.CreateHouseholdRequest;
import com.example.health_guardian_server.dtos.requests.ListHouseholdsRequest;
import com.example.health_guardian_server.dtos.requests.UpdateHouseholdRequest;
import com.example.health_guardian_server.dtos.responses.HouseholdResponse;
import org.springframework.data.domain.Page;

public interface HouseholdService {

  Page<HouseholdResponse> listHouseholds(ListHouseholdsRequest request);

  HouseholdResponse createHousehold(CreateHouseholdRequest request);

  HouseholdResponse getHousehold(String householdId);

  HouseholdResponse updateHousehold(String householdId, UpdateHouseholdRequest request);

  void deleteHousehold(String householdId);
}
