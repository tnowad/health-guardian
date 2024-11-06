package com.example.health_guardian_server.dtos.responses;

import lombok.Builder;

@Builder
public class TokensResponse {
  String accessToken;
  String refreshToken;
}
