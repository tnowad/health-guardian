package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.ListUsersRequest;
import com.example.health_guardian_server.dtos.responses.UserResponse;
import com.example.health_guardian_server.entities.User;
import com.example.health_guardian_server.entities.UserType;
import com.example.health_guardian_server.mappers.UserMapper;
import com.example.health_guardian_server.repositories.UserRepository;
import com.example.health_guardian_server.services.UserService;
import com.example.health_guardian_server.specifications.UserSpecification;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {

  UserRepository userRepository;
  UserMapper userMapper;

  @Override
  public User getUserByAccountId(String accountId) {
    return userRepository.findByAccountId(accountId);
  }

  @Override
  public User createUser(UserType patient) {
    return userRepository.save(User.builder().type(UserType.PATIENT).build());
  }

  @Override
  public User getUserById(String userId) {
    return userRepository.findById(userId).get();
  }

  @Override
  public User saveUser(User user) {
    return userRepository.save(user);
  }

  @Override
  public Page<UserResponse> listUsers(ListUsersRequest request) {
    PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
    UserSpecification specification = new UserSpecification(request);

    var users = userRepository.findAll(specification, pageRequest).map(userMapper::toUserResponse);
    return users;
  }
}
