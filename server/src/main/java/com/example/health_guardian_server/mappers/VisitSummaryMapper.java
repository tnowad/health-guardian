package com.example.health_guardian_server.mappers;

import com.example.health_guardian_server.dtos.requests.visit_summary.CreateVisitSummaryRequest;
import com.example.health_guardian_server.dtos.requests.visit_summary.UpdateVisitSummaryRequest;
import com.example.health_guardian_server.dtos.responses.SimpleResponse;
import com.example.health_guardian_server.dtos.responses.visit_summary.VisitSummaryResponse;
import com.example.health_guardian_server.entities.Appointment;
import com.example.health_guardian_server.entities.VisitSummary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = "spring")
public interface VisitSummaryMapper {

  @Mapping(source = "user.id", target = "userId")
  VisitSummaryResponse toVisitSummaryResponse(VisitSummary visitSummary);

  VisitSummary toVisitSummary(CreateVisitSummaryRequest createVisitSummaryRequest);

  VisitSummary toVisitSummary(UpdateVisitSummaryRequest updateVisitSummaryRequest);

  static SimpleResponse toVisitSummarySimpleResponse(VisitSummary visitSummary) {
    return SimpleResponse.builder()
      .id(visitSummary.getId())
      .message("VisitSummaryId: " + visitSummary.getId())
      .build();
  }
}
