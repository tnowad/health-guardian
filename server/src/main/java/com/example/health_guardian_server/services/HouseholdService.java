package com.example.health_guardian_server.services;

import com.example.health_guardian_server.dtos.requests.household.CreateHouseholdRequest;
import com.example.health_guardian_server.dtos.requests.household.ListHouseholdsRequest;
import com.example.health_guardian_server.dtos.responses.household.HouseholdMemberResponse;
import com.example.health_guardian_server.dtos.responses.household.HouseholdResponse;
import com.example.health_guardian_server.entities.Household;
import com.example.health_guardian_server.entities.HouseholdMember;
import org.springframework.data.domain.Page;

public interface HouseholdService {

  Page<HouseholdResponse> listHouseholds(ListHouseholdsRequest request);

  HouseholdResponse createHousehold(CreateHouseholdRequest request);

  HouseholdResponse getHousehold(String householdId);

  Household getHouseholdEntity(String householdId);

  HouseholdResponse updateHousehold(String householdId, CreateHouseholdRequest request);

  void deleteHousehold(String householdId);

  HouseholdMemberResponse joinHousehold(String householdId, String userId);
}
