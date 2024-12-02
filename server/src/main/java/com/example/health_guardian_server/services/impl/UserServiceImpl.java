package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.CreateUserRequest;
import com.example.health_guardian_server.dtos.requests.ListUsersRequest;
import com.example.health_guardian_server.dtos.requests.UpdateUserRequest;
import com.example.health_guardian_server.dtos.responses.CurrentUserInfomationResponse;
import com.example.health_guardian_server.dtos.responses.MedicalStaffInforResponse;
import com.example.health_guardian_server.dtos.responses.PatientInforResponse;
import com.example.health_guardian_server.dtos.responses.StaffInforResponse;
import com.example.health_guardian_server.dtos.responses.UserResponse;
import com.example.health_guardian_server.entities.User;
import com.example.health_guardian_server.entities.UserType;
import com.example.health_guardian_server.mappers.UserMapper;
import com.example.health_guardian_server.repositories.UserRepository;
import com.example.health_guardian_server.services.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
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
    var users = userRepository
        .findAll(request.toSpecification(), request.toPageable())
        .map(userMapper::toUserResponse);
    return users;
  }

  @Override
  public UserResponse createUser(CreateUserRequest request) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'createUser'");
  }

  @Override
  public UserResponse getUser(String userId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getUser'");
  }

  @Override
  public UserResponse updateUser(String userId, UpdateUserRequest request) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateUser'");
  }

  @Override
  public void deleteUser(String userId) {
    throw new UnsupportedOperationException("Unimplemented method 'deleteUser'");
  }

  @Override
  public CurrentUserInfomationResponse getCurrentUserInformation() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();

    User user = userRepository.findById(username).orElseThrow(() -> new RuntimeException("User not found"));

    return buildResponse(user);
  }

  private CurrentUserInfomationResponse buildResponse(User user) {
    CurrentUserInfomationResponse response = new CurrentUserInfomationResponse();

    response.setUserId(user.getId());
    response.setUsername(user.getUsername());
    response.setEmail(user.getEmail());
    response.setName(user.getUsername());
    response.setRole(user.getType());

    if (user.getUserStaffs() != null) {
      response.setName(
          user.getUserStaffs().getFirst().getFirstName()
              + " "
              + user.getUserStaffs().getFirst().getLastName());
      StaffInforResponse staffResponse = new StaffInforResponse();
      staffResponse.setId(user.getUserStaffs().getFirst().getId());
      response.setStaff(staffResponse);
    }

    if (user.getUserMedicalStaffs() != null) {
      MedicalStaffInforResponse medicalStaffResponse = new MedicalStaffInforResponse();
      medicalStaffResponse.setId(user.getUserMedicalStaffs().getFirst().getId());
      medicalStaffResponse.setSpecialization(
          user.getUserMedicalStaffs().getFirst().getSpecialization());
      response.setMedicalStaff(medicalStaffResponse);
    }

    if (user.getPatient() != null) {
      response.setName(user.getPatient().getFirstName() + " " + user.getPatient().getLastName());
      PatientInforResponse patientResponse = new PatientInforResponse();
      patientResponse.setId(user.getPatient().getId());
      response.setPatient(patientResponse);
    }

    return response;
  }
}
