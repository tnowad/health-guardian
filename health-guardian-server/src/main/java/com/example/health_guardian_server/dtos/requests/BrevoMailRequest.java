package com.example.health_guardian_server.dtos.requests;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

import com.example.health_guardian_server.dtos.others.MailActor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BrevoMailRequest {

  MailActor sender;

  List<MailActor> to;

  String subject;

  String htmlContent;

  // Integer templateId;
  //
  // Map<String, String> params;
  //
  // @Builder.Default
  // MailHeaders headers = new MailHeaders(DEFAULT_MAIL_HEADERS_MAILIN_CUSTOM,
  // DEFAULT_MAIL_HEADERS_CHARSET);

}
