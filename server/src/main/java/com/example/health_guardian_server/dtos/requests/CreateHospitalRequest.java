package com.example.health_guardian_server.dtos.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateHospitalRequest {

  @NotNull(message = "Name is required")
  private String name;

  @NotNull(message = "Location is required")
  private String location;

  @NotNull(message = "Phone is required")
  private String phone;

  @NotNull(message = "Email is required")
  private String email;
}
