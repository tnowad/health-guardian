package com.example.health_guardian_server.dtos.requests;

import java.util.Date;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateUserMedicalStaffRequest {

  @NotNull(message = "User is required")
  private String userId;

  @NotNull(message = "Hospital is required")
  private String hospitalId;

  @NotNull(message = "Staff Type is required")
  private String staffType;

  @NotNull(message = "Specialization is required")
  private String specialization;

  @NotNull(message = "Role is required")
  private String role;

  @NotNull(message = "Active is required")
  private boolean active;

  @NotNull(message = "End Date is required")
  private Date endDate;
}
