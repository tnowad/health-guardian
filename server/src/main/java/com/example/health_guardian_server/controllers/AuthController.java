package com.example.health_guardian_server.controllers;

import static org.springframework.http.HttpStatus.*;

import com.example.health_guardian_server.dtos.enums.VerificationType;
import com.example.health_guardian_server.dtos.requests.auth.ForgotPasswordRequest;
import com.example.health_guardian_server.dtos.requests.auth.RefreshTokenRequest;
import com.example.health_guardian_server.dtos.requests.auth.ResetPasswordRequest;
import com.example.health_guardian_server.dtos.requests.auth.SendEmailForgotPasswordRequest;
import com.example.health_guardian_server.dtos.requests.auth.SendEmailVerificationRequest;
import com.example.health_guardian_server.dtos.requests.auth.SignInRequest;
import com.example.health_guardian_server.dtos.requests.auth.SignOutRequest;
import com.example.health_guardian_server.dtos.requests.auth.SignUpRequest;
import com.example.health_guardian_server.dtos.requests.auth.VerifyEmailByCodeRequest;
import com.example.health_guardian_server.dtos.responses.auth.ForgotPasswordResponse;
import com.example.health_guardian_server.dtos.responses.user.GetCurrentUserPermissionsResponse;
import com.example.health_guardian_server.dtos.responses.auth.RefreshTokenResponse;
import com.example.health_guardian_server.dtos.responses.auth.ResetPasswordResponse;
import com.example.health_guardian_server.dtos.responses.auth.SendEmailForgotPasswordResponse;
import com.example.health_guardian_server.dtos.responses.auth.SignInResponse;
import com.example.health_guardian_server.dtos.responses.auth.SignUpResponse;
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
    authService.sendEmailVerification(request.email(), VerificationType.VERIFY_EMAIL_BY_CODE);

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

  @Operation(summary = "Sign out", description = "Sign out user")
  @PostMapping("/sign-out")
  @ResponseStatus(OK)
  void signOut(@RequestBody @Valid SignOutRequest request) {
    try {
      authService.signOut(request.accessToken(), request.refreshToken());
    } catch (Exception e) {
      throw new RuntimeException("Token invalid");
    }
  }

  @Operation(summary = "Send email forgot password", description = "Send email forgot password")
  @PostMapping("/send-forgot-password")
  @ResponseStatus(OK)
  ResponseEntity<SendEmailForgotPasswordResponse> sendEmailForgotPassword(
      @RequestBody @Valid SendEmailForgotPasswordRequest request) {
    authService.sendEmailForgotPassword(request.email());

    return ResponseEntity.status(OK)
        .body(new SendEmailForgotPasswordResponse("send_forgot_password_email_success", 60));
  }

  @Operation(summary = "Verify forgot password code", description = "Verify forgot password code")
  @PostMapping("/forgot")
  @ResponseStatus(OK)
  ResponseEntity<ForgotPasswordResponse> forgotPassword(
      @RequestBody @Valid ForgotPasswordRequest request) {
    LocalProvider localProvider = localProviderService.getLocalProviderByEmail(request.email());
    String forgotPasswordToken = authService.forgotPassword(localProvider, request.code());

    return ResponseEntity.status(OK)
        .body(
            new ForgotPasswordResponse("verify_forgot_password_code_success", forgotPasswordToken));
  }

  @Operation(summary = "Reset password", description = "Reset password")
  @PostMapping("/reset")
  @ResponseStatus(OK)
  ResponseEntity<ResetPasswordResponse> resetPassword(
      @RequestBody @Valid ResetPasswordRequest request) {
    authService.resetPassword(request.token(), request.password(), request.passwordConfirmation());

    return ResponseEntity.status(OK).body(new ResetPasswordResponse("reset_password_success"));
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
