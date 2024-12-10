package com.example.health_guardian_server.configurations;

import com.example.health_guardian_server.entities.ExternalProvider;
import com.example.health_guardian_server.entities.User;
import com.example.health_guardian_server.repositories.ExternalProviderRepository;
import com.example.health_guardian_server.repositories.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {
  private final ExternalProviderRepository externalProviderRepository;
  private final OAuth2AuthorizedClientService authorizedClientService;
  private final UserRepository userRepository;

  public CustomOAuth2SuccessHandler(
      ExternalProviderRepository externalProviderRepository,
      UserRepository userRepository,
      OAuth2AuthorizedClientService authorizedClientService) {
    this.externalProviderRepository = externalProviderRepository;
    this.authorizedClientService = authorizedClientService;
    this.userRepository = userRepository;
  }

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {
    OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

    // Lấy thông tin từ oauthUser để lưu vào database
    String provider = oauthUser.getName();
    String providerUserId = oauthUser.getAttribute("sub");
    String email = oauthUser.getAttribute("email");
    OAuth2AuthorizedClient authorizedClient =
        authorizedClientService.loadAuthorizedClient("google", authentication.getName());

    if (authorizedClient == null) {
      // Handle the case where the authorized client is not found
      response.sendRedirect("/error");
      return;
    }

    String token = authorizedClient.getAccessToken().getTokenValue();

    // Kiểm tra nếu người dùng đã tồn tại trong cơ sở dữ liệu
    Optional<ExternalProvider> existingExternalProviderOpt =
        externalProviderRepository.findByProviderNameAndProviderUserId(provider, providerUserId);
    if (existingExternalProviderOpt.isPresent()) {
      // Người dùng đã tồn tại, có thể cập nhật thông tin hoặc làm gì đó
      ExternalProvider existingExternalProvider = existingExternalProviderOpt.get();
      existingExternalProvider.setToken(token);
      externalProviderRepository.save(existingExternalProvider);
    } else {
      // Người dùng chưa tồn tại, tạo mới
      User user = User.builder().build();
      userRepository.save(user);

      // accountRepository.save(account);

      ExternalProvider externalProvider =
          ExternalProvider.builder()
              .providerName(provider)
              .providerUserId(providerUserId)
              .providerUserEmail(email)
              .token(token)
              .build();
      externalProviderRepository.save(externalProvider);
    }

    // Chuyển hướng về trang home hoặc trang khác sau khi lưu thành công
    response.sendRedirect("/home");
  }

  @Bean
  public OAuth2AuthorizedClientService authorizedClientService(
      ClientRegistrationRepository clientRegistrationRepository) {
    return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);
  }
}
