package com.example.health_guardian_server.services;

import com.example.health_guardian_server.dtos.requests.CreateHouseholdMemberRequest;
import com.example.health_guardian_server.dtos.requests.ListHouseholdMembersRequest;
import com.example.health_guardian_server.dtos.requests.UpdateHouseholdMemberRequest;
import com.example.health_guardian_server.dtos.responses.HouseholdMemberResponse;
import org.springframework.data.domain.Page;

public interface HouseholdMemberService {

  Page<HouseholdMemberResponse> listHouseholdMembers(ListHouseholdMembersRequest request);

  HouseholdMemberResponse createHouseholdMember(CreateHouseholdMemberRequest request);

  HouseholdMemberResponse getHouseholdMember(String householdMemberId);

  HouseholdMemberResponse updateHouseholdMember(
      String householdMemberId, UpdateHouseholdMemberRequest request);

  void deleteHouseholdMember(String householdMemberId);
}
