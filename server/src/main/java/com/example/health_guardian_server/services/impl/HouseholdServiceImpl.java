package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.CreateHouseholdRequest;
import com.example.health_guardian_server.dtos.requests.ListHouseholdsRequest;
import com.example.health_guardian_server.dtos.requests.UpdateHouseholdRequest;
import com.example.health_guardian_server.dtos.responses.HouseholdResponse;
import com.example.health_guardian_server.services.HouseholdService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class HouseholdServiceImpl implements HouseholdService {

  @Override
  public Page<HouseholdResponse> listHouseholds(ListHouseholdsRequest request) {

    throw new UnsupportedOperationException("Unimplemented method 'listHouseholds'");
  }

  @Override
  public HouseholdResponse createHousehold(CreateHouseholdRequest request) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'createHousehold'");
  }

  @Override
  public HouseholdResponse getHousehold(String householdId) {
    throw new UnsupportedOperationException("Unimplemented method 'getHousehold'");
  }

  @Override
  public HouseholdResponse updateHousehold(String householdId, UpdateHouseholdRequest request) {
    throw new UnsupportedOperationException("Unimplemented method 'updateHousehold'");
  }

  @Override
  public void deleteHousehold(String householdId) {
    throw new UnsupportedOperationException("Unimplemented method 'deleteHousehold'");
  }
}
