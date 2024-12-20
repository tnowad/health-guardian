package com.example.health_guardian_server.mappers;

import com.example.health_guardian_server.dtos.requests.past_condition.CreatePastConditionRequest;
import com.example.health_guardian_server.dtos.responses.past_condition.PastConditionResponse;
import com.example.health_guardian_server.entities.PastCondition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PastConditionMapper {
  @Mapping(source = "user.id", target = "userId")
  PastConditionResponse toResponse(PastCondition pastCondition);

  PastCondition toPastCondition(CreatePastConditionRequest pastCondition);
}
