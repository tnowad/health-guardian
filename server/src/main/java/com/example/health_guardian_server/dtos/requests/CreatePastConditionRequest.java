package com.example.health_guardian_server.dtos.requests;

import java.util.Date;
import lombok.Data;

@Data
public class CreatePastConditionRequest {
  private String userId;
  private String condition;
  private String description;
  private Date dateDiagnosed;
}
