package com.example.health_guardian_server.mappers;

import com.example.health_guardian_server.dtos.requests.CreatePastConditionRequest;
import com.example.health_guardian_server.dtos.responses.PastConditionResponse;
import com.example.health_guardian_server.entities.PastCondition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PastConditionMapper {
  // Define methods
  @Mapping(source = "user.id", target = "userId")
  PastConditionResponse toResponse(PastCondition pastCondition);

  PastCondition toPastCondition(CreatePastConditionRequest pastCondition);
}
