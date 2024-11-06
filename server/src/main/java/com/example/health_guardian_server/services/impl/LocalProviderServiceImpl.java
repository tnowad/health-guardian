package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.entities.LocalProvider;
import com.example.health_guardian_server.repositories.LocalProviderRepository;
import com.example.health_guardian_server.services.LocalProviderService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocalProviderServiceImpl implements LocalProviderService {

  LocalProviderRepository localProviderRepository;
  BCryptPasswordEncoder passwordEncoder;

  @Override
  public LocalProvider getLocalProviderByEmail(String email) {
    return localProviderRepository.findByEmail(email);
  }

  @Override
  public boolean verifyLocalProviderPassword(String email, String password) {
    LocalProvider localProvider = localProviderRepository.findByEmail(email);
    return passwordEncoder.matches(password, localProvider.getPasswordHash());
  }
}
