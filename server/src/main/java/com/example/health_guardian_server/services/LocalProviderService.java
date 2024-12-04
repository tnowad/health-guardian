package com.example.health_guardian_server.services;

import com.example.health_guardian_server.entities.LocalProvider;

public interface LocalProviderService {
  LocalProvider getLocalProviderByEmail(String email);

  LocalProvider saveLocalProvider(LocalProvider localProvider);

  boolean verifyLocalProviderPassword(String email, String password);

  LocalProvider createLocalProvider(String email, String password);
}
