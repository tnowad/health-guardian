package com.example.health_guardian_server.mappers;

import com.example.health_guardian_server.dtos.requests.household.CreateHouseholdRequest;
import com.example.health_guardian_server.dtos.responses.household.HouseholdResponse;
import com.example.health_guardian_server.entities.Household;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HouseholdMapper {
  @Mapping(source = "head.id", target = "headId")
  HouseholdResponse toHouseholdResponse(Household household);

  @Mapping(source = "headId", target = "head.id")
  Household toHousehold(CreateHouseholdRequest createHouseholdRequest);
}
