package com.example.health_guardian_server.services;

import org.springframework.data.domain.Page;

import com.example.health_guardian_server.dtos.requests.ListUsersRequest;
import com.example.health_guardian_server.dtos.responses.UserResponse;
import com.example.health_guardian_server.entities.User;
import com.example.health_guardian_server.entities.UserType;

public interface UserService {
  User getUserByAccountId(String accountId);

  User createUser(UserType patient);

  User getUserById(String userId);

  User saveUser(User user);

  Page<UserResponse> listUsers(ListUsersRequest request);
}
