package com.example.health_guardian_server.services;

import com.example.health_guardian_server.dtos.requests.notification.CreateNotificationRequest;
import com.example.health_guardian_server.dtos.requests.notification.ListNotificationRequest;
import com.example.health_guardian_server.dtos.requests.notification.UpdateNotificationRequest;
import com.example.health_guardian_server.dtos.requests.visit_summary.CreateVisitSummaryRequest;
import com.example.health_guardian_server.dtos.requests.visit_summary.ListVisitSummaryRequest;
import com.example.health_guardian_server.dtos.requests.visit_summary.UpdateVisitSummaryRequest;
import com.example.health_guardian_server.dtos.responses.SimpleResponse;
import com.example.health_guardian_server.dtos.responses.notification.NotificationResponse;
import com.example.health_guardian_server.dtos.responses.visit_summary.VisitSummaryResponse;
import org.springframework.data.domain.Page;

public interface NotificationService {

  void sendEmail(String to, String subject, String body);

  Page<NotificationResponse> getAllNotifications(ListNotificationRequest request);

  NotificationResponse getNotificationById(String id);

  NotificationResponse createNotification(CreateNotificationRequest request);

  NotificationResponse updateNotification(String id, UpdateNotificationRequest request);

  SimpleResponse deleteNotification(String id);
}
