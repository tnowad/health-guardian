package com.example.health_guardian_server.dtos.requests;

import com.example.health_guardian_server.entities.Hospital;
import com.example.health_guardian_server.entities.User;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class CreateUserMedicalStaffRequest {

  @NotNull( message = "User is required")
  private String userId;

  @NotNull( message = "Hospital is required")
  private Hospital hospital;

  @NotNull( message = "Staff Type is required")
  private String staffType;

  @NotNull( message = "Specialization is required")
  private String specialization;

  @NotNull( message = "Role is required")
  private String role;

  @NotNull( message = "Active is required")
  private boolean active;

  @NotNull( message = "End Date is required")
  private Date endDate;
}
