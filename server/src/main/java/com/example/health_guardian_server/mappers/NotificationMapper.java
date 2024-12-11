package com.example.health_guardian_server.mappers;

import com.example.health_guardian_server.dtos.requests.appointment.CreateAppointmentRequest;
import com.example.health_guardian_server.dtos.requests.notification.CreateNotificationRequest;
import com.example.health_guardian_server.dtos.requests.notification.UpdateNotificationRequest;
import com.example.health_guardian_server.dtos.responses.SimpleResponse;
import com.example.health_guardian_server.dtos.responses.notification.NotificationResponse;
import com.example.health_guardian_server.entities.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

  @Mapping(source = "user.id", target = "userId")
  NotificationResponse toNotificationResponse(Notification notification);

  Notification toNotification(CreateNotificationRequest createNotificationRequest);

  Notification toNotification(UpdateNotificationRequest updateNotificationRequest);

  static SimpleResponse toNotificationSimpleResponse(Notification notification) {
    return SimpleResponse.builder()
      .id(notification.getId())
      .message("NotificationId: " + notification.getId())
      .build();
  }
}
