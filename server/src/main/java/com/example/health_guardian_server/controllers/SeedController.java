package com.example.health_guardian_server.controllers;

import com.example.health_guardian_server.services.SeedService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seeds")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SeedController {
  SeedService seedService;

  @PostMapping("/clear")
  public ResponseEntity<?> clear() {
    seedService.clear();
    return ResponseEntity.ok("Cleared");
  }

  @PostMapping("/initial")
  public ResponseEntity<?> initial() {
    seedService.initial();
    return ResponseEntity.ok("Initial");
  }

  @PostMapping("/mock")
  public ResponseEntity<?> mock() {
    seedService.mock();
    return ResponseEntity.ok("Mocked");
  }
}
