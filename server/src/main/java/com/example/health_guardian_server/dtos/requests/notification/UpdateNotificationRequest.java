package com.example.health_guardian_server.dtos.requests.notification;

import com.example.health_guardian_server.entities.enums.NotificationType;
import lombok.Data;

import java.util.Date;

@Data
public class UpdateNotificationRequest {
  private String id;
  private String userId;
  private String title;
  private NotificationType notificationType;
  private Date notificationDate;
  private boolean readStatus;
}
