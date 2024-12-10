package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.entities.LocalProvider;
import com.example.health_guardian_server.repositories.LocalProviderRepository;
import com.example.health_guardian_server.services.LocalProviderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class LocalProviderServiceImpl implements LocalProviderService {

  LocalProviderRepository localProviderRepository;
  PasswordEncoder passwordEncoder;

  @Override
  public LocalProvider getLocalProviderByEmail(String email) {
    log.debug("Fetching LocalProvider with email: {}", email);
    return localProviderRepository.findByEmail(email);
  }

  @Override
  public LocalProvider saveLocalProvider(LocalProvider localProvider) {
    return localProviderRepository.save(localProvider);
  }

  @Override
  public boolean verifyLocalProviderPassword(String email, String password) {
    log.debug("Verifying password for LocalProvider with email: {}", email);
    LocalProvider localProvider = getLocalProviderByEmail(email);
    boolean matches = passwordEncoder.matches(password, localProvider.getPasswordHash());
    log.info("Password verification for email {}: {}", email, matches ? "successful" : "failed");
    return matches;
  }

  @Override
  public LocalProvider createLocalProvider(LocalProvider localProvider) {
    log.debug("Creating a new LocalProvider with email: {}", localProvider.getEmail());
    if (localProviderRepository.findByEmail(localProvider.getEmail()) != null) {
      log.warn("LocalProvider already exists for email: {}", localProvider.getEmail());
      throw new IllegalArgumentException("LocalProvider with this email already exists");
    }
    LocalProvider savedProvider = localProviderRepository.save(localProvider);
    log.info("LocalProvider created with ID: {}", savedProvider.getId());
    return savedProvider;
  }
}
