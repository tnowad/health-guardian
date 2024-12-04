package com.example.health_guardian_server.dtos.requests;

import lombok.Data;

@Data
public class CreateAggregatedSideEffectRequest {
  private String sideEffectId;
  private String medicationId;
  private int totalReports;
  private String periodStart;
  private String periodEnd;

}
