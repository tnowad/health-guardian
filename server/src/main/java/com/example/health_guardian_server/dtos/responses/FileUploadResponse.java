package com.example.health_guardian_server.dtos.responses;

import lombok.Builder;

@Builder
public class FileUploadResponse {
  private String id;
  private String url;
}
