package com.example.health_guardian_server.dtos.responses.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStatusNotificationResponse {
  private String id;
  private String message;
}
