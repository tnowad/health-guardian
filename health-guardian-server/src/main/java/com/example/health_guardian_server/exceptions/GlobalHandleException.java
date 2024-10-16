package com.example.health_guardian_server.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.health_guardian_server.dtos.responses.CommonResponse;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalHandleException {
  private static final long serialVersionUID = 1L;

  @ExceptionHandler(value = RuntimeException.class)
  public ResponseEntity<CommonResponse<?>> handlingRuntimeException(RuntimeException exception) {
    log.error("Runtime Exception: {}", exception.getMessage());
    CommonResponse<?> response = CommonResponse.builder()
        .message("uncategorized").build();
    return ResponseEntity.badRequest().body(response);
  }

  @ExceptionHandler(value = AppException.class)
  public ResponseEntity<CommonResponse<?>> handlingAppException(AppException exception) {
    log.error("App Exception: {}", exception.getMessage());

    CommonResponse<?> response = CommonResponse.builder()
        .errorCode(exception.getAppErrorCode().getCode())
        .message(exception.getMessage()).build();
    return ResponseEntity.status(exception.getHttpStatus()).body(response);
  }

  @ExceptionHandler(value = AuthException.class)
  public ResponseEntity<CommonResponse<?>> handlingAuthException(AuthException exception) {
    log.error("Auth Exception: {}", exception.getMessage());
    CommonResponse<?> response = CommonResponse.builder()
        .errorCode(exception.getAuthenticationErrorCode().getCode())
        .message(exception.getMessage()).build();
    return ResponseEntity.status(exception.getHttpStatus()).body(response);
  }
}
