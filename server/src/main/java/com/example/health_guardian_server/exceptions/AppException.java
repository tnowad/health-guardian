package com.example.health_guardian_server.exceptions;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@FieldDefaults(level = AccessLevel.PROTECTED)
public class AppException extends RuntimeException {

  public AppException(String Message, HttpStatus httpStatus, Object... moreInfo) {
    super(Message);
    this.httpStatus = httpStatus;
    this.moreInfo = moreInfo;
  }

  public AppException(CommonErrorCode commonErrorCode, HttpStatus httpStatus, Object... moreInfo) {
    super(commonErrorCode.getMessage());
    this.httpStatus = httpStatus;
    this.commonErrorCode = commonErrorCode;
    this.moreInfo = moreInfo;
  }

  @Setter Object[] moreInfo;
  final HttpStatus httpStatus;
  CommonErrorCode commonErrorCode;
}
