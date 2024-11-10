package com.example.health_guardian_server.configurations;

import com.example.health_guardian_server.components.CustomJwtDecoder;
import com.example.health_guardian_server.repositories.AccountRepository;
import com.example.health_guardian_server.repositories.LocalProviderRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class SecurityConfiguration {
  CustomJwtDecoder customJwtDecoder;
  AccountRepository accountRepository;
  LocalProviderRepository localProviderRepository;
  final String[] PUBLIC_ENDPOINTS =
      new String[] {
        //Google login
        "/login/oauth2/code/google/**",
        "/oauth2/authorization/google/**",
        //AI assistant
        "/api/ai-assistant/**",

        "/auth/sign-up",
        "/auth/sign-in",
        "/auth/refresh",
        "/auth/current-user/permissions",
        "/auth/verify-email-by-code",
        "/auth/verify-email-by-token",
        "/auth/send-email-verification",
        "/auth/send-forgot-password",
        "/auth/forgot-password",
        "/auth/reset-password",
        "/auth/sign-out",
        "/auth/health",
        "/auth/introspect",
        "/auth/test",
        "/actuator/health",
        "/actuator/info",
        "/actuator/prometheus",
        "/actuator/metrics",
        "/api-docs/**",
        "/v3/api-docs/**",
        "/swagger-ui/**",
        "/swagger-ui.html",
        "/swagger-resources/**",
        "/seeds/**"
      };

  @Bean
  SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.csrf(AbstractHttpConfigurer::disable);
    httpSecurity.authorizeHttpRequests(
        request -> {
          request.requestMatchers(PUBLIC_ENDPOINTS).permitAll().anyRequest().authenticated();
        });
    httpSecurity.oauth2Login(oauth2 -> oauth2
      .loginPage("/oauth2/authorization/google")
      .successHandler(customOAuth2SuccessHandler(localProviderRepository,accountRepository)) // Thêm success handler
      .failureUrl("/login?error=true") // Chuyển hướng đến trang lỗi nếu đăng nhập thất bại
    );
    httpSecurity.oauth2ResourceServer(
        oauth2 ->
            oauth2
                .jwt(
                    jwtConfigurer ->
                        jwtConfigurer
                            .decoder(customJwtDecoder)
                            .jwtAuthenticationConverter(jwtAuthenticationConverter()))
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint()));
    return httpSecurity.build();
  }

  @Bean
  CorsFilter corsFilter() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();

    corsConfiguration.addAllowedOrigin("*");
    corsConfiguration.addAllowedMethod("*");
    corsConfiguration.addAllowedHeader("*");

    UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource =
        new UrlBasedCorsConfigurationSource();
    urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

    return new CorsFilter(urlBasedCorsConfigurationSource);
  }

  @Bean
  JwtAuthenticationConverter jwtAuthenticationConverter() {
    JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter =
        new JwtGrantedAuthoritiesConverter();
    jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");

    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

    return jwtAuthenticationConverter;
  }
  @Bean
  public CustomOAuth2SuccessHandler customOAuth2SuccessHandler(LocalProviderRepository localProviderRepository, AccountRepository accountRepository) {
    return new CustomOAuth2SuccessHandler(localProviderRepository,accountRepository);
  }
  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
