package com.example.health_guardian_server.services;

public interface NotificationService {

  void sendEmail(String to, String subject, String body);
}
