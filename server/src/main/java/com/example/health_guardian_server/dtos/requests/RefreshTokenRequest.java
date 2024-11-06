package com.example.health_guardian_server.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RefreshTokenRequest {

  @Size(min = 8)
  @NotBlank
  private String refreshToken;
}
