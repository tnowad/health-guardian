package com.example.health_guardian_server.dtos.requests.diagnostic;

import jakarta.validation.constraints.NotBlank;
import java.util.Date;
import lombok.Data;
import lombok.NonNull;

@Data
public class CreateDiagnosticResultRequest {
  @NonNull
  @NotBlank(message = "userId is required")
  private String userId;

  @NotBlank(message = "testName is required")
  @NonNull
  private String testName;

  @NonNull
  @NotBlank(message = "resultDate is required")
  private Date resultDate;

  @NonNull
  @NotBlank(message = "resultValue is required")
  private String resultValue;

  private String notes;

  private String file;
}
