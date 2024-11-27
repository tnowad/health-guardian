package com.example.health_guardian_server.services;

import com.example.health_guardian_server.entities.User;
import com.example.health_guardian_server.entities.UserType;

public interface UserService {
  User getUserByAccountId(String accountId);

  User createUser(UserType patient);

  User getUserById(String userId);

  User saveUser(User user);
}
