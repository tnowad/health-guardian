package com.example.health_guardian_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class HealthGuardianServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(HealthGuardianServerApplication.class, args);
  }
}
