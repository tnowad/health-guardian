package com.example.health_guardian_server.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.health_guardian_server.dtos.responses.auth.TokenResponse;
import com.example.health_guardian_server.services.TokenService;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TokenServiceImpl implements TokenService {
  RedisTemplate<String, String> redisTemplate;

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
  public String generateAccessToken(String userId) {
    String cacheKey = "accessToken:" + userId;
    ValueOperations<String, String> valueOps = redisTemplate.opsForValue();

    if (Boolean.TRUE.equals(redisTemplate.hasKey(cacheKey))) {
      String cachedToken = valueOps.get(cacheKey);
      log.debug("Returning cached access token for user {}: {}", userId, cachedToken);
      return cachedToken;
    }

    Date now = new Date();
    Date expirationTime = new Date(now.getTime() + accessTokenValidityDuration);

    String accessToken = JWT.create()
        .withIssuer(ISSUER)
        .withSubject(userId)
        .withIssuedAt(now)
        .withExpiresAt(expirationTime)
        .sign(Algorithm.HMAC256(accessSignerKey));

    valueOps.set(cacheKey, accessToken, accessTokenValidityDuration, TimeUnit.MILLISECONDS);
    log.debug("Generated and cached access token for user {}: {}", userId, accessToken);

    return accessToken;
  }

  @Override
  public String generateRefreshToken(String userId) {
    String cacheKey = "refreshToken:" + userId;
    ValueOperations<String, String> valueOps = redisTemplate.opsForValue();

    if (Boolean.TRUE.equals(redisTemplate.hasKey(cacheKey))) {
      String cachedToken = valueOps.get(cacheKey);
      log.debug("Returning cached refresh token for user {}: {}", userId, cachedToken);
      return cachedToken;
    }

    Date now = new Date();
    Date expirationTime = new Date(now.getTime() + refreshTokenValidityDuration);

    String refreshToken = JWT.create()
        .withIssuer(ISSUER)
        .withSubject(userId)
        .withIssuedAt(now)
        .withExpiresAt(expirationTime)
        .sign(Algorithm.HMAC256(refreshSignerKey));

    valueOps.set(cacheKey, refreshToken, refreshTokenValidityDuration, TimeUnit.MILLISECONDS);
    log.debug("Generated and cached refresh token for user {}: {}", userId, refreshToken);

    return refreshToken;
  }

  @Override
  public TokenResponse refreshTokens(String refreshToken) {
    try {
      DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(refreshSignerKey))
          .withIssuer(ISSUER)
          .build()
          .verify(refreshToken);

      String userId = decodedJWT.getSubject();

      String newAccessToken = generateAccessToken(userId);
      String newRefreshToken = refreshToken;

      if (decodedJWT.getExpiresAt().getTime() - System.currentTimeMillis() < refreshTokenValidityDuration * 0.1) {
        newRefreshToken = generateRefreshToken(userId);
      }

      return new TokenResponse(newAccessToken, newRefreshToken);

    } catch (Exception e) {
      log.error("Token refresh failed", e);
      throw new RuntimeException("Invalid refresh token");
    }
  }

  @Override
  public Set<String> extractPermissionNames(String accessToken) {
    try {
      DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(accessSignerKey))
          .withIssuer(ISSUER)
          .build()
          .verify(accessToken);

      return Set.copyOf(decodedJWT.getClaim("permissions").asList(String.class));
    } catch (Exception e) {
      log.error("Failed to extract permissions from access token", e);
      throw new RuntimeException("Invalid access token");
    }
  }

  @Override
  public DecodedJWT decodeAccessToken(String accessToken) {
    try {
      return JWT.require(Algorithm.HMAC256(accessSignerKey))
          .withIssuer(ISSUER)
          .build()
          .verify(accessToken);
    } catch (Exception e) {
      log.error("Failed to decode access token", e);
      throw new RuntimeException("Invalid access token");
    }
  }

  @Override
  public TokenResponse generateTokens(String userId) {
    log.info("Generating tokens for user: {}", userId);

    String accessToken = generateAccessToken(userId);
    String refreshToken = generateRefreshToken(userId);

    log.info("Tokens generated successfully for user: {}", userId);
    return new TokenResponse(accessToken, refreshToken);
  }
}
