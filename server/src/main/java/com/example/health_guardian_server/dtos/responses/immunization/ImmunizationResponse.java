package com.example.health_guardian_server.dtos.responses.immunization;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImmunizationResponse {
  private String id;
  private String userId;
  private Date vaccinationDate;
  private String vaccineName;
  private String batchNumber;
  private String notes;
}
