package com.example.health_guardian_server.dtos.requests;

import lombok.Data;

import java.util.Date;

@Data
public class CreatePastConditionRequest {
  private String userId;
  private String condition;
  private String description;
  private Date dateDiagnosed;
}
