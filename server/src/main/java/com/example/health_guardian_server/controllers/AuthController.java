package com.example.health_guardian_server.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.health_guardian_server.dtos.requests.SignInRequest;
import com.example.health_guardian_server.dtos.responses.SignInResponse;
import com.example.health_guardian_server.services.AuthService;
import lombok.RequiredArgsConstructor;

@RestController("/auth")
@RequiredArgsConstructor
public class AuthController {
  private AuthService authService;

  @PostMapping("/sign-in")
  public ResponseEntity<SignInResponse> signIn(
      @RequestBody SignInRequest request) {
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
