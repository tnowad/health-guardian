package com.example.health_guardian_server.services;

import com.example.health_guardian_server.entities.User;

public interface UserService {
  User getUserByAccountId(String accountId);
}
