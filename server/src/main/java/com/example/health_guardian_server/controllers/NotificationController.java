package com.example.health_guardian_server.controllers;

import com.example.health_guardian_server.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
  private final NotificationService notificationService;

  @PostMapping("/send")
  public ResponseEntity<String> sendEmail(
      @RequestParam String to, @RequestParam String subject, @RequestParam String body) {

    notificationService.sendEmail(to, subject, body);
    return ResponseEntity.ok("Email sent to " + to);
  }
}
