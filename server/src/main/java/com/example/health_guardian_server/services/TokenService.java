package com.example.health_guardian_server.services;

import com.example.health_guardian_server.dtos.responses.TokensResponse;
import java.util.Set;

public interface TokenService {

  String generateRefreshToken(String userId);

  String generateAccessToken(String userId, Set<String> permissionNames);

  TokensResponse generateTokens(String userId, Set<String> permissionNames);

  TokensResponse refreshTokens(String refreshToken);

  Set<String> extractPermissionNames(String accessToken);
}
