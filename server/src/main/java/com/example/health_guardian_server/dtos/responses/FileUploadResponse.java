package com.example.health_guardian_server.dtos.responses;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FileUploadResponse {
  private String id;
  private String url;
}
