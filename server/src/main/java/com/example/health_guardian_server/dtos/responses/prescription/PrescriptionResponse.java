package com.example.health_guardian_server.dtos.responses.prescription;

import com.example.health_guardian_server.entities.enums.PrescriptionStatus;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionResponse {
  private String id;
  private String userId;
  private String issuedBy;
  private Date issuedDate;
  private Date validUntil;
  private PrescriptionStatus status;
  private Date createdAt;
  private Date updatedAt;
}
