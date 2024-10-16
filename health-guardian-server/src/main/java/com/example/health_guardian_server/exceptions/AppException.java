package com.example.health_guardian_server.exceptions;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

import com.example.health_guardian_server.exceptions.errorcodes.AppErrorCode;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppException extends RuntimeException {

  public AppException(AppErrorCode appErrorCode, HttpStatus httpStatus, String reason, Object... moreInfo) {
    super(appErrorCode.getMessage());
    this.reason = reason;
    this.appErrorCode = appErrorCode;
    this.httpStatus = httpStatus;
    this.moreInfo = moreInfo;
  }

  final AppErrorCode appErrorCode;
  final String reason;
  final HttpStatus httpStatus;
  final Object[] moreInfo;

}
