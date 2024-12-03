package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.entities.LocalProvider;
import com.example.health_guardian_server.repositories.LocalProviderRepository;
import com.example.health_guardian_server.services.LocalProviderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class LocalProviderServiceImpl implements LocalProviderService {

  LocalProviderRepository localProviderRepository;
  PasswordEncoder passwordEncoder;

  @Override
  public LocalProvider getLocalProviderByEmail(String email) {
    var a = localProviderRepository.findByEmail(email);
    return localProviderRepository.findByEmail(email);
  }

  @Override
  public boolean verifyLocalProviderPassword(String email, String password) {
    LocalProvider localProvider = localProviderRepository.findByEmail(email);
    return passwordEncoder.matches(password, localProvider.getPasswordHash());
  }

  @Override
  public LocalProvider createLocalProvider(String email, String password) {
    LocalProvider localProvider = new LocalProvider();
    localProvider.setEmail(email);
    localProvider.setPasswordHash(passwordEncoder.encode(password));
    return localProviderRepository.save(localProvider);
  }
}
