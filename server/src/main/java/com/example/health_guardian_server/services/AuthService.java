package com.example.health_guardian_server.services;

import com.example.health_guardian_server.dtos.enums.VerificationType;
import com.example.health_guardian_server.dtos.requests.RefreshTokenRequest;
import com.example.health_guardian_server.dtos.requests.SignInRequest;
import com.example.health_guardian_server.dtos.requests.SignUpRequest;
import com.example.health_guardian_server.dtos.responses.GetCurrentUserPermissionsResponse;
import com.example.health_guardian_server.dtos.responses.RefreshTokenResponse;
import com.example.health_guardian_server.dtos.responses.SignInResponse;
import com.example.health_guardian_server.dtos.responses.SignUpResponse;
import com.example.health_guardian_server.entities.LocalProvider;

public interface AuthService {
  SignInResponse signIn(SignInRequest request);

  RefreshTokenResponse refresh(RefreshTokenRequest request);

  GetCurrentUserPermissionsResponse getCurrentUserPermissions(String accessToken);

  SignUpResponse signUp(SignUpRequest request);

  void verifyEmail(LocalProvider LocalProvider, String code, String token);

  void sendEmailVerification(String email, VerificationType verificationType);

  void signOut(String accessToken, String refreshToken) throws Exception;

  void sendEmailForgotPassword(String email);

  void resetPassword(String token, String password, String confirmationPassword);

  String forgotPassword(LocalProvider localProvider, String code);
}
