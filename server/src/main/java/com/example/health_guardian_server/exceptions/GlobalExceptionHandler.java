package com.example.health_guardian_server.exceptions;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(OAuth2AuthenticationException.class)
  public ResponseEntity<Object> handleOAuth2AuthenticationException(
      OAuth2AuthenticationException ex) {
    log.error("OAuth2 Authentication error: {}", ex.getMessage(), ex);
    Map<String, String> response = new HashMap<>();
    response.put("error", "Unauthorized");
    response.put("message", ex.getMessage());
    return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex) {
    log.error("Authentication error: {}", ex.getMessage(), ex);
    Map<String, String> response = new HashMap<>();
    response.put("error", "Unauthorized");
    response.put("message", ex.getMessage());
    return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
    log.warn("Validation error: {}", ex.getMessage(), ex);
    Map<String, String> response = new HashMap<>();
    ex.getBindingResult()
        .getFieldErrors()
        .forEach(
            error -> {
              response.put(error.getField(), error.getDefaultMessage());
            });
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(BindException.class)
  public ResponseEntity<Object> handleBindException(BindException ex) {
    log.warn("Binding error: {}", ex.getMessage(), ex);
    Map<String, String> response = new HashMap<>();
    ex.getBindingResult()
        .getFieldErrors()
        .forEach(
            error -> {
              response.put(error.getField(), error.getDefaultMessage());
            });
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleException(Exception ex) {
    log.error("Internal server error: {}", ex.getMessage(), ex);
    Map<String, String> response = new HashMap<>();
    response.put("error", "Internal Server Error");
    response.put("message", ex.getMessage());
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
