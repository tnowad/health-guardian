package com.example.health_guardian_server.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CreateGuardianRequest {
  @NotBlank
  private String name;

  @NotBlank
  private String relationshipToPatient;

  @NotBlank
  @Pattern(regexp = "^\\+?[0-9]*$")
  private String phone;

  @Email
  private String email;
}
