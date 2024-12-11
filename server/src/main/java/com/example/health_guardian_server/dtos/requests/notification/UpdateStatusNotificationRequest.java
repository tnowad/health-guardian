package com.example.health_guardian_server.dtos.requests.notification;

import lombok.Data;

@Data
public class UpdateStatusNotificationRequest {
  private boolean readStatus;
}
