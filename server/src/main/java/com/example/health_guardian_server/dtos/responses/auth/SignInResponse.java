package com.example.health_guardian_server.dtos.responses.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInResponse {
  String message;
  TokenResponse tokens;
}
