package com.example.health_guardian_server.services;

import java.text.ParseException;
import com.example.health_guardian_server.entities.Account;
import com.example.health_guardian_server.enums.VerificationType;
import com.nimbusds.jose.JOSEException;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {

  boolean introspect(String token) throws JOSEException, ParseException;

  void signUp(Account account, String confirmationPassword, boolean acceptTerms);

  void sendEmailVerification(String email, VerificationType verificationType);

  void verifyEmail(Account account, String code, String token);

  Account signIn(String email, String password);

  String generateToken(Account account, boolean isRefresh);

  Account refresh(String refreshToken, HttpServletRequest servletRequest) throws ParseException, JOSEException;

  void sendEmailForgotPassword(String email);

  String forgotPassword(Account account, String code);

  void resetPassword(String token, String password, String confirmationPassword);

  void signOut(String accessToken, String refreshToken) throws ParseException, JOSEException;

}
