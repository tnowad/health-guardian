package com.example.health_guardian_server.dtos.responses.visit_summary;

import com.example.health_guardian_server.entities.enums.VisitSummaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitSummaryResponse {
  public String id;
  public String userId;
  public Date visitDate;
  private VisitSummaryType visitType;
  private String summary;
  private String notes;
}
