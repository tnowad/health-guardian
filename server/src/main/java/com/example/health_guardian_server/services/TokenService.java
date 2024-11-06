package com.example.health_guardian_server.services;

import java.util.List;

import com.example.health_guardian_server.dtos.responses.TokensResponse;
import com.example.health_guardian_server.entities.Permission;
import com.example.health_guardian_server.entities.Role;
import com.example.health_guardian_server.entities.User;

public interface TokenService {

  String generateRefreshToken(String userId);

  String generateAccessToken(String userId, List<String> roles, List<String> permissions);

  TokensResponse generateTokens(User user, List<Role> roles, List<Permission> permissions);
}
