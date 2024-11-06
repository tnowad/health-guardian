package com.example.health_guardian_server.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignInRequest {
  @Email @NotBlank private String email;

  @Size(min = 8)
  @NotBlank
  private String password;
}
