package com.example.health_guardian_server.services;

import com.example.health_guardian_server.dtos.others.SendBrevoEmailDetails;

public interface EmailService {
  String sendEmail(SendBrevoEmailDetails details, String token, String code);
}
