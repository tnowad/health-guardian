package com.example.health_guardian_server.services.impl;

import static java.time.temporal.ChronoUnit.MINUTES;
import static java.time.temporal.ChronoUnit.SECONDS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.springframework.http.HttpStatus.*;
import com.example.health_guardian_server.exceptions.AuthException;
import com.example.health_guardian_server.exceptions.errorcodes.AuthenticationErrorCode;
import static com.example.health_guardian_server.exceptions.errorcodes.AuthenticationErrorCode.*;
import static com.example.health_guardian_server.exceptions.errorcodes.AppErrorCode.*;
import static com.example.health_guardian_server.utils.Constants.*;

import java.security.SecureRandom;
import java.text.ParseException;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.health_guardian_server.entities.Account;
import com.example.health_guardian_server.entities.Verification;
import com.example.health_guardian_server.enums.VerificationType;
import com.example.health_guardian_server.exceptions.*;
import com.example.health_guardian_server.repositories.VerificationRepository;
import com.example.health_guardian_server.services.*;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class AuthServiceImpl implements AuthService {
  AccountService accountService;
  VerificationRepository verificationRepository;
  PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
  RedisService<String, String, Object> redisService;

  @NonFinal
  @Value("${ACCESS_SIGNER_KEY}")
  String ACCESS_SIGNER_KEY;

  @NonFinal
  @Value("${REFRESH_SIGNER_KEY}")
  String REFRESH_SIGNER_KEY;

  @NonFinal
  @Value("${jwt.valid-duration}")
  protected long VALID_DURATION;

  KafkaTemplate<String, String> kafkaTemplate;

  @NonFinal
  @Value("${jwt.refreshable-duration}")
  protected long REFRESHABLE_DURATION;

  @Override
  public boolean introspect(String token) throws JOSEException, ParseException {
    boolean isValid = true;
    try {
      verifyToken(token, false);
    } catch (AuthException e) {
      isValid = false;
    }
    return isValid;
  }

  @Override
  public void signUp(Account account, String confirmationPassword, boolean acceptTerms) {

    if (accountService.existsByEmail(account.getEmail()))
      throw new AuthException(AuthenticationErrorCode.EMAIL_ALREADY_IN_USE, CONFLICT);

    if (!account.getPassword().equals(confirmationPassword))
      throw new AuthException(AuthenticationErrorCode.PASSWORD_MIS_MATCH, BAD_REQUEST);

    if (isInvalidEmail(account.getEmail()))
      throw new AuthException(AuthenticationErrorCode.INVALID_EMAIL, BAD_REQUEST);

    if (isWeakPassword(account.getPassword()))
      throw new AuthException(AuthenticationErrorCode.WEAK_PASSWORD, BAD_REQUEST);

    if (!acceptTerms)
      throw new AuthException(AuthenticationErrorCode.TERMS_NOT_ACCEPTED, BAD_REQUEST);

    account.setPassword(passwordEncoder.encode(account.getPassword()));

    try {
      accountService.createaccount(account);
    } catch (DataIntegrityViolationException exception) {
      throw new AuthException(AuthenticationErrorCode.EMAIL_ALREADY_IN_USE, CONFLICT);
    }
  }

  @Override
  public void sendEmailVerification(String email, VerificationType verificationType) {
    Account account = accountService.findByEmail(email);

    List<Verification> verifications = verificationRepository.findByAccountAndVerificationType(account,
        verificationType);
    if (verificationType.equals(VerificationType.VERIFY_EMAIL_BY_CODE)
        || verificationType.equals(VerificationType.VERIFY_EMAIL_BY_TOKEN)) {
      if (account.isActivated())
        throw new AuthException(AuthenticationErrorCode.USER_ALREADY_VERIFIED, BAD_REQUEST);

      else {
        if (!verifications.isEmpty())
          verificationRepository.deleteAll(verifications);

        sendEmail(email, verificationType);
      }

    } else {
      if (verifications.isEmpty())
        throw new AuthException(AuthenticationErrorCode.CANNOT_SEND_EMAIL, BAD_REQUEST);

      else {
        verificationRepository.deleteAll(verifications);
        sendEmail(email, verificationType);
      }
    }
  }

  @Transactional
  protected void sendEmail(String email, VerificationType verificationType) {
    Account account = accountService.findByEmail(email);

    Verification verification = verificationRepository.save(Verification.builder()
        .code(generateVerificationCode(6))
        .expiryTime(Date.from(Instant.now().plus(3, MINUTES)))
        .verificationType(verificationType)
        .account(account)
        .build());

    kafkaTemplate.send(KAFKA_TOPIC_SEND_MAIL,
        verificationType + ":" + email + ":" + verification.getToken() + ":" +
            verification.getCode());

  }

  @Override
  public void verifyEmail(Account account, String code, String token) {
    Verification verification = (code != null)
        ? verificationRepository.findByCode(code)
            .orElseThrow(() -> new AuthException(AuthenticationErrorCode.CODE_INVALID, BAD_REQUEST))

        : verificationRepository.findById(token)
            .orElseThrow(() -> new AuthException(AuthenticationErrorCode.CODE_INVALID, BAD_REQUEST));

    if (verification.getExpiryTime().before(new Date()))
      throw new AuthException(AuthenticationErrorCode.CODE_INVALID, UNPROCESSABLE_ENTITY);

    accountService.activateAccount((account != null) ? account : verification.getAccount());

    verificationRepository.delete(verification);
  }

  @Override
  public Account signIn(String email, String password) {
    Account account = accountService.findByEmail(email);
    if (account == null) {
      throw new AuthException(AuthenticationErrorCode.USER_NOT_FOUND, UNAUTHORIZED);
    }
    if (isPasswordExpired(account)) {
      throw new AuthException(AuthenticationErrorCode.PASSWORD_EXPIRED, UNAUTHORIZED);
    }
    if (passwordEncoder.matches(password, account.getPassword())) {
      return account;
    }
    if (!account.isActivated()) {
      throw new AuthException(AuthenticationErrorCode.USER_NOT_ACTIVATED, UNAUTHORIZED);
    }
    throw new AuthException(AuthenticationErrorCode.WRONG_PASSWORD, UNAUTHORIZED);
  }

  @Override
  public String generateToken(Account account, boolean isRefresh) {

    JWSHeader accessHeader = new JWSHeader(ACCESS_TOKEN_SIGNATURE_ALGORITHM);
    JWSHeader refreshHeader = new JWSHeader(REFRESH_TOKEN_SIGNATURE_ALGORITHM);

    Date expiryTime = (isRefresh)
        ? new Date(Instant.now().plus(REFRESHABLE_DURATION, SECONDS).toEpochMilli())
        : new Date(Instant.now().plus(VALID_DURATION, SECONDS).toEpochMilli());

    String jwtID = UUID.randomUUID().toString();

    JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
        .subject(account.getEmail())
        .issuer("com.health_guardian")
        .issueTime(new Date())
        .expirationTime(expiryTime)
        .jwtID(jwtID)
        .build();

    if (!isRefresh) {
      jwtClaimsSet = new JWTClaimsSet.Builder(jwtClaimsSet)
          .claim("scope", buildScope(account))
          .build();
    }

    Payload payload = new Payload(jwtClaimsSet.toJSONObject());

    JWSObject jwsObject = (isRefresh)
        ? new JWSObject(refreshHeader, payload)
        : new JWSObject(accessHeader, payload);

    try {
      if (isRefresh)
        jwsObject.sign(new MACSigner(REFRESH_SIGNER_KEY.getBytes()));
      else
        jwsObject.sign(new MACSigner(ACCESS_SIGNER_KEY.getBytes()));

      return jwsObject.serialize();

    } catch (JOSEException e) {
      log.error("Cannot create token", e);
      throw new RuntimeException(e);
    }
  }

  private String buildScope(Account account) {
    StringJoiner joiner = new StringJoiner(" ");
    if (!CollectionUtils.isEmpty(account.getRoles())) {
      account.getRoles().forEach(role -> {
        joiner.add("ROLE_" + role.getName());
        if (CollectionUtils.isEmpty(role.getPermissions())) {
          joiner.add(role.getName());
        } else {
          role.getPermissions().forEach(permission -> joiner.add(permission.getName()));
        }
      });
    }
    return joiner.toString();
  }

  @Override
  public Account refresh(String refreshToken, HttpServletRequest servletRequest) throws ParseException, JOSEException {
    return null;
  }

  private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
    JWSVerifier verifier = (isRefresh)
        ? new MACVerifier(REFRESH_SIGNER_KEY.getBytes())
        : new MACVerifier(ACCESS_SIGNER_KEY.getBytes());

    SignedJWT signedJWT = SignedJWT.parse(token);

    Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

    boolean verified = signedJWT.verify(verifier);

    if (isRefresh) {
      if (expiryTime.before(new Date()))
        throw new AuthException(AuthenticationErrorCode.TOKEN_EXPIRED, UNAUTHORIZED);

      if (!verified)
        throw new AuthException(AuthenticationErrorCode.INVALID_SIGNATURE, UNAUTHORIZED);

      SecretKeySpec secretKeySpec = new SecretKeySpec(
          REFRESH_SIGNER_KEY.getBytes(),
          REFRESH_TOKEN_SIGNATURE_ALGORITHM.getName());
      try {
        NimbusJwtDecoder nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(secretKeySpec)
            .macAlgorithm(MacAlgorithm.from(REFRESH_TOKEN_SIGNATURE_ALGORITHM.getName()))
            .build();
        nimbusJwtDecoder.decode(token);

      } catch (JwtException e) {
        throw new AuthException(AuthenticationErrorCode.INVALID_SIGNATURE, UNAUTHORIZED);
      }

    } else {
      if (!verified || expiryTime.before(new Date()))
        throw new AuthException(AuthenticationErrorCode.TOKEN_INVALID, UNAUTHORIZED);
    }

    String value = (String) redisService.get(signedJWT.getJWTClaimsSet().getJWTID());

    if (value != null) {
      if (value.equals("revoked"))
        throw new AuthException(AuthenticationErrorCode.TOKEN_REVOKED, UNAUTHORIZED);

      else
        throw new AuthException(AuthenticationErrorCode.TOKEN_BLACKLISTED, UNAUTHORIZED);
    }

    return signedJWT;
  }

  @Override
  public void sendEmailForgotPassword(String email) {
    throw new UnsupportedOperationException("Unimplemented method 'sendEmailForgotPassword'");
  }

  @Override
  public String forgotPassword(Account account, String code) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'forgotPassword'");
  }

  @Override
  public void resetPassword(String token, String password, String confirmationPassword) {
    Verification verification = verificationRepository.findById(token)
        .orElseThrow(() -> new AuthException(AuthenticationErrorCode.TOKEN_REVOKED, UNPROCESSABLE_ENTITY));

    if (verification.getExpiryTime().before(new Date()))
      throw new AuthException(AuthenticationErrorCode.TOKEN_EXPIRED, UNPROCESSABLE_ENTITY);

    if (!password.equals(confirmationPassword))
      throw new AuthException(AuthenticationErrorCode.PASSWORD_MIS_MATCH, BAD_REQUEST);

    if (isWeakPassword(password))
      throw new AuthException(AuthenticationErrorCode.WEAK_PASSWORD, BAD_REQUEST);

    Account account = verification.getAccount();
    accountService.updatePassword(account, passwordEncoder.encode(password));
    verificationRepository.delete(verification);
  }

  @Override
  public void signOut(String accessToken, String refreshToken) throws ParseException, JOSEException {

    try {
      SignedJWT signAccessToken = verifyToken(accessToken, false);
      Date AccessTokenExpiryTime = signAccessToken.getJWTClaimsSet().getExpirationTime();

      if (AccessTokenExpiryTime.after(new Date())) {
        redisService.set(signAccessToken.getJWTClaimsSet().getJWTID(), "revoked");
        redisService.setTimeToLive(signAccessToken.getJWTClaimsSet().getJWTID(),
            AccessTokenExpiryTime.getTime() - System.currentTimeMillis());
      }

      SignedJWT signRefreshToken = verifyToken(refreshToken, true);
      Date RefreshTokenExpiryTime = signRefreshToken.getJWTClaimsSet().getExpirationTime();

      if (RefreshTokenExpiryTime.after(new Date())) {
        redisService.set(signRefreshToken.getJWTClaimsSet().getJWTID(), "revoked");
        redisService.setTimeToLive(signRefreshToken.getJWTClaimsSet().getJWTID(),
            RefreshTokenExpiryTime.getTime() - System.currentTimeMillis());
      }

    } catch (AuthException exception) {
      log.error("Cannot sign out", exception);
    }
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

  private boolean isPasswordExpired(Account account) {
    return false;
  }

  private boolean isTwoFactorRequired(Account account) {
    return false;
  }

  private boolean isUserDisabled(Account account) {
    return false;
  }

  private boolean isInvalidEmail(String email) {
    return false;
  }

  private boolean isWeakPassword(String password) {
    return false;
  }
}
