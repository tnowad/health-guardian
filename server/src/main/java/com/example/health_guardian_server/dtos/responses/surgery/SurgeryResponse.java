package com.example.health_guardian_server.dtos.responses.surgery;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SurgeryResponse {
  private String id;
  private String userId;
  private Date date;
  private String description;
  private String notes;
}
