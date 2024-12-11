package com.example.health_guardian_server.services.impl;

import static com.example.health_guardian_server.dtos.enums.VerificationType.*;
import static com.example.health_guardian_server.utils.Constants.*;
import static java.time.temporal.ChronoUnit.MINUTES;

import com.example.health_guardian_server.dtos.enums.VerificationType;
import com.example.health_guardian_server.dtos.requests.auth.RefreshTokenRequest;
import com.example.health_guardian_server.dtos.requests.auth.SignInRequest;
import com.example.health_guardian_server.dtos.requests.auth.SignUpRequest;
import com.example.health_guardian_server.dtos.responses.auth.RefreshTokenResponse;
import com.example.health_guardian_server.dtos.responses.auth.SignInResponse;
import com.example.health_guardian_server.dtos.responses.auth.SignUpResponse;
import com.example.health_guardian_server.dtos.responses.user.GetCurrentUserPermissionsResponse;
import com.example.health_guardian_server.entities.LocalProvider;
import com.example.health_guardian_server.entities.User;
import com.example.health_guardian_server.entities.Verification;
import com.example.health_guardian_server.repositories.VerificationRepository;
import com.example.health_guardian_server.services.AuthService;
import com.example.health_guardian_server.services.BaseRedisService;
import com.example.health_guardian_server.services.LocalProviderService;
import com.example.health_guardian_server.services.TokenService;
import com.example.health_guardian_server.services.UserService;
import jakarta.transaction.Transactional;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthServiceImpl implements AuthService {

  LocalProviderService localProviderService;
  TokenService tokenService;
  UserService userService;
  BaseRedisService<String, String, Object> baseRedisService;
  VerificationRepository verificationRepository;
  KafkaTemplate<String, String> kafkaTemplate;
  int VERIFICATION_VALID_DURATION = 15;
  PasswordEncoder passwordEncoder;

  @Override
  public SignInResponse signIn(SignInRequest request) {
    var localProvider = localProviderService.getLocalProviderByEmail(request.getEmail());
    if (localProvider == null) {
      throw new RuntimeException("User not found");
    }
    if (!localProvider.isVerified()) {
      throw new RuntimeException("User not verified");
    }
    if (!localProviderService.verifyLocalProviderPassword(
        request.getEmail(), request.getPassword())) {
      throw new RuntimeException("Invalid password");
    }

    var tokens = tokenService.generateTokens(localProvider.getUser().getId());

    return SignInResponse.builder().tokens(tokens).message("Sign in successfully").build();
  }

  @Override
  public RefreshTokenResponse refresh(RefreshTokenRequest request) {
    var tokens = tokenService.refreshTokens(request.getRefreshToken());
    return RefreshTokenResponse.builder()
        .tokens(tokens)
        .message("Refresh token successfully")
        .build();
  }

  @Override
  public GetCurrentUserPermissionsResponse getCurrentUserPermissions(String accessToken) {
    Set<String> permissionNames = new HashSet<>();
    if (accessToken == null) {
    } else {
      permissionNames.add(PermissionName.VIEW_MAIN_DASHBOARD.toString());
    }
    return GetCurrentUserPermissionsResponse.builder()
        .items(permissionNames)
        .message("Get current user permissions successfully")
        .build();
  }

  @Override
  @Transactional
  public SignUpResponse signUp(SignUpRequest request) {
    log.info("Sign-up attempt for email: {}", request.getEmail());
    if (localProviderService.getLocalProviderByEmail(request.getEmail()) != null
        || userService.getOptionalUserById(request.getEmail()).isPresent()) {
      log.warn("Sign-up failed: Email already in use: {}", request.getEmail());
      throw new RuntimeException("Email already in use");
    }

    if (!request.getPassword().equals(request.getConfirmPassword())) {
      log.warn("Sign-up failed: Passwords do not match: {}", request.getEmail());
      throw new RuntimeException("Passwords do not match");
    }

    log.debug("Creating user record for email: {}", request.getEmail());
    var user = userService.createUser(
        User.builder()
            .email(request.getEmail())
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .gender(request.getGender())
            .dob(request.getDateOfBirth())
            .build());

    log.debug("Creating local provider for email: {}", request.getEmail());
    localProviderService.createLocalProvider(
        LocalProvider.builder()
            .email(request.getEmail())
            .passwordHash(passwordEncoder.encode(request.getPassword()))
            .user(user)
            .isVerified(false)
            .build());
    sendEmail(request.getEmail(), VERIFY_EMAIL_BY_CODE);
    log.info("Sign-up successful for email: {}", request.getEmail());
    return SignUpResponse.builder()
        .message("Sign up successfully\nPlease verify code in your mail. Thank you!")
        .build();
  }

  @Override
  public void verifyEmail(LocalProvider localProvider, String code, String token) {
    Verification verification = (code != null)
        ? verificationRepository
            .findByCode(code)
            .orElseThrow(() -> new RuntimeException("Wrong code"))
        : verificationRepository
            .findById(token)
            .orElseThrow(() -> new RuntimeException("Invalid verification"));

    if (verification.getExpiryTime().before(new Date()))
      throw new RuntimeException("Code invalid");
    localProvider.setVerified(true);
    localProviderService.saveLocalProvider(localProvider);
    verificationRepository.delete(verification);
  }

  @Override
  public void sendEmailVerification(String email, VerificationType verificationType) {
    LocalProvider localProvider = localProviderService.getLocalProviderByEmail(email);

    List<Verification> verifications = verificationRepository.findByLocalProviderAndVerificationType(
        localProvider, verificationType);

    if (verificationType.equals(VERIFY_EMAIL_BY_CODE)
        || verificationType.equals(VERIFY_EMAIL_BY_TOKEN)) {
      if (localProvider.isVerified())
        throw new RuntimeException("User already verified");
      else {
        if (!verifications.isEmpty())
          verificationRepository.deleteAll(verifications);
        sendEmail(email, verificationType);
      }
    } else {
      if (verifications.isEmpty())
        throw new RuntimeException("Can't send mail");
      else {
        verificationRepository.deleteAll(verifications);
        sendEmail(email, verificationType);
      }
    }
  }

  @Transactional
  protected void sendEmail(String email, VerificationType verificationType) {
    LocalProvider localProvider = localProviderService.getLocalProviderByEmail(email);

    Verification verification = verificationRepository.save(
        Verification.builder()
            .code(generateVerificationCode(6))
            .expiryTime(Date.from(Instant.now().plus(VERIFICATION_VALID_DURATION, MINUTES)))
            .verificationType(verificationType)
            .localProvider(localProvider)
            .build());

    kafkaTemplate.send(
        KAFKA_TOPIC_SEND_MAIL, verificationType + ":" + email + ":" + verification.getCode());
  }

  public static String generateVerificationCode(int length) {
    StringBuilder code = new StringBuilder();
    SecureRandom random = new SecureRandom();

    for (int i = 0; i < length; i++) {
      int randomIndex = random.nextInt(CHARACTERS.length());
      code.append(CHARACTERS.charAt(randomIndex));
    }

    return code.toString();
  }

  @Override
  public void signOut(String accessToken, String refreshToken) throws Exception {
    // try {
    // SignedJWT signAccessToken = verifyToken(accessToken, false);
    // byte AccessTokenExpiryTime =
    // signAccessToken.getJWTClaimsSet().getExpirationTime();
    //
    // if (AccessTokenExpiryTime.after(new Date())) {
    // baseRedisService.set(signAccessToken.getJWTClaimsSet().getJWTID(),
    // "revoked");
    // baseRedisService.setTimeToLive(
    // signAccessToken.getJWTClaimsSet().getJWTID(),
    // AccessTokenExpiryTime.getTime() - System.currentTimeMillis());
    // }
    //
    // SignedJWT signRefreshToken = verifyToken(refreshToken, true);
    // Date RefreshTokenExpiryTime =
    // signRefreshToken.getJWTClaimsSet().getExpirationTime();
    //
    // if (RefreshTokenExpiryTime.after(new Date())) {
    // baseRedisService.set(signRefreshToken.getJWTClaimsSet().getJWTID(),
    // "revoked");
    // baseRedisService.setTimeToLive(
    // signRefreshToken.getJWTClaimsSet().getJWTID(),
    // RefreshTokenExpiryTime.getTime() - System.currentTimeMillis());
    // }
    //
    // } catch (Exception exception) {
    // log.error("Cannot sign out", exception);
    // }
    throw new UnsupportedOperationException("Unimplemented method 'sendEmailForgotPassword'");
  }

  @Override
  public void sendEmailForgotPassword(String email) {
    throw new UnsupportedOperationException("Unimplemented method 'sendEmailForgotPassword'");
  }

  @Override
  public void resetPassword(String token, String password, String confirmationPassword) {
    throw new UnsupportedOperationException("Unimplemented method 'resetPassword'");
  }

  @Override
  public String forgotPassword(LocalProvider localProvider, String code) {
    throw new UnsupportedOperationException("Unimplemented method 'forgotPassword'");
  }
}
