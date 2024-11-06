package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.responses.TokensResponse;
import com.example.health_guardian_server.entities.User;
import com.example.health_guardian_server.services.TokenService;
import java.util.Set;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TokenServiceImpl implements TokenService {

  @Override
  public String generateRefreshToken(String userId) {
    return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9."
        + "eyJ1c2VySWQiOiIxMjM0NTY3ODkwIiwicm9sZXMiOlsiUk9MRV9BRE1JTiIsIlJPT"
        + "EVfVVNFUiJdLCJwZXJtaXNzaW9ucyI6WyJQZXJtaXNzaW9uMSIsIlBlcm1pc3Npb2"
        + "4yIl0sImlhdCI6MTUxNjIzOTAyMn0.3Jv3";
  }

  @Override
  public String generateAccessToken(String userId, Set<String> permissionIds) {
    return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9."
        + "eyJ1c2VySWQiOiIxMjM0NTY3ODkwIiwicm9sZXMiOlsiUk9MRV9BRE1JTiIsIlJPT"
        + "EVfVVNFUiJdLCJwZXJtaXNzaW9ucyI6WyJQZXJtaXNzaW9uMSIsIlBlcm1pc3Npb2"
        + "4yIl0sImlhdCI6MTUxNjIzOTAyMn0.3Jv3";
  }

  @Override
  public TokensResponse generateTokens(User user, Set<String> permissionIds) {
    return TokensResponse.builder()
        .accessToken(generateAccessToken(user.getId(), permissionIds))
        .refreshToken(generateRefreshToken(user.getId()))
        .build();
  }
}
