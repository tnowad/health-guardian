package com.example.health_guardian_server.dtos.requests.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateUserRequest {
  private String avatar;
}