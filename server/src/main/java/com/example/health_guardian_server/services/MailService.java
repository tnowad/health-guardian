package com.example.health_guardian_server.services;

import jakarta.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface MailService {
  void sendMailToVerifyWithToken(String to, String token)
      throws MessagingException, UnsupportedEncodingException;

  void sendMailToVerifyWithCode(String to, String code)
      throws MessagingException, UnsupportedEncodingException;

  void sendMailToResetPassword(String to, String code)
      throws MessagingException, UnsupportedEncodingException;
}
