package com.example.health_guardian_server.controllers;

import com.example.health_guardian_server.dtos.requests.RefreshTokenRequest;
import com.example.health_guardian_server.dtos.requests.SignInRequest;
import com.example.health_guardian_server.dtos.responses.GetCurrentUserPermissionsResponse;
import com.example.health_guardian_server.dtos.responses.RefreshTokenResponse;
import com.example.health_guardian_server.dtos.responses.SignInResponse;
import com.example.health_guardian_server.services.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
  public ResponseEntity<RefreshTokenResponse> refresh(@RequestBody RefreshTokenRequest request) {
    var response = authService.refresh(request);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/current-user/permissions")
  public ResponseEntity<GetCurrentUserPermissionsResponse> getCurrentUserPermissions(
      @RequestHeader(value = "Authorization", required = false) String accessToken) {
    System.out.println(accessToken);

    String token = (accessToken != null) ? accessToken.replace("Bearer ", "") : null;
    GetCurrentUserPermissionsResponse response = authService.getCurrentUserPermissions(token);

    return ResponseEntity.ok(response);
  }
}
