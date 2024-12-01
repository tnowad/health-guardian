package com.example.health_guardian_server.mappers;

import com.example.health_guardian_server.dtos.responses.HouseholdResponse;
import com.example.health_guardian_server.entities.Household;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HouseholdMapper {

  HouseholdResponse toHouseholdResponse(Household household);

  Household toHousehold(HouseholdResponse householdResponse);
}
