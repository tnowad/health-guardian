package com.example.health_guardian_server.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BrevoConfig {
  @Value("${brevo-mail.from-mail}")
  private String fromMail;

  @Value("${brevo-mail.url}")
  private String apiUrl;

  @Value("${brevo-mail.api-key}")
  private String apiKey;

  @Value("${brevo-mail.url}")
  private String serverUrl;

  public String getFromMail() {
    return fromMail;
  }

  public String getApiUrl() {
    return apiUrl;
  }

  public String getApiKey() {
    return apiKey;
  }

  public String getServerUrl() {
    return serverUrl;
  }
}
