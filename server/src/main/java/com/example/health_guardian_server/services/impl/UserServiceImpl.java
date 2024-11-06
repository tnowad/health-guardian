package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.entities.User;
import com.example.health_guardian_server.repositories.UserRepository;
import com.example.health_guardian_server.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  UserRepository userRepository;

  @Override
  public User getUserByAccountId(String accountId) {
    return userRepository.findByAccountId(accountId);
  }
}
