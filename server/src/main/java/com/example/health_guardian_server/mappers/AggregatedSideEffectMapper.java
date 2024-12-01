package com.example.health_guardian_server.mappers;

import com.example.health_guardian_server.dtos.responses.AggregatedSideEffectResponse;
import com.example.health_guardian_server.entities.AggregatedSideEffect;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AggregatedSideEffectMapper {

  AggregatedSideEffectResponse toAggregatedSideEffectResponse(
      AggregatedSideEffect aggregatedSideEffect);

  AggregatedSideEffect toAggregatedSideEffect(
      AggregatedSideEffectResponse aggregatedSideEffectResponse);
}
