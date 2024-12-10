package com.example.health_guardian_server.mappers;

import com.example.health_guardian_server.dtos.requests.household.CreateHouseholdMemberRequest;
import com.example.health_guardian_server.dtos.responses.household.HouseholdMemberResponse;
import com.example.health_guardian_server.entities.HouseholdMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HouseholdMemberMapper {

  @Mapping(source = "household.id", target = "householdId")
  @Mapping(source = "user.id", target = "userId")
  HouseholdMemberResponse toHouseholdMemberResponse(HouseholdMember householdMember);

  HouseholdMember toHouseholdMember(CreateHouseholdMemberRequest createHouseholdMemberRequest);
}
