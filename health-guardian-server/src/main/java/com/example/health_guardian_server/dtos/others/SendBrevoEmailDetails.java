package com.example.health_guardian_server.dtos.others;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

import com.example.health_guardian_server.enums.VerificationType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SendBrevoEmailDetails {

  List<MailActor> to;

  String subject;

  VerificationType type;

}
