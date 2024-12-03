package com.example.health_guardian_server.controllers;

import static org.springframework.http.HttpStatus.*;

import com.example.health_guardian_server.dtos.requests.RefreshTokenRequest;
import com.example.health_guardian_server.dtos.requests.SendEmailVerificationRequest;
import com.example.health_guardian_server.dtos.requests.SignInRequest;
import com.example.health_guardian_server.dtos.requests.SignUpRequest;
import com.example.health_guardian_server.dtos.requests.VerifyEmailByCodeRequest;
import com.example.health_guardian_server.dtos.responses.GetCurrentUserPermissionsResponse;
import com.example.health_guardian_server.dtos.responses.RefreshTokenResponse;
import com.example.health_guardian_server.dtos.responses.SignInResponse;
import com.example.health_guardian_server.dtos.responses.SignUpResponse;
import com.example.health_guardian_server.entities.LocalProvider;
import com.example.health_guardian_server.services.AuthService;
import com.example.health_guardian_server.services.LocalProviderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthController {
  AuthService authService;
  LocalProviderService localProviderService;

  @PostMapping("/sign-in")
  public ResponseEntity<SignInResponse> signIn(@RequestBody SignInRequest request) {
    var response = authService.signIn(request);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/sign-up")
  public ResponseEntity<SignUpResponse> signUp(@RequestBody SignUpRequest request) {
    var response = authService.signUp(request);
    return ResponseEntity.ok(response);
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

  @Operation(summary = "Send email verification", description = "Send email verification")
  @PostMapping("/send-email-verification")
  @ResponseStatus(OK)
  ResponseEntity<String> sendEmailVerification(
      @RequestBody @Valid SendEmailVerificationRequest request) {
    authService.sendEmailVerification(request.email(), request.type());

    return ResponseEntity.status(OK).body("resend_verification_email_success");
  }

  @Operation(summary = "Verify email by code", description = "Verify email by code")
  @PostMapping("/verify-email-by-code")
  @ResponseStatus(OK)
  ResponseEntity<String> verifyEmail(@RequestBody @Valid VerifyEmailByCodeRequest request) {
    LocalProvider localProvider = localProviderService.getLocalProviderByEmail(request.email());
    if (localProvider == null) {
      return ResponseEntity.status(NOT_FOUND).body("email_not_found");
    }
    authService.verifyEmail(localProvider, request.code(), null);

    return ResponseEntity.status(OK).body("verify_email_success");
  }

  @Operation(summary = "Verify email by token", description = "Verify email by token")
  @GetMapping("/verify-email-by-token")
  @ResponseStatus(OK)
  ResponseEntity<String> verifyEmail(@RequestParam(name = "token") String token) {
    authService.verifyEmail(null, null, token);

    return ResponseEntity.status(OK).body("verify_email_success");
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
