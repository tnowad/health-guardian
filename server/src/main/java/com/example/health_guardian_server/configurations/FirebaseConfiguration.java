package com.example.health_guardian_server.configurations;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirebaseConfiguration {

  @PostConstruct
  public void initializeFirebase() throws IOException {
    FileInputStream serviceAccount =
        new FileInputStream("src/main/resources/firebase-adminsdk.json");

    FirebaseOptions options =
        FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build();

    if (FirebaseApp.getApps().isEmpty()) {
      FirebaseApp.initializeApp(options);
    }
  }
}
