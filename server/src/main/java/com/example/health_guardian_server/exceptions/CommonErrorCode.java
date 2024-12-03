package com.example.health_guardian_server.exceptions;

import lombok.Getter;

@Getter
public enum CommonErrorCode {
  USER_BANNED("USER-BANNED", "user_banned"),
  OBJECT_NOT_FOUND("OBJECT-NOT-FOUND", "object_not_found"),
  ROLE_NOT_FOUND("ROLE-NOT-FOUND", "role_not_found"),
  RATING_FAILED("RATING-FAILED", "rating_failed");

  CommonErrorCode(String code, String message) {
    this.code = code;
    this.message = message;
  }

  private final String code;
  private final String message;
}
