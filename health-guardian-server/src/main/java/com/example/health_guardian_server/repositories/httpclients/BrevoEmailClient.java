package com.example.health_guardian_server.repositories.httpclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.health_guardian_server.dtos.responses.BrevoMailResponse;
import com.example.health_guardian_server.dtos.responses.SendBrevoEmailRequest;

@FeignClient(name = "brevo-mail-client", url = "${brevo-mail.url}")
public interface BrevoEmailClient {

  @PostMapping(value = "/v3/smtp/email", produces = MediaType.APPLICATION_JSON_VALUE)
  BrevoMailResponse sendEmail(@RequestHeader("api-key") String apiKey, @RequestBody SendBrevoEmailRequest body);

}
