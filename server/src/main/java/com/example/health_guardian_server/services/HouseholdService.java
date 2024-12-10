package com.example.health_guardian_server.services;

import com.example.health_guardian_server.dtos.requests.household.CreateHouseholdRequest;
import com.example.health_guardian_server.dtos.requests.household.ListHouseholdsRequest;
import com.example.health_guardian_server.dtos.responses.household.HouseholdResponse;
import org.springframework.data.domain.Page;

public interface HouseholdService {

  Page<HouseholdResponse> listHouseholds(ListHouseholdsRequest request);

  HouseholdResponse createHousehold(CreateHouseholdRequest request);

  HouseholdResponse getHousehold(String householdId);

  HouseholdResponse updateHousehold(String householdId, CreateHouseholdRequest request);

  void deleteHousehold(String householdId);
}
