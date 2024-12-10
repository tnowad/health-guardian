package com.example.health_guardian_server.services;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.health_guardian_server.dtos.responses.auth.TokenResponse;
import org.springframework.stereotype.Service;

import java.util.Set;
@Service
public interface TokenService {

  String generateRefreshToken(String userId);

  String generateAccessToken(String userId);

  TokenResponse generateTokens(String userId);

  TokenResponse refreshTokens(String refreshToken);

  Set<String> extractPermissionNames(String accessToken);

  DecodedJWT decodeAccessToken(String accessToken);
}
