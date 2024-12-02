package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.CreateHouseholdMemberRequest;
import com.example.health_guardian_server.dtos.requests.ListHouseholdMembersRequest;
import com.example.health_guardian_server.dtos.requests.UpdateHouseholdMemberRequest;
import com.example.health_guardian_server.dtos.responses.HouseholdMemberResponse;
import com.example.health_guardian_server.services.HouseholdMemberService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class HouseholdMemberServiceImpl implements HouseholdMemberService {

  @Override
  public Page<HouseholdMemberResponse> listHouseholdMembers(ListHouseholdMembersRequest request) {
    throw new UnsupportedOperationException("Unimplemented method 'listHouseholdMembers'");
  }

  @Override
  public HouseholdMemberResponse createHouseholdMember(CreateHouseholdMemberRequest request) {
    throw new UnsupportedOperationException("Unimplemented method 'createHouseholdMember'");
  }

  @Override
  public HouseholdMemberResponse getHouseholdMember(String householdMemberId) {
    throw new UnsupportedOperationException("Unimplemented method 'getHouseholdMember'");
  }

  @Override
  public HouseholdMemberResponse updateHouseholdMember(
      String householdMemberId, UpdateHouseholdMemberRequest request) {
    throw new UnsupportedOperationException("Unimplemented method 'updateHouseholdMember'");
  }

  @Override
  public void deleteHouseholdMember(String householdMemberId) {
    throw new UnsupportedOperationException("Unimplemented method 'deleteHouseholdMember'");
  }
}
