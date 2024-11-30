package com.example.health_guardian_server.configurations;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.SessionsSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class AIConfiguration {
  @Value("${spring.dialogflow.project-id}")
  private String projectId;

  @Value("${spring.dialogflow.credentials}")
  private String credentials;

  @Bean
  public SessionsClient sessionsClient() throws IOException {
    // Đọc tệp JSON từ classpath
    Resource resource = new ClassPathResource("health-guardian-439302-4ff9782bd0a5.json"); // Đảm bảo rằng tệp ở trong thư mục resources
    try (InputStream inputStream = resource.getInputStream()) {
      GoogleCredentials credentials = GoogleCredentials.fromStream(inputStream);
      SessionsSettings sessionsSettings = SessionsSettings.newBuilder()
        .setCredentialsProvider(() -> credentials)
        .build();
      return SessionsClient.create(sessionsSettings);
    }
  }
  @Bean
  public String projectId() {
    return projectId;
  }
}
