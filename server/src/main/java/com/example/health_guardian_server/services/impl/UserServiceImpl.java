package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.CreateUserRequest;
import com.example.health_guardian_server.dtos.requests.ListUsersRequest;
import com.example.health_guardian_server.dtos.requests.UpdateUserRequest;
import com.example.health_guardian_server.dtos.responses.CurrentUserInfomationResponse;
import com.example.health_guardian_server.dtos.responses.UserResponse;
import com.example.health_guardian_server.entities.User;
import com.example.health_guardian_server.mappers.UserMapper;
import com.example.health_guardian_server.repositories.UserRepository;
import com.example.health_guardian_server.services.MinioClientService;
import com.example.health_guardian_server.services.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {

  UserRepository userRepository;
  MinioClientService minioClientService;
  UserMapper userMapper;

  @Override
  public User getUserByAccountId(String id) {
    log.debug("Fetching user with accountId: {}", id);
    User user = userRepository.findById(id).get();
    if (user == null) {
      log.warn("User with accountId: {} not found", id);
    } else {
      log.info("User with accountId: {} found", id);
    }
    return user;
  }

  @Override
  public User createUser(User user) {
    log.debug("Creating new user with email: {}", user.getEmail());
    User savedUser = userRepository.save(user);
    log.info("User with email: {} created successfully", savedUser.getEmail());
    return savedUser;
  }

  @Override
  public User getUserById(String userId) {
    log.debug("Fetching user with userId: {}", userId);
    User user = userRepository.findById(userId).orElse(null);
    if (user == null) {
      log.warn("User with userId: {} not found", userId);
    } else {
      log.info("User with userId: {} found", userId);
    }
    return user;
  }

  @Override
  public User saveUser(User user) {
    log.debug("Saving user with userId: {}", user.getId());
    User savedUser = userRepository.save(user);
    log.info("User with userId: {} saved successfully", savedUser.getId());
    return savedUser;
  }

  @Override
  public Page<UserResponse> listUsers(ListUsersRequest request) {
    log.debug(
        "Listing users with pagination: page = {}, size = {}",
        request.getPage(),
        request.getSize());
    Page<UserResponse> users =
        userRepository
            .findAll(request.toSpecification(), request.toPageable())
            .map(userMapper::toUserResponse);
    log.info("Found {} users", users.getTotalElements());
    return users;
  }

  @Override
  public UserResponse createUser(CreateUserRequest request) {
    log.debug("Creating user with request: {}", request);
    throw new UnsupportedOperationException("Unimplemented method 'createUser'");
  }

  @Override
  public UserResponse getUser(String userId) {
    log.debug("Fetching user with userId: {}", userId);
    throw new UnsupportedOperationException("Unimplemented method 'getUser'");
  }

  @Override
  public UserResponse updateUser(String userId, UpdateUserRequest request) {
    log.debug("Updating user with userId: {}", userId);
    User user =
        userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    log.debug("User found: {}", user);
    user.setAvatar(request.getAvatar());
    User updatedUser = userRepository.save(user);
    log.info("User with userId: {} updated successfully", updatedUser.getId());
    return userMapper.toUserResponse(updatedUser);
  }

  @Override
  public void deleteUser(String userId) {
    log.debug("Deleting user with userId: {}", userId);
    throw new UnsupportedOperationException("Unimplemented method 'deleteUser'");
  }

  @Override
  public CurrentUserInfomationResponse getCurrentUserInformation() {
    log.debug("Fetching current user information");
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    log.debug("Current authenticated username: {}", username);

    User user =
        userRepository.findById(username).orElseThrow(() -> new RuntimeException("User not found"));
    log.debug("User found: {}", user.getEmail());

    return buildResponse(user);
  }

  private CurrentUserInfomationResponse buildResponse(User user) {
    log.debug("Building current user information response");
    CurrentUserInfomationResponse response = new CurrentUserInfomationResponse();
    response.setUserId(user.getId());
    response.setEmail(user.getEmail());
    response.setName(user.getFirstName() + " " + user.getLastName());
    String url = "https://default-avatar-url.com";
    try {
      url = minioClientService.getObjectUrl(user.getAvatar(), "user-avatars");
      log.debug("Avatar URL retrieved: {}", url);
    } catch (Exception e) {
      log.error("Error when retrieving avatar URL for user: {}", user.getEmail(), e);
    }
    response.setAvatarUrl(url);

    log.debug("User information response built successfully: {}", response);
    return response;
  }
}
