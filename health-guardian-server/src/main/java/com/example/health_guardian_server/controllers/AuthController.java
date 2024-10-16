package com.example.health_guardian_server.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.health_guardian_server.dtos.others.Tokens;
import com.example.health_guardian_server.dtos.requests.ForgotPasswordRequest;
import com.example.health_guardian_server.dtos.requests.IntrospectRequest;
import com.example.health_guardian_server.dtos.requests.RefreshRequest;
import com.example.health_guardian_server.dtos.requests.ResetPasswordRequest;
import com.example.health_guardian_server.dtos.requests.SendEmailForgotPasswordRequest;
import com.example.health_guardian_server.dtos.requests.SendEmailVerificationRequest;
import com.example.health_guardian_server.dtos.requests.SignInRequest;
import com.example.health_guardian_server.dtos.requests.SignOutRequest;
import com.example.health_guardian_server.dtos.requests.SignUpRequest;
import com.example.health_guardian_server.dtos.requests.VerifyEmailByCodeRequest;
import com.example.health_guardian_server.dtos.responses.CommonResponse;
import com.example.health_guardian_server.dtos.responses.ForgotPasswordResponse;
import com.example.health_guardian_server.dtos.responses.IntrospectResponse;
import com.example.health_guardian_server.dtos.responses.RefreshResponse;
import com.example.health_guardian_server.dtos.responses.ResetPasswordResponse;
import com.example.health_guardian_server.dtos.responses.SendEmailForgotPasswordResponse;
import com.example.health_guardian_server.dtos.responses.SendEmailVerificationResponse;
import com.example.health_guardian_server.dtos.responses.SignInResponse;
import com.example.health_guardian_server.dtos.responses.SignUpResponse;
import com.example.health_guardian_server.dtos.responses.VerifyEmailResponse;
import com.example.health_guardian_server.entities.Account;
import com.example.health_guardian_server.exceptions.AppException;
import com.example.health_guardian_server.exceptions.AuthException;
import com.example.health_guardian_server.exceptions.errorcodes.AppErrorCode;
import com.example.health_guardian_server.exceptions.errorcodes.AuthenticationErrorCode;
import com.example.health_guardian_server.mappers.AccountMapper;
import com.example.health_guardian_server.services.AccountService;
import com.example.health_guardian_server.services.AuthService;
import com.nimbusds.jose.JOSEException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.http.HttpStatus.*;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "Authentication Apis")
public class AuthController {

  AuthService authService;
  AccountService accountservice;
  AccountMapper accountMapper;

  @Operation(summary = "Sign in", description = "Authenticate user and return token")
  @PostMapping("/sign-in")
  ResponseEntity<SignInResponse> signIn(@Valid @RequestBody SignInRequest request) {
    Account account = authService.signIn(request.email(), request.password());
    String accessToken = authService.generateToken(account, false);
    String refreshToken = authService.generateToken(account, true);
    return ResponseEntity.status(OK).body(SignInResponse.builder()
        .tokens(new Tokens(accessToken, refreshToken)).accountInfoResponse(accountMapper.toAccountInfoResponse(account))
        .build());
  }

  @Operation(summary = "Sign up", description = "Create new account")
  @PostMapping("/sign-up")
  @ResponseStatus(CREATED)
  ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest request) {
    Account account = accountMapper.toAccount(request);
    authService.signUp(account, request.getPasswordConfirmation(), request.getAcceptTerms());
    return ResponseEntity.status(CREATED).body(new SignUpResponse("Account created successfully"));
  }

  @Operation(summary = "Send email verification", description = "Send email verification")
  @PostMapping("/send-email-verification")
  @ResponseStatus(OK)
  ResponseEntity<SendEmailVerificationResponse> sendEmailVerification(
      @RequestBody @Valid SendEmailVerificationRequest request) {
    authService.sendEmailVerification(request.email(), request.type());

    return ResponseEntity.status(OK).body(
        new SendEmailVerificationResponse("resend_verification_email_success"));
  }

  @Operation(summary = "Verify email by code", description = "Verify email by code")
  @PostMapping("/verify-email-by-code")
  @ResponseStatus(OK)
  ResponseEntity<VerifyEmailResponse> verifyEmail(@RequestBody @Valid VerifyEmailByCodeRequest request) {
    Account account = accountservice.findByEmail(request.email());

    authService.verifyEmail(account, request.code(), null);

    return ResponseEntity.status(OK).body(
        new VerifyEmailResponse("verify_email_success"));
  }

  @Operation(summary = "Verify email by token", description = "Verify email by token")
  @GetMapping("/verify-email-by-token")
  @ResponseStatus(OK)
  ResponseEntity<VerifyEmailResponse> verifyEmail(@RequestParam(name = "token") String token) {
    authService.verifyEmail(null, null, token);

    return ResponseEntity.status(OK).body(
        new VerifyEmailResponse("verify_email_success"));
  }

  @Operation(summary = "Refresh", description = "Refresh token")
  @PostMapping("/refresh")
  @ResponseStatus(OK)
  ResponseEntity<RefreshResponse> refresh(@RequestBody @Valid RefreshRequest request,
      HttpServletRequest httpServletRequest) {
    Account account;
    try {
      account = authService.refresh(request.refreshToken(), httpServletRequest);
    } catch (ParseException | JOSEException e) {
      throw new AppException(AppErrorCode.INVALID_SIGNATURE, UNPROCESSABLE_ENTITY, "INVALID_SIGNATURE");
    }

    String newAccessToken = authService.generateToken(account, false);

    return ResponseEntity.status(OK).body(new RefreshResponse("Refresh token success", newAccessToken));
  }

  @Operation(summary = "Sign out", description = "Sign out user")
  @PostMapping("/sign-out")
  @ResponseStatus(OK)
  void signOut(@RequestBody @Valid SignOutRequest request) {
    try {
      authService.signOut(request.accessToken(), request.refreshToken());

    } catch (ParseException | JOSEException e) {
      throw new AppException(AppErrorCode.INVALID_SIGNATURE, UNPROCESSABLE_ENTITY, "INVALID_SIGNATURE");
    }
  }

  @Operation(summary = "Send email forgot password", description = "Send email forgot password")
  @PostMapping("/send-forgot-password")
  @ResponseStatus(OK)
  ResponseEntity<SendEmailForgotPasswordResponse> sendEmailForgotPassword(
      @RequestBody @Valid SendEmailForgotPasswordRequest request) {
    authService.sendEmailForgotPassword(request.email());

    return ResponseEntity.status(OK).body(new SendEmailForgotPasswordResponse("send_forgot_password_email_success",
        Date.from(Instant.now().plus(1, ChronoUnit.MINUTES))));
  }

  @Operation(summary = "Verify forgot password code", description = "Verify forgot password code")
  @PostMapping("/forgot-password")
  @ResponseStatus(OK)
  ResponseEntity<ForgotPasswordResponse> forgotPassword(@RequestBody @Valid ForgotPasswordRequest request) {
    Account account = accountservice.findByEmail(request.email());
    String forgotPasswordToken = authService.forgotPassword(account, request.code());

    return ResponseEntity.status(OK).body(new ForgotPasswordResponse("verify_forgot_password_code_success",
        forgotPasswordToken));
  }

  @Operation(summary = "Reset password", description = "Reset password")
  @PostMapping("/reset-password")
  @ResponseStatus(OK)
  ResponseEntity<ResetPasswordResponse> resetPassword(@RequestBody @Valid ResetPasswordRequest request) {
    authService.resetPassword(request.token(), request.password(), request.passwordConfirmation());

    return ResponseEntity.status(OK).body(
        new ResetPasswordResponse("reset_password_success"));
  }

  @Operation(summary = "Introspect", description = "Introspect provided token")
  @PostMapping("/introspect")
  @ResponseStatus(OK)
  ResponseEntity<IntrospectResponse> introspect(@RequestBody @Valid IntrospectRequest request)
      throws ParseException, JOSEException {
    boolean isValid = authService.introspect(request.token());

    return ResponseEntity.status(OK).body(new IntrospectResponse(isValid));
  }

}
