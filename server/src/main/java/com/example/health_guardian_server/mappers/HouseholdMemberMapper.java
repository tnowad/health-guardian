package com.example.health_guardian_server.mappers;

import com.example.health_guardian_server.dtos.responses.HouseholdMemberResponse;
import com.example.health_guardian_server.entities.HouseholdMember;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HouseholdMemberMapper {

  HouseholdMemberResponse toHouseholdMemberResponse(HouseholdMember householdMember);

  HouseholdMember toHouseholdMember(HouseholdMemberResponse householdMemberResponse);
}
