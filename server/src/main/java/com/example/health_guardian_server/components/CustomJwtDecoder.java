package com.example.health_guardian_server.components;

import static com.example.health_guardian_server.utils.Constants.*;

import java.util.Objects;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomJwtDecoder implements JwtDecoder {

  @Value("${jwt.accessSignerKey}")
  private String ACCESS_SIGNER_KEY;

  private NimbusJwtDecoder nimbusJwtDecoder = null;

  @Override
  public Jwt decode(String token) {
    if (Objects.isNull(nimbusJwtDecoder)) {
      SecretKeySpec secretKeySpec =
          new SecretKeySpec(
              ACCESS_SIGNER_KEY.getBytes(), ACCESS_TOKEN_SIGNATURE_ALGORITHM.getName());

      nimbusJwtDecoder =
          NimbusJwtDecoder.withSecretKey(secretKeySpec)
              .macAlgorithm(MacAlgorithm.from(ACCESS_TOKEN_SIGNATURE_ALGORITHM.getName()))
              .build();
    }
    return nimbusJwtDecoder.decode(token);
  }
}
