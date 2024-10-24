package com.example.health_guardian_server.components;

import com.example.health_guardian_server.exceptions.AppException;
import com.example.health_guardian_server.exceptions.errorcodes.AppErrorCode;
import com.example.health_guardian_server.services.AuthService;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.util.Objects;
import static com.example.health_guardian_server.utils.Constants.*;
import static org.springframework.http.HttpStatus.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomJwtDecoder implements JwtDecoder {

  @Value("${jwt.accessSignerKey}")
  private String ACCESS_SIGNER_KEY;

  private final AuthService authService;

  private NimbusJwtDecoder nimbusJwtDecoder = null;

  @Override
  public Jwt decode(String token) {
    try {
      if (!authService.introspect(token))
        throw new AppException(AppErrorCode.INVALID_TOKEN, UNAUTHORIZED, "Introspection failed");

    } catch (ParseException | JOSEException e) {
      throw new JwtException(e.getMessage());
    }

    if (Objects.isNull(nimbusJwtDecoder)) {
      SecretKeySpec secretKeySpec = new SecretKeySpec(
          ACCESS_SIGNER_KEY.getBytes(),
          ACCESS_TOKEN_SIGNATURE_ALGORITHM.getName());

      nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(secretKeySpec)
          .macAlgorithm(MacAlgorithm.from(ACCESS_TOKEN_SIGNATURE_ALGORITHM.getName()))
          .build();
    }
    return nimbusJwtDecoder.decode(token);
  }

}
