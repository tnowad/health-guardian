package com.example.health_guardian_server.dtos.requests;

import com.example.health_guardian_server.entities.User;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateUserStaffRequest {
  @NotNull(message = "User ID is required")
  private String userId;

  @NotNull(message = "Role is required")
  private String role;

  @NotNull(message = "Role Type is required")
  private String roleType;


}
