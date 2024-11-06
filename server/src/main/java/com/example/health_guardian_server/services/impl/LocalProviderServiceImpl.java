package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.responses.LocalProviderResponse;
import com.example.health_guardian_server.dtos.responses.UserResponse;
import com.example.health_guardian_server.repositories.LocalProviderRepository;
import com.example.health_guardian_server.services.LocalProviderService;
import org.springframework.stereotype.Service;

@Service
public class LocalProviderServiceImpl implements LocalProviderService {

  LocalProviderRepository localProviderRepository;

  @Override
  public LocalProviderResponse authenticate(String email, String password) {

  }
}
