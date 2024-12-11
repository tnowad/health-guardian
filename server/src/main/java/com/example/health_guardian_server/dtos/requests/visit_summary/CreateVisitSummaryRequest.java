package com.example.health_guardian_server.dtos.requests.visit_summary;

import com.example.health_guardian_server.entities.enums.VisitSummaryType;
import lombok.Data;

import java.util.Date;

@Data
public class CreateVisitSummaryRequest {
  public String userId;
  public Date visitDate;
  private VisitSummaryType visitType;
  private String summary;
  private String notes;
}
