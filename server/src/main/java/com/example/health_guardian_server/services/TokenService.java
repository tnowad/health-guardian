package com.example.health_guardian_server.services;

import com.example.health_guardian_server.dtos.responses.TokensResponse;
import com.example.health_guardian_server.entities.User;
import java.util.Set;

public interface TokenService {

  String generateRefreshToken(String userId);

  String generateAccessToken(String userId, Set<String> permissionIds);

  TokensResponse generateTokens(User user, Set<String> permissionIds);
}
