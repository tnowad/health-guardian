package com.example.health_guardian_server.exceptions;

import org.springframework.http.HttpStatus;

import com.example.health_guardian_server.exceptions.errorcodes.AuthenticationErrorCode;

import lombok.Getter;

@Getter
public class AuthException extends RuntimeException {

  public AuthException(AuthenticationErrorCode authenticationErrorCode, HttpStatus httpStatus) {
    super(authenticationErrorCode.getMessage());
    this.authenticationErrorCode = authenticationErrorCode;
    this.httpStatus = httpStatus;
  }

  private final AuthenticationErrorCode authenticationErrorCode;
  private final HttpStatus httpStatus;

}
