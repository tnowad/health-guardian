package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.services.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class MailServiceImpl implements MailService {

  JavaMailSender mailSender;

  SpringTemplateEngine templateEngine;

  @Value("${spring.mail.from}")
  @NonFinal
  String emailFrom;

  @Value("${server.port}")
  @NonFinal
  String port;

  @Override
  public void sendMailToVerifyWithToken(String to, String token)
      throws MessagingException, UnsupportedEncodingException {
    log.info("Preparing to send email to verify with token to {}", to);
    sendMail(
        to,
        "subject_verify_email",
        "content_verify_email_with_token",
        "sub_content_verify_email",
        "footer_verify_email",
        String.format(
            """
                http://localhost:%s/auth/verify-email-by-token?token=%s""",
            port, token));
  }

  @Override
  public void sendMailToVerifyWithCode(String to, String code)
      throws MessagingException, UnsupportedEncodingException {
    log.info("Preparing to send email to verify with code to {}", to);
    sendMail(
        to,
        "subject_verify_email",
        "content_verify_email_with_code",
        "sub_content_verify_email",
        "footer_verify_email",
        code);
  }

  @Override
  public void sendMailToResetPassword(String to, String code)
      throws MessagingException, UnsupportedEncodingException {
    log.info("Preparing to send email to reset password to {}", to);
    sendMail(
        to,
        "subject_reset_password",
        "content_reset_password",
        "sub_reset_password",
        "footer_reset_password",
        code);
  }

  private void sendMail(
      String toMail,
      String subjectKey,
      String contentKey,
      String subContentKey,
      String footerKey,
      String secret)
      throws MessagingException, UnsupportedEncodingException {
    log.debug("Sending mail to {} with secret {}", toMail, secret);

    log.info("Sending confirming link to user, email={}", toMail);

    String subject = subjectKey;
    String[] contents = new String[] { contentKey, subContentKey };

    Map<String, Object> properties = new HashMap<>();
    properties.put("secret", secret);
    properties.put("contents", contents);
    properties.put("subject", subject);
    properties.put("footer", footerKey);

    Context context = new Context();
    context.setVariables(properties);

    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(
        message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
    helper.setFrom(emailFrom, "Health guardian");
    helper.setTo(toMail);
    helper.setSubject(subject);

    log.debug("Subject set to: {}", subject);

    String html = templateEngine.process("common-template.html", context);
    helper.setText(html, true);

    log.debug("HTML email content generated successfully.");

    mailSender.send(message);

    log.info("Confirming link has been sent to user, email={}, code={}", toMail, secret);
  }
}
