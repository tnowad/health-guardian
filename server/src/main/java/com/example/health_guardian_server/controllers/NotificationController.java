package com.example.health_guardian_server.controllers;

import com.example.health_guardian_server.dtos.requests.notification.CreateNotificationRequest;
import com.example.health_guardian_server.dtos.requests.notification.ListNotificationRequest;
import com.example.health_guardian_server.dtos.requests.notification.UpdateNotificationRequest;
import com.example.health_guardian_server.dtos.requests.visit_summary.CreateVisitSummaryRequest;
import com.example.health_guardian_server.dtos.requests.visit_summary.ListVisitSummaryRequest;
import com.example.health_guardian_server.dtos.requests.visit_summary.UpdateVisitSummaryRequest;
import com.example.health_guardian_server.dtos.responses.SimpleResponse;
import com.example.health_guardian_server.dtos.responses.notification.NotificationResponse;
import com.example.health_guardian_server.dtos.responses.notification.UpdateStatusNotificationResponse;
import com.example.health_guardian_server.dtos.responses.visit_summary.VisitSummaryResponse;
import com.example.health_guardian_server.entities.Notification;
import com.example.health_guardian_server.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("notifications")
@RequiredArgsConstructor
public class NotificationController {
  private final NotificationService notificationService;

  @PostMapping("/send")
  public ResponseEntity<String> sendEmail(
      @RequestParam String to, @RequestParam String subject, @RequestParam String body) {

    notificationService.sendEmail(to, subject, body);
    return ResponseEntity.ok("Email sent to " + to);
  }

  @GetMapping()
  public ResponseEntity<Page<NotificationResponse>> getAllNotifications(
    @ModelAttribute ListNotificationRequest request) {
    Page<NotificationResponse> notifications = notificationService.getAllNotifications(request);
    return new ResponseEntity<>(notifications, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<NotificationResponse> getNotificationById(@PathVariable String id) {
    NotificationResponse notification = notificationService.getNotificationById(id);
    return new ResponseEntity<>(notification, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<NotificationResponse> createNotification(
    @RequestBody CreateNotificationRequest request) {
    NotificationResponse notification = notificationService.createNotification(request);
    return new ResponseEntity<>(notification, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<NotificationResponse> updateNotification(
    @PathVariable String id, @RequestBody UpdateNotificationRequest request) {
    NotificationResponse notification = notificationService.updateNotification(id, request);
    return new ResponseEntity<>(notification, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<SimpleResponse> deleteNotification(@PathVariable String id) {
    return new ResponseEntity<>(notificationService.deleteNotification(id), HttpStatus.OK);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<UpdateStatusNotificationResponse> updateStatusNotification(@PathVariable String id) {
    return new ResponseEntity<>(notificationService.updateStatusNotification(id), HttpStatus.OK);
  }
}
