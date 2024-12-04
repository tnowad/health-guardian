package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.CreateUserStaffRequest;
import com.example.health_guardian_server.dtos.requests.ListUserStaffRequest;
import com.example.health_guardian_server.dtos.requests.UpdateUserStaffRequest;
import com.example.health_guardian_server.dtos.responses.UserStaffResponse;
import com.example.health_guardian_server.entities.User;
import com.example.health_guardian_server.entities.UserStaff;
import com.example.health_guardian_server.mappers.UserStaffMapper;
import com.example.health_guardian_server.repositories.UserRepository;
import com.example.health_guardian_server.repositories.UserStaffRepository;
import com.example.health_guardian_server.services.UserStaffService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j  // This enables logging
public class UserStaffServiceImpl implements UserStaffService {

  private final UserStaffRepository userStaffRepository;
  private final UserRepository userRepository;
  private final UserStaffMapper userStaffMapper;

  @Override
  public Page<UserStaffResponse> getAllUserStaffs(ListUserStaffRequest request) {
    log.debug("Fetching all user staff with page: {} and size: {}", request.getPage(), request.getSize());

    Page<UserStaff> userStaffs =
      userStaffRepository.findAll(PageRequest.of(request.getPage(), request.getSize()));

    log.info("Successfully fetched {} user staff records.", userStaffs.getTotalElements());

    return userStaffs.map(
      userStaff ->
        new UserStaffResponse(
          userStaff.getId(),
          userStaff.getUser(),
          userStaff.getRole(),
          userStaff.getRoleType()));
  }

  @Override
  public UserStaffResponse getUserStaffById(String id) {
    log.debug("Fetching user staff by id: {}", id);

    UserStaff userStaff = userStaffRepository.findById(id).orElse(null);

    if (userStaff == null) {
      log.warn("User staff with id {} not found", id);
      return null;
    }

    log.info("Successfully fetched user staff with id: {}", id);

    return new UserStaffResponse(
      userStaff.getId(), userStaff.getUser(), userStaff.getRole(), userStaff.getRoleType());
  }

  @Override
  public UserStaffResponse createUserStaff(CreateUserStaffRequest createUserStaffRequest) {
    log.debug("Creating user staff for userId: {}", createUserStaffRequest.getUserId());

    User user = userRepository.findById(createUserStaffRequest.getUserId()).orElse(null);
    if (user == null) {
      log.error("User with id {} not found", createUserStaffRequest.getUserId());
      throw new ResourceNotFoundException("User not found");
    }

    UserStaff userStaff = new UserStaff();
    userStaff.setUser(user);
    userStaff.setRole(createUserStaffRequest.getRole());
    userStaff.setRoleType(createUserStaffRequest.getRoleType());

    UserStaff savedUserStaff = userStaffRepository.save(userStaff);
    log.info("Successfully created user staff with id: {}", savedUserStaff.getId());

    return new UserStaffResponse(
      savedUserStaff.getId(),
      savedUserStaff.getUser(),
      savedUserStaff.getRole(),
      savedUserStaff.getRoleType());
  }

  @Override
  public UserStaffResponse updateUserStaff(UserStaffResponse userStaff) {
    log.debug("Updating user staff with id: {}", userStaff.getId());

    UserStaff existingUserStaff = userStaffRepository.findById(userStaff.getId()).orElse(null);

    if (existingUserStaff == null) {
      log.warn("User staff with id {} not found", userStaff.getId());
      return null;
    }

    existingUserStaff.setRole(userStaff.getRole());
    existingUserStaff.setRoleType(userStaff.getRoleType());

    UserStaff updatedUserStaff = userStaffRepository.save(existingUserStaff);
    log.info("Successfully updated user staff with id: {}", updatedUserStaff.getId());

    return new UserStaffResponse(
      updatedUserStaff.getId(),
      updatedUserStaff.getUser(),
      updatedUserStaff.getRole(),
      updatedUserStaff.getRoleType());
  }

  @Override
  public void deleteUserStaff(String id) {
    log.debug("Deleting user staff with id: {}", id);

    try {
      userStaffRepository.deleteById(id);
      log.info("Successfully deleted user staff with id: {}", id);
    } catch (Exception e) {
      log.error("Error occurred while deleting user staff with id: {}", id, e);
    }
  }

  @Override
  public UserStaffResponse updateUserStaff(String id, UpdateUserStaffRequest request) {
    log.debug("Updating user staff with id: {} using request: {}", id, request);

    UserStaff userStaff =
      userStaffRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("UserStaff not found with id " + id));

    var updateUserStaff = userStaffMapper.toUserStaff(request);
    updateUserStaff.setId(userStaff.getId());

    UserStaff updatedUserStaff = userStaffRepository.save(updateUserStaff);
    log.info("Successfully updated user staff with id: {}", updatedUserStaff.getId());

    return userStaffMapper.toUserStaffResponse(updatedUserStaff);
  }
}
