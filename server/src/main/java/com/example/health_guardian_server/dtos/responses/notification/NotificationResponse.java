package com.example.health_guardian_server.dtos.responses.notification;

import com.example.health_guardian_server.entities.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponse {
  private String id;
  private String userId;
  private String title;
  private NotificationType notificationType;
  private Date notificationDate;
  private boolean readStatus;
}
