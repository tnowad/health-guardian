package com.example.health_guardian_server.mappers;

import com.example.health_guardian_server.dtos.responses.ReportedSideEffectResponse;
import com.example.health_guardian_server.entities.ReportedSideEffect;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReportedSideEffectMapper {

  ReportedSideEffectResponse toReportedSideEffectResponse(ReportedSideEffect reportedSideEffect);

  ReportedSideEffect toReportedSideEffect(ReportedSideEffectResponse reportedSideEffectResponse);
}
