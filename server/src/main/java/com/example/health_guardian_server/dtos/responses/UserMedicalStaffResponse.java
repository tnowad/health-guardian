package com.example.health_guardian_server.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMedicalStaffResponse {
  private String id;
  private String userId;
  private String hospitalId;
  private String staffType;
  private String specialization;
  private String role;
  private boolean active;
  private Date endDate;
}
