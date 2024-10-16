package com.example.health_guardian_server.services.impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.health_guardian_server.dtos.others.MailActor;
import com.example.health_guardian_server.dtos.others.SendBrevoEmailDetails;
import com.example.health_guardian_server.dtos.responses.SendBrevoEmailRequest;
import com.example.health_guardian_server.exceptions.AuthException;
import com.example.health_guardian_server.exceptions.errorcodes.AuthenticationErrorCode;
import com.example.health_guardian_server.repositories.httpclients.BrevoEmailClient;
import com.example.health_guardian_server.services.EmailService;

import feign.FeignException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class EmailServiceImpl implements EmailService {

  BrevoEmailClient brevoEmailClient;

  @Value("${brevo-mail.server-url}")
  @NonFinal
  String baseUrl;

  @Value("${brevo-mail.from-mail}")
  @NonFinal
  String fromMail;

  @Value("${brevo-mail.api-key}")
  @NonFinal
  String apiKey;

  @Override
  public String sendEmail(SendBrevoEmailDetails details, String token, String code) {
    log.info("baseUrl: {}", baseUrl);
    String htmlContent = switch (details.getType()) {
      case VERIFY_EMAIL_BY_CODE ->
        "<html><head></head><body><p>Hello, " + details.getTo().getFirst().email()
            + "</p>Your verify code is <b>" + code + "</b></p></body></html>";

      case VERIFY_EMAIL_BY_TOKEN -> "<html><head></head><body><p>Hello, " + details.getTo().getFirst().email()
          + "</p>Click the link below to verify your account<br>"
          + baseUrl + "/auth/verify-email-by-token?token=" + token
          + ".</p></body></html>";

      case RESET_PASSWORD -> "<html><head></head><body><p>Hello, " + details.getTo().getFirst().email()
          + "</p>Your code to reset password is <b>" + code + "</b></p></body></html>";
    };

    SendBrevoEmailRequest sendBrevoEmailRequest = SendBrevoEmailRequest.builder()
        .sender(new MailActor("Health guardian", fromMail))
        .to(details.getTo())
        .subject(details.getSubject())
        .htmlContent(htmlContent)
        .build();

    try {
      return brevoEmailClient.sendEmail(apiKey, sendBrevoEmailRequest).messageId();

    } catch (FeignException e) {
      log.error("Cannot send email", e);
      throw new AuthException(AuthenticationErrorCode.CANNOT_SEND_EMAIL, HttpStatus.UNPROCESSABLE_ENTITY);
    }
  }

}
