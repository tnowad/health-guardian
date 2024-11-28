package com.example.health_guardian_server.configurations;

import com.example.health_guardian_server.entities.*;
import com.example.health_guardian_server.repositories.AccountRepository;
import com.example.health_guardian_server.repositories.ExternalProviderRepository;
import com.example.health_guardian_server.repositories.LocalProviderRepository;
import com.example.health_guardian_server.repositories.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Optional;


public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final AccountRepository accountRepository;
    private final ExternalProviderRepository externalProviderRepository;
    private final OAuth2AuthorizedClientService authorizedClientService;
    private final UserRepository userRepository;
    public CustomOAuth2SuccessHandler(ExternalProviderRepository externalProviderRepository, AccountRepository accountRepository, UserRepository userRepository, OAuth2AuthorizedClientService authorizedClientService) {
        this.accountRepository = accountRepository;
        this.externalProviderRepository = externalProviderRepository;
        this.authorizedClientService = authorizedClientService;
        this.userRepository = userRepository;

    }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication) throws IOException, ServletException {
    OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

    // Lấy thông tin từ oauthUser để lưu vào database
    String provider = oauthUser.getName();
    String providerUserId = oauthUser.getAttribute("sub");
    String email = oauthUser.getAttribute("email");
    OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(authentication.getName(), authentication.getName());

    if (authorizedClient == null) {
      // Handle the case where the authorized client is not found
      response.sendRedirect("/error");
      return;
    }

    String token = authorizedClient.getAccessToken().getTokenValue();

    // Kiểm tra nếu người dùng đã tồn tại trong cơ sở dữ liệu
    Optional<ExternalProvider> existingExternalProviderOpt = externalProviderRepository.findByProviderNameAndProviderUserId(provider, providerUserId);
    if (existingExternalProviderOpt.isPresent()) {
      // Người dùng đã tồn tại, có thể cập nhật thông tin hoặc làm gì đó
      ExternalProvider existingExternalProvider = existingExternalProviderOpt.get();
      existingExternalProvider.setToken(token);
      externalProviderRepository.save(existingExternalProvider);
    } else {
      // Người dùng chưa tồn tại, tạo mới
      User user = User.builder()
        .type(UserType.PATIENT)  // Gán đúng loại người dùng
        .build();
      userRepository.save(user);

      Account account = Account.builder()
        .profileType(provider)
        .status(AccountStatus.ACTIVE)
        .user(user)
        .build();
      accountRepository.save(account);

      ExternalProvider externalProvider = ExternalProvider.builder()
        .account(account)
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

}
