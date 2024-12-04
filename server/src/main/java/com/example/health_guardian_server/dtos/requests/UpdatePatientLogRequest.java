package com.example.health_guardian_server.dtos.requests;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdatePatientLogRequest {
  List<String> fileNames;

  private String logType;

  private String description;

  private String message;
}
