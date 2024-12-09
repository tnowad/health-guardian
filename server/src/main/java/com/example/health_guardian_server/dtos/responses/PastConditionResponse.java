package com.example.health_guardian_server.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PastConditionResponse {
  private String id;
  private String userId;
  private String condition;
  private String description;
  private Date dateDiagnosed;
}
