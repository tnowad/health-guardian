package com.example.health_guardian_server.exceptions.errorcodes;

import lombok.Getter;

@Getter
public enum ProfileErrorCode {
  // Validation Errors
  VALIDATION_ERROR("auth/validation-error", "validation_error"),
  INVALID_EMAIL("auth/invalid-email", "invalid_email"),
  WEAK_PASSWORD("auth/weak-password", "weak_password"),
  PASSWORD_MIS_MATCH("auth/password-mismatch", "password_mis_match"),
  TERMS_NOT_ACCEPTED("auth/terms-not-accepted", "terms_not_accepted"),
  ;

  ProfileErrorCode(String code, String message) {
    this.code = code;
    this.description = message;
  }

  private final String code;
  private final String description;
}
