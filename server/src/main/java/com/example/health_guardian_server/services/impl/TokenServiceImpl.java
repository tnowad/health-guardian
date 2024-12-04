package com.example.health_guardian_server.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.health_guardian_server.dtos.responses.TokensResponse;
import com.example.health_guardian_server.services.PermissionService;
import com.example.health_guardian_server.services.RoleService;
import com.example.health_guardian_server.services.TokenService;
import java.util.Date;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TokenServiceImpl implements TokenService {

  RoleService roleService;
  PermissionService permissionService;

  @NonFinal
  @Value("${jwt.accessSignerKey}")
  String accessSignerKey;

  @NonFinal
  @Value("${jwt.refreshSignerKey}")
  String refreshSignerKey;

  @NonFinal
  @Value("${jwt.valid-duration}")
  long accessTokenValidityDuration;

  @NonFinal
  @Value("${jwt.refreshable-duration}")
  long refreshTokenValidityDuration;

  private static final String ISSUER = "health-guardian-server";

  @Override
  public String generateAccessToken(String userId, Set<String> permissionNames) {
    Date now = new Date();
    Date expirationTime = new Date(now.getTime() + accessTokenValidityDuration);

    log.info("Generating access token for user: {}", userId);
    return JWT.create()
        .withIssuer(ISSUER)
        .withSubject(userId)
        .withIssuedAt(now)
        .withExpiresAt(expirationTime)
        .withClaim("permissions", List.copyOf(permissionNames))
        .sign(Algorithm.HMAC256(accessSignerKey));
  }

  @Override
  public String generateRefreshToken(String userId) {
    Date now = new Date();
    Date expirationTime = new Date(now.getTime() + refreshTokenValidityDuration);

    log.info("Generating refresh token for user: {}", userId);
    return JWT.create()
        .withIssuer(ISSUER)
        .withSubject(userId)
        .withIssuedAt(now)
        .withExpiresAt(expirationTime)
        .sign(Algorithm.HMAC256(refreshSignerKey));
  }

  @Override
  public TokensResponse generateTokens(String userId, Set<String> permissionNames) {
    log.info("Generating tokens for user: {}", userId);
    String accessToken = generateAccessToken(userId, permissionNames);
    String refreshToken = generateRefreshToken(userId);

    log.info("Tokens generated successfully for user: {}", userId);
    return new TokensResponse(accessToken, refreshToken);
  }

  @Override
  public TokensResponse refreshTokens(String refreshToken) {
    try {
      log.info("Refreshing tokens using refresh token.");
      DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(refreshSignerKey))
          .withIssuer(ISSUER)
          .build()
          .verify(refreshToken);
      String userId = decodedJWT.getSubject();
      log.info("Refresh token verified successfully for user: {}", userId);

      Set<String> roleIds = roleService.getRoleIdsByUserId(userId);
      Set<String> permissionNames = permissionService.getPermissionNamesByRoleIds(roleIds);

      log.info("Fetched roles and permissions for user: {}", userId);
      String newAccessToken = generateAccessToken(userId, permissionNames);
      String newRefreshToken = refreshToken;

      if (decodedJWT.getExpiresAt().getTime() - System.currentTimeMillis() < refreshTokenValidityDuration * 0.1) {
        log.info("Generating new refresh token for user: {}", userId);
        newRefreshToken = generateRefreshToken(userId);
      }

      log.info("Tokens refreshed successfully for user: {}", userId);
      return new TokensResponse(newAccessToken, newRefreshToken);

    } catch (Exception e) {
      log.error("Token refresh failed", e);
      throw new RuntimeException("Invalid refresh token");
    }
  }

  @Override
  public Set<String> extractPermissionNames(String accessToken) {
    try {
      log.info("Extracting permissions from access token.");
      DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(accessSignerKey))
          .withIssuer(ISSUER)
          .build()
          .verify(accessToken);

      log.info("Permissions extracted successfully from access token.");
      return Set.copyOf(decodedJWT.getClaim("permissions").asList(String.class));
    } catch (Exception e) {
      log.error("Failed to extract permissions from access token", e);
      throw new RuntimeException("Invalid access token");
    }
  }

  @Override
  public DecodedJWT decodeAccessToken(String accessToken) {
    try {
      log.info("Decoding access token.");
      DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(accessSignerKey))
          .withIssuer(ISSUER)
          .build()
          .verify(accessToken);

      log.info("Access token decoded successfully.");
      return decodedJWT;
    } catch (Exception e) {
      log.error("Failed to decode access token", e);
      throw new RuntimeException("Invalid access token");
    }
  }
}
