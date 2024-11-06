package com.example.health_guardian_server.services;

import com.example.health_guardian_server.entities.LocalProvider;

public interface LocalProviderService {
  LocalProvider getLocalProviderByEmail(String email);

  boolean verifyLocalProviderPassword(String email, String password);
}
