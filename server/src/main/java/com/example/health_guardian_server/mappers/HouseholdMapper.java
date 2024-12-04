package com.example.health_guardian_server.mappers;

import com.example.health_guardian_server.dtos.requests.CreateHouseholdRequest;
import com.example.health_guardian_server.dtos.responses.HouseholdResponse;
import com.example.health_guardian_server.entities.Household;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HouseholdMapper {
  @Mapping(source = "head.id", target = "headId")
  HouseholdResponse toHouseholdResponse(Household household);

  Household toHousehold(CreateHouseholdRequest createHouseholdRequest);
}
