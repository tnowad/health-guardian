package com.example.health_guardian_server.services.impl;


import com.example.health_guardian_server.configurations.BrevoConfig;
import com.example.health_guardian_server.services.NotificationService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationServiceImpl implements NotificationService {

  private final BrevoConfig brevoConfig;
  private final RestTemplate restTemplate;

  public NotificationServiceImpl(BrevoConfig brevoConfig, RestTemplate restTemplate) {
    this.brevoConfig = brevoConfig;
    this.restTemplate = restTemplate;
  }
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
    payload.put("to", new Object[] { Map.of("email", to) });
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
}
