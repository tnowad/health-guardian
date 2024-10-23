package com.example.health_guardian_server.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import com.example.health_guardian_server.exceptions.errorcodes.FileStorageErrorCode;

@Getter
public class FileStorageException extends RuntimeException {

  public FileStorageException(FileStorageErrorCode fileStorageErrorCode, HttpStatus httpStatus) {
    super(fileStorageErrorCode.getMessage());
    this.fileStorageErrorCode = fileStorageErrorCode;
    this.httpStatus = httpStatus;
  }

  private final FileStorageErrorCode fileStorageErrorCode;
  private Object[] moreInfo;
  private final HttpStatus httpStatus;

  public void setMoreInfo(Object[] moreInfo) {
    this.moreInfo = moreInfo;
  }

}
