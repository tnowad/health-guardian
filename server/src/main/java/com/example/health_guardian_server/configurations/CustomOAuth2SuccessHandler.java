package com.example.health_guardian_server.configurations;

import com.example.health_guardian_server.entities.Account;
import com.example.health_guardian_server.entities.LocalProvider;
import com.example.health_guardian_server.repositories.AccountRepository;
import com.example.health_guardian_server.repositories.LocalProviderRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Optional;


public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final LocalProviderRepository localProviderRepo;
    private final AccountRepository accountRepository;
    public CustomOAuth2SuccessHandler(LocalProviderRepository localProviderRepo, AccountRepository accountRepository) {
        this.localProviderRepo = localProviderRepo;
        this.accountRepository = accountRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");
        String defaultPassword = "123456";
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // Kiểm tra nếu người dùng đã tồn tại, nếu không thì lưu mới
      Optional<LocalProvider> existingUserOpt = Optional.ofNullable(localProviderRepo.findByEmail(email));
      existingUserOpt.ifPresentOrElse(existingUser -> {

      }, () -> {
        //create new account
        Account newAccount = new Account();
        accountRepository.save(newAccount);
        Account account = accountRepository.findAll().get(0);
        //create new local provider
        LocalProvider newLocalProvider = new LocalProvider();
        newLocalProvider.setAccountId(account.getId());
        newLocalProvider.setEmail(email);
        newLocalProvider.setPasswordHash(passwordEncoder.encode(defaultPassword));

        localProviderRepo.save(newLocalProvider);
        newLocalProvider.setAccountId(newAccount.getId());
      });

        // Chuyển hướng về trang home hoặc trang khác sau khi lưu thành công
        response.sendRedirect("/home");
    }
}
