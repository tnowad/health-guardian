package com.example.health_guardian_server.services;

import com.example.health_guardian_server.dtos.requests.household.CreateHouseholdMemberRequest;
import com.example.health_guardian_server.dtos.requests.household.ListHouseholdMembersRequest;
import com.example.health_guardian_server.dtos.responses.household.HouseholdMemberResponse;
import org.springframework.data.domain.Page;

public interface HouseholdMemberService {

  Page<HouseholdMemberResponse> listHouseholdMembers(ListHouseholdMembersRequest request);

  HouseholdMemberResponse createHouseholdMember(CreateHouseholdMemberRequest request);

  HouseholdMemberResponse getHouseholdMember(String householdMemberId);

  HouseholdMemberResponse updateHouseholdMember(
      String householdMemberId, CreateHouseholdMemberRequest request);

  void deleteHouseholdMember(String householdMemberId);
}
