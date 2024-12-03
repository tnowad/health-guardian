package com.example.health_guardian_server.exceptions.file;

import com.example.health_guardian_server.exceptions.AppException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class FileException extends AppException {

  public FileException(FileErrorCode fileErrorCode, HttpStatus httpStatus) {
    super(fileErrorCode.getMessage(), httpStatus);
    this.fileErrorCode = fileErrorCode;
  }

  private final FileErrorCode fileErrorCode;
}
