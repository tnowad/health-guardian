package com.example.health_guardian_server.services;

import com.example.health_guardian_server.dtos.requests.CreateUserRequest;
import com.example.health_guardian_server.dtos.requests.ListUsersRequest;
import com.example.health_guardian_server.dtos.requests.UpdateUserRequest;
import com.example.health_guardian_server.dtos.responses.CurrentUserInfomationResponse;
import com.example.health_guardian_server.dtos.responses.UserResponse;
import com.example.health_guardian_server.entities.User;
import com.example.health_guardian_server.entities.UserType;
import org.springframework.data.domain.Page;

public interface UserService {
  User getUserByAccountId(String accountId);

  User createUser(UserType patient);

  User getUserById(String userId);

  User saveUser(User user);

  Page<UserResponse> listUsers(ListUsersRequest request);

  UserResponse createUser(CreateUserRequest request);

  UserResponse getUser(String userId);

  UserResponse updateUser(String userId, UpdateUserRequest request);

  void deleteUser(String userId);

  CurrentUserInfomationResponse getCurrentUserInformation();
}
