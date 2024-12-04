package com.example.health_guardian_server.controllers;

import static com.example.health_guardian_server.utils.Constants.*;

import com.example.health_guardian_server.dtos.enums.VerificationType;
import com.example.health_guardian_server.services.MailService;
import jakarta.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AsyncEmailController {

  MailService mailService;

  @KafkaListener(topics = KAFKA_TOPIC_SEND_MAIL, groupId = "${spring.kafka.mail-consumer.group-id}", errorHandler = "kafkaListenerErrorHandler")
  public void listenNotificationDelivery(String message) {
    String type = message.split(":")[0];
    String email = message.split(":")[1];
    String code = message.split(":")[3];

    log.info("Message received: {}", message);

    try {
      switch (VerificationType.valueOf(type)) {
        case VERIFY_EMAIL_BY_CODE -> mailService.sendMailToVerifyWithCode(email, code);

        case RESET_PASSWORD -> mailService.sendMailToResetPassword(email, code);
      }
    } catch (MessagingException | UnsupportedEncodingException e) {
      throw new RuntimeException("SEND MAIL ERROR");
    }
  }
}
