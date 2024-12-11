package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.configurations.BrevoConfig;
import com.example.health_guardian_server.dtos.requests.notification.CreateNotificationRequest;
import com.example.health_guardian_server.dtos.requests.notification.ListNotificationRequest;
import com.example.health_guardian_server.dtos.requests.notification.UpdateNotificationRequest;
import com.example.health_guardian_server.dtos.responses.SimpleResponse;
import com.example.health_guardian_server.dtos.responses.notification.NotificationResponse;
import com.example.health_guardian_server.dtos.responses.notification.UpdateStatusNotificationResponse;
import com.example.health_guardian_server.entities.Notification;
import com.example.health_guardian_server.entities.User;
import com.example.health_guardian_server.entities.VisitSummary;
import com.example.health_guardian_server.mappers.NotificationMapper;
import com.example.health_guardian_server.mappers.VisitSummaryMapper;
import com.example.health_guardian_server.repositories.NotificationRepository;
import com.example.health_guardian_server.repositories.UserRepository;
import com.example.health_guardian_server.services.NotificationService;
import java.util.HashMap;
import java.util.Map;

import com.example.health_guardian_server.specifications.NotificationSpecification;
import com.example.health_guardian_server.specifications.VisitSummarySpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

  private final NotificationRepository notificationRepository;
  private final NotificationMapper notificationMapper;
  private final UserRepository userRepository;
  private final BrevoConfig brevoConfig;
  private final RestTemplate restTemplate;

  @Override
  public void sendEmail(String to, String subject, String body) {
    String url = brevoConfig.getApiUrl() + "/v3/smtp/email";

    // Cấu hình Header
    HttpHeaders headers = new HttpHeaders();
    headers.set("accept", "application/json");
    headers.set("api-key", brevoConfig.getApiKey());
    headers.set("Content-Type", "application/json");

    // Payload
    Map<String, Object> payload = new HashMap<>();
    payload.put("sender", Map.of("email", brevoConfig.getFromMail()));
    payload.put("to", new Object[] {Map.of("email", to)});
    payload.put("subject", subject);
    payload.put("htmlContent", body);

    // Gửi request
    HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

    ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
    if (response.getStatusCode().is2xxSuccessful()) {
      System.out.println("Email sent successfully!");
    } else {
      System.err.println("Failed to send email: " + response.getBody());
    }
  }

  @Override
  public Page<NotificationResponse> getAllNotifications(ListNotificationRequest request) {
    log.debug("Fetching all notifications with request: {}", request);
    PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
    NotificationSpecification specification = new NotificationSpecification(request);

    var notifications =
      notificationRepository
        .findAll(specification, pageRequest)
        .map(notificationMapper::toNotificationResponse);

    log.info("Fetched {} notifications", notifications.getTotalElements());
    return notifications;
  }

  @Override
  public NotificationResponse getNotificationById(String id) {
    log.debug("Fetching notifications with id: {}", id);
    return notificationRepository
      .findById(id)
      .map(notificationMapper::toNotificationResponse)
      .orElseThrow(
        () -> {
          log.error("Notifications not found with id: {}", id);
          return new ResourceNotFoundException(
            "Notifications not found with id " + id);
        });
  }

  @Override
  public NotificationResponse createNotification(CreateNotificationRequest request) {
    log.debug("Creating notification: {}", request);
    Notification createdNotification = notificationMapper.toNotification(request);
    User user =
      userRepository
        .findById(request.getUserId())
        .orElseThrow(
          () -> {
            log.error("User not found with id: {}", request.getUserId());
            return new ResourceNotFoundException(
              "User not found with id " + request.getUserId());
          });
    createdNotification.setUser(user);
    Notification notification = notificationRepository.save(createdNotification);
    log.info("Notification created with id: {}", createdNotification.getId());
    return notificationMapper.toNotificationResponse(notification);
  }

  @Override
  public NotificationResponse updateNotification(String id, UpdateNotificationRequest request) {
    log.debug("Updating notification with id: {}", id);
    Notification existingNotification =
      notificationRepository
        .findById(id)
        .orElseThrow(
          () -> {
            log.error("Notification not found with id: {}", id);
            return new ResourceNotFoundException(
              "Notification not found with id " + id);
          });

    User user =
      userRepository
        .findById(request.getUserId())
        .orElseThrow(
          () -> {
            log.error("User not found with id: {}", request.getUserId());
            return new ResourceNotFoundException(
              "User not found with id " + request.getUserId());
          });

    Notification updatedNotification = notificationMapper.toNotification(request);
    updatedNotification.setUser(user);
    Notification notification = notificationRepository.save(updatedNotification);
    log.info("Notification updated with id: {}", request.getId());
    return notificationMapper.toNotificationResponse(notification);
  }

  @Override
  public SimpleResponse deleteNotification(String id) {
    log.debug("Deleting notification with id: {}", id);
    Notification existingNotification =
      notificationRepository
        .findById(id)
        .orElseThrow(
          () -> {
            log.error("Notification not found with id: {}", id);
            return new ResourceNotFoundException(
              "Notification not found with id " + id);
          });

    notificationRepository.delete(existingNotification);
    log.info("Notification deleted with id: {}", id);
    return NotificationMapper.toNotificationSimpleResponse(existingNotification);
  }

  @Override
  public UpdateStatusNotificationResponse updateStatusNotification(String id) {
    log.debug("Updating status notification with id: {}", id);
    Notification existingNotification =
      notificationRepository
        .findById(id)
        .orElseThrow(
          () -> {
            log.error("Notification not found with id: {}", id);
            return new ResourceNotFoundException(
              "Notification not found with id " + id);
          });
    boolean readStatus = existingNotification.isReadStatus();
    existingNotification.setReadStatus(!readStatus);
    notificationRepository.save(existingNotification);
    log.info("Status Notification updated with id: {}", id);

    UpdateStatusNotificationResponse updateStatusNotificationResponse
      = UpdateStatusNotificationResponse.builder()
      .id(existingNotification.getId())
      .message("Status of notification changed to: " + !readStatus)
      .build();

    return updateStatusNotificationResponse;
  }
}
