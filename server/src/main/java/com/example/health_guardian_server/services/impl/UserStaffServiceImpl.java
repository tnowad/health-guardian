package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.CreateUserStaffRequest;
import com.example.health_guardian_server.dtos.responses.UserStaffResponse;
import com.example.health_guardian_server.entities.User;
import com.example.health_guardian_server.entities.UserStaff;
import com.example.health_guardian_server.repositories.UserRepository;
import com.example.health_guardian_server.repositories.UserStaffRepository;
import com.example.health_guardian_server.services.UserStaffService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserStaffServiceImpl implements UserStaffService {

  private final UserStaffRepository userStaffRepository;
  private final UserRepository userRepository;

  public UserStaffServiceImpl(UserStaffRepository userStaffRepository, UserRepository userRepository) {

    this.userStaffRepository = userStaffRepository;
    this.userRepository = userRepository;
  }
  // Implement methods

  @Override
  public Page<UserStaffResponse> getAllUserStaffs(int page, int size) {

    return userStaffRepository.findAll(PageRequest.of(page,size))
      .map(userStaff -> new UserStaffResponse(userStaff.getId(),userStaff.getUser(),userStaff.getRole(),userStaff.getRoleType()));

  }

  @Override
  public UserStaffResponse getUserStaffById(String id) {

    UserStaff userStaff = userStaffRepository.findById(id).orElse(null);
    if (userStaff == null) {
      return null;
    }
    return new UserStaffResponse(userStaff.getId(),userStaff.getUser(),userStaff.getRole(),userStaff.getRoleType());
  }



  @Override
  public UserStaffResponse createUserStaff(CreateUserStaffRequest createUserStaffRequest) {

    User user = userRepository.findById(createUserStaffRequest.getUserId()).orElse(null);

    UserStaff userStaff = new UserStaff();
    userStaff.setUser(user);
    userStaff.setRole(createUserStaffRequest.getRole());
    userStaff.setRoleType(createUserStaffRequest.getRoleType());
    return new UserStaffResponse(userStaffRepository.save(userStaff).getId(),userStaff.getUser(),userStaff.getRole(),userStaff.getRoleType());
  }

  @Override
  public UserStaffResponse updateUserStaff(String id, UserStaffResponse userStaff) {

    UserStaff existingUserStaff = userStaffRepository.findById(id).orElse(null);
    if (existingUserStaff == null) {
      return null;
    }
    existingUserStaff.setUser(userStaff.getUser());
    existingUserStaff.setRole(userStaff.getRole());
    existingUserStaff.setRoleType(userStaff.getRoleType());
    return new UserStaffResponse(userStaffRepository.save(existingUserStaff).getId(),existingUserStaff.getUser(),existingUserStaff.getRole(),existingUserStaff.getRoleType());
  }

  @Override
  public void deleteUserStaff(String id) {

    userStaffRepository.deleteById(id);
  }

}
