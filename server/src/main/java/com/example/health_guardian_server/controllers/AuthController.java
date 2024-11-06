package com.example.health_guardian_server.controllers;

import com.example.health_guardian_server.dtos.requests.SignInRequest;
import com.example.health_guardian_server.dtos.responses.SignInResponse;
import com.example.health_guardian_server.services.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthController {
  AuthService authService;

  @PostMapping("/sign-in")
  public ResponseEntity<SignInResponse> signIn(@RequestBody SignInRequest request) {
    System.out.println("Sign in successfully");
    var response = authService.signIn(request);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/sign-up")
  public ResponseEntity<?> signUp() {
    return ResponseEntity.ok().build();
  }

  @PostMapping("/sign-out")
  public ResponseEntity<?> signOut() {
    return ResponseEntity.ok().build();
  }

  @PostMapping("/refresh")
  public ResponseEntity<?> refresh() {
    return ResponseEntity.ok().build();
  }
}
