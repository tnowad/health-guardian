package com.example.health_guardian_server.dtos.responses;

import lombok.Builder;

@Builder
public class SignInResponse {
  String message;
  TokensResponse tokens;
}
