package com.example.health_guardian_server.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.health_guardian_server.dtos.responses.TokensResponse;
import com.example.health_guardian_server.entities.Permission;
import com.example.health_guardian_server.entities.Role;
import com.example.health_guardian_server.entities.User;
import com.example.health_guardian_server.services.TokenService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

  @Override
  public String generateRefreshToken(String userId) {
    return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiIxMjM0NTY3ODkwIiwicm9sZXMiOlsiUk9MRV9BRE1JTiIsIlJPTEVfVVNFUiJdLCJwZXJtaXNzaW9ucyI6WyJQZXJtaXNzaW9uMSIsIlBlcm1pc3Npb24yIl0sImlhdCI6MTUxNjIzOTAyMn0.3Jv3";
  }

  @Override
  public String generateAccessToken(String userId, List<String> roles, List<String> permissions) {
    return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiIxMjM0NTY3ODkwIiwicm9sZXMiOlsiUk9MRV9BRE1JTiIsIlJPTEVfVVNFUiJdLCJwZXJtaXNzaW9ucyI6WyJQZXJtaXNzaW9uMSIsIlBlcm1pc3Npb24yIl0sImlhdCI6MTUxNjIzOTAyMn0.3Jv3";
  }

  @Override
  public TokensResponse generateTokens(User user, List<Role> roles, List<Permission> permissions) {
    return TokensResponse.builder()
        .accessToken(generateAccessToken(user.getId(),
            roles.stream().map(Role::getId).toList(), permissions.stream().map(Permission::getId).toList()))
        .refreshToken(generateRefreshToken(user.getId()))
        .build();
  }

}
