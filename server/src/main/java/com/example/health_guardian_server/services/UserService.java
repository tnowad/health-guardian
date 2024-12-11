package com.example.health_guardian_server.services;

import com.example.health_guardian_server.dtos.requests.user.CreateUserRequest;
import com.example.health_guardian_server.dtos.requests.user.ListUsersRequest;
import com.example.health_guardian_server.dtos.requests.user.UpdateUserRequest;
import com.example.health_guardian_server.dtos.responses.user.CurrentUserInfomationResponse;
import com.example.health_guardian_server.dtos.responses.user.UserResponse;
import com.example.health_guardian_server.entities.User;

import java.util.Optional;

import org.springframework.data.domain.Page;

public interface UserService {
  User getUserByAccountId(String accountId);

  User createUser(User user);

  User getUserById(String userId);

  Optional<User> getOptionalUserById(String userId);

  User saveUser(User user);

  User getUserByEmail(String email);

  Page<UserResponse> listUsers(ListUsersRequest request);

  UserResponse createUser(CreateUserRequest request);

  UserResponse getUser(String userId);

  UserResponse updateUser(String userId, UpdateUserRequest request);

  void deleteUser(String userId);

  CurrentUserInfomationResponse getCurrentUserInformation();
}
