package com.example.health_guardian_server.mappers;

import com.example.health_guardian_server.dtos.requests.CreateHouseholdMemberRequest;
import com.example.health_guardian_server.dtos.requests.CreateHouseholdRequest;
import com.example.health_guardian_server.dtos.responses.HouseholdMemberResponse;
import com.example.health_guardian_server.entities.HouseholdMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HouseholdMemberMapper {

  @Mapping(source = "household.id", target = "householdId")
  @Mapping(source = "patient.id", target = "patientId")
  HouseholdMemberResponse toHouseholdMemberResponse(HouseholdMember householdMember);

  HouseholdMember toHouseholdMember(CreateHouseholdMemberRequest createHouseholdMemberRequest);
}
