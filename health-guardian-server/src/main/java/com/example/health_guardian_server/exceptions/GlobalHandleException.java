package com.example.health_guardian_server.exceptions;

import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.health_guardian_server.dtos.responses.CommonResponse;
import com.example.health_guardian_server.exceptions.errorcodes.AuthenticationErrorCode;
import com.example.health_guardian_server.exceptions.errorcodes.FileStorageErrorCode;

import static org.springframework.http.HttpStatus.*;
import static com.example.health_guardian_server.exceptions.errorcodes.AuthenticationErrorCode.*;
import static com.example.health_guardian_server.components.Translator.getLocalizedMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalHandleException {

  // Handle exceptions that are not caught by other handlers
  @ExceptionHandler(value = RuntimeException.class)
  ResponseEntity<CommonResponse<?>> handlingRuntimeException(RuntimeException exception) {
    log.error("Exception: ", exception);
    CommonResponse<?> commonResponse = new CommonResponse<>();

    commonResponse.setMessage(getLocalizedMessage("uncategorized"));

    return ResponseEntity.badRequest().body(commonResponse);
  }

  // Handle exceptions about messages that are not found
  @ExceptionHandler(value = NoSuchMessageException.class)
  ResponseEntity<CommonResponse<?>> handlingNoSuchMessageException(NoSuchMessageException exception) {
    log.error("Exception: ", exception);
    CommonResponse<?> commonResponse = new CommonResponse<>();

    commonResponse.setMessage(getLocalizedMessage("no_such_message"));

    return ResponseEntity.badRequest().body(commonResponse);
  }

  @ExceptionHandler(value = AuthorizationDeniedException.class)
  ResponseEntity<?> handlingAuthorizationDeniedException(AuthorizationDeniedException exception) {
    log.error("Exception: ", exception);
    return ResponseEntity.status(UNAUTHORIZED).body(
        CommonResponse.builder().message(getLocalizedMessage("not_have_permission")));
  }

  // Handle authentication exceptions
  @ExceptionHandler(value = AuthException.class)
  ResponseEntity<CommonResponse<?>> handlingAuthExceptions(AuthException exception) {
    AuthenticationErrorCode authenticationErrorCode = exception.getAuthenticationErrorCode();
    CommonResponse<Map<String, String>> commonResponse = new CommonResponse<>();

    commonResponse.setErrorCode(authenticationErrorCode.getCode());
    commonResponse.setMessage(getLocalizedMessage(authenticationErrorCode.getMessage()));

    Map<String, String> errors = new HashMap<>();
    try {
      switch (authenticationErrorCode) {
        case VALIDATION_ERROR -> {
          errors.put("email", getLocalizedMessage(PASSWORD_EXPIRED.getMessage()));
          errors.put("password", getLocalizedMessage(PASSWORD_EXPIRED.getMessage()));
        }
        case PASSWORD_EXPIRED -> errors.put("password", getLocalizedMessage(PASSWORD_EXPIRED.getMessage()));

        case TOKEN_INVALID -> errors.put("token", getLocalizedMessage(TOKEN_INVALID.getMessage()));

        case WRONG_PASSWORD -> errors.put("password", getLocalizedMessage(WRONG_PASSWORD.getMessage()));

        case PASSWORD_MIS_MATCH -> errors.put("password", getLocalizedMessage(PASSWORD_MIS_MATCH.getMessage()));

        case EMAIL_ALREADY_IN_USE -> errors.put("email", getLocalizedMessage(EMAIL_ALREADY_IN_USE.getMessage()));

        case WEAK_PASSWORD -> errors.put("password", getLocalizedMessage(WEAK_PASSWORD.getMessage()));

        case INVALID_EMAIL -> errors.put("email", getLocalizedMessage(INVALID_EMAIL.getMessage()));

        case TERMS_NOT_ACCEPTED -> errors.put("termsAccepted", getLocalizedMessage(TERMS_NOT_ACCEPTED.getMessage()));

        case CODE_INVALID -> errors.put("code", getLocalizedMessage(CODE_INVALID.getMessage()));

        default -> errors = null;
      }

    } catch (NoSuchMessageException exception1) {
      CommonResponse<?> commonResponse1 = new CommonResponse<>();
      commonResponse1.setMessage(getLocalizedMessage("no_such_message"));
      return ResponseEntity.badRequest().body(commonResponse1);
    }

    commonResponse.setResults(errors);

    return ResponseEntity.status(exception.getHttpStatus()).body(commonResponse);
  }

  // Handle file storage exceptions
  @ExceptionHandler(value = FileStorageException.class)
  ResponseEntity<CommonResponse<?>> handlingFileStorageExceptions(FileStorageException exception) {
    FileStorageErrorCode authenticationErrorCode = exception.getFileStorageErrorCode();
    CommonResponse<?> commonResponse = new CommonResponse<>();

    commonResponse.setErrorCode(authenticationErrorCode.getCode());
    commonResponse.setMessage(getLocalizedMessage(authenticationErrorCode.getMessage()));
    if (exception.getMoreInfo() != null) {
      commonResponse.setMessage(getLocalizedMessage(authenticationErrorCode.getMessage(), exception.getMoreInfo()));
    }

    return ResponseEntity.status(exception.getHttpStatus()).body(commonResponse);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<CommonResponse<?>> handleMethodArgumentNotValidExceptions(MethodArgumentNotValidException e) {
    try {
      Map<String, String> errors = new HashMap<>();
      e.getBindingResult().getAllErrors()
          .forEach((error) -> {
            String field = ((FieldError) error).getField();
            String errorMessage = getLocalizedMessage(error.getDefaultMessage());
            errors.put(field, errorMessage);
          });

      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
          CommonResponse.builder()
              .errorCode(VALIDATION_ERROR.getCode())
              .message(getLocalizedMessage(VALIDATION_ERROR.getMessage()))
              .results(errors)
              .build());

    } catch (NoSuchMessageException exception) {
      CommonResponse<?> commonResponse = new CommonResponse<>();
      commonResponse.setMessage(getLocalizedMessage("no_such_message"));
      return ResponseEntity.badRequest().body(commonResponse);
    }
  }

}
