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
  UserMapper userMapper;
  MinioClientService minioClientService;

  @Override
  public User getUserByAccountId(String accountId) {
    return userRepository.findByAccountId(accountId);
  }

  @Override
  public User createUser(User user) {
    return userRepository.save(user);
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
    var users =
        userRepository
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
    User user = userRepository.findById(userId).get();
    user.setAvatar(request.getAvatar());
    return userMapper.toUserResponse(userRepository.save(user));
  }

  @Override
  public void deleteUser(String userId) {
    throw new UnsupportedOperationException("Unimplemented method 'deleteUser'");
  }

  @Override
  public CurrentUserInfomationResponse getCurrentUserInformation() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();

    User user =
        userRepository.findById(username).orElseThrow(() -> new RuntimeException("User not found"));

    return buildResponse(user);
  }

  private CurrentUserInfomationResponse buildResponse(User user) {
    CurrentUserInfomationResponse response = new CurrentUserInfomationResponse();

    response.setUserId(user.getId());
    response.setUsername(user.getUsername());
    response.setEmail(user.getEmail());
    response.setName(user.getUsername());
    var url =
        "https://genk.mediacdn.vn/2018/10/19/photo-1-15399266837281100315834-15399271585711710441111.png";
    try {
      url = minioClientService.getObjectUrl(user.getAvatar(), "user-avatars");
    } catch (Exception e) {
      log.error("Error when get avatar url", e);
    }
    response.setAvatarUrl(url);
    response.setRole(user.getType());

    if (user.getUserStaffs().size() > 0) {
      response.setName(
          user.getUserStaffs().getFirst().getFirstName()
              + " "
              + user.getUserStaffs().getFirst().getLastName());
      StaffInforResponse staffResponse = new StaffInforResponse();
      staffResponse.setId(user.getUserStaffs().getFirst().getId());
      response.setStaff(staffResponse);
    }

    if (user.getUserMedicalStaffs().size() > 0) {
      MedicalStaffInforResponse medicalStaffResponse = new MedicalStaffInforResponse();
      var a = user.getUserMedicalStaffs();
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
