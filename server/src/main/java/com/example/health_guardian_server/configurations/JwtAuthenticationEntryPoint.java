package com.example.health_guardian_server.configurations;

// import com.example.health_guardian_server.dtos.responses.CommonResponse;
// import
// com.example.health_guardian_server.exceptions.errorcodes.AuthenticationErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException {
    // AuthenticationErrorCode authenticationErrorCode =
    // AuthenticationErrorCode.TOKEN_MISSING;
    //
    // response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    // response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    //
    // CommonResponse<?> commonResponse = CommonResponse.builder()
    // .errorCode(authenticationErrorCode.getCode())
    // .message(authenticationErrorCode.getMessage())
    // .build();
    //
    // ObjectMapper objectMapper = new ObjectMapper();
    //
    // response.getWriter().write(objectMapper.writeValueAsString(commonResponse));
    response.flushBuffer();
  }
}
