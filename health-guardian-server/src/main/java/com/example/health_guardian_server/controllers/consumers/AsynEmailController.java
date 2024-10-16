package com.example.health_guardian_server.controllers.consumers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import com.example.health_guardian_server.services.EmailService;
import com.example.health_guardian_server.dtos.responses.*;
import com.example.health_guardian_server.dtos.others.*;
import static com.example.health_guardian_server.utils.Constants.KAFKA_TOPIC_SEND_MAIL;
import java.util.List;
import com.example.health_guardian_server.enums.VerificationType;

@RestController
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AsynEmailController {

  EmailService emailService;

  @KafkaListener(topics = KAFKA_TOPIC_SEND_MAIL, groupId = "${spring.kafka.mail-consumer.group-id}")
  public void listenNotificationDelivery(String message) {
    String type = message.split(":")[0];
    String email = message.split(":")[1];
    String token = message.split(":")[2];
    String code = message.split(":")[3];

    log.info("Message received: {}", message);

    String messageId = switch (VerificationType.valueOf(type)) {
      case VERIFY_EMAIL_BY_CODE,
          VERIFY_EMAIL_BY_TOKEN ->
        emailService.sendEmail(SendBrevoEmailDetails.builder()
            .to(List.of(new MailActor("User", email)))
            .subject("Welcome to Health guardian.\n Manage health for everyone")
            .type(VerificationType.valueOf(type))
            .build(), token, code);

      case RESET_PASSWORD ->
        emailService.sendEmail(SendBrevoEmailDetails.builder()
            .to(List.of(new MailActor("User", email)))
            .subject("Reset Password")
            .type(VerificationType.VERIFY_EMAIL_BY_CODE)
            .build(), token, code);
    };
    log.info("Email sent: {}", new BrevoMailResponse(messageId));
  }

}
