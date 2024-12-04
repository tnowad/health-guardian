package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.CreateUserMedicalStaffRequest;
import com.example.health_guardian_server.dtos.requests.ListUserMedicalStaffRequest;
import com.example.health_guardian_server.dtos.responses.UserMedicalStaffResponse;
import com.example.health_guardian_server.entities.User;
import com.example.health_guardian_server.entities.UserMedicalStaff;
import com.example.health_guardian_server.repositories.UserMedicalStaffRepository;
import com.example.health_guardian_server.repositories.UserRepository;
import com.example.health_guardian_server.repositories.UserStaffRepository;
import com.example.health_guardian_server.services.UserMedicalStaffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j // Enable logging for the class
public class UserMedicalStaffServiceImpl implements UserMedicalStaffService {

  private final UserMedicalStaffRepository userMedicalStaffRepository;
  private final UserRepository userRepository;

  @Override
  public Page<UserMedicalStaffResponse> getAllUserMedicalStaffs(ListUserMedicalStaffRequest request) {
    log.debug("Fetching all user medical staff with request: {}", request);

    Page<UserMedicalStaff> userMedicalStaffs = userMedicalStaffRepository.findAll(PageRequest.of(request.getPage(), request.getSize()));

    log.debug("Found {} user medical staff records", userMedicalStaffs.getTotalElements());
    return userMedicalStaffs.map(userMedicalStaff ->
      new UserMedicalStaffResponse(
        userMedicalStaff.getId(),
        userMedicalStaff.getUser(),
        userMedicalStaff.getHospital(),
        userMedicalStaff.getStaffType(),
        userMedicalStaff.getSpecialization(),
        userMedicalStaff.getRole(),
        userMedicalStaff.getActive(),
        userMedicalStaff.getEndDate())
    );
  }

  @Override
  public UserMedicalStaffResponse getUserMedicalStaffById(String id) {
    log.debug("Fetching user medical staff by id: {}", id);

    return userMedicalStaffRepository.findById(id)
      .map(userMedicalStaff -> {
        log.info("User medical staff found with id: {}", id);
        return new UserMedicalStaffResponse(
          userMedicalStaff.getId(),
          userMedicalStaff.getUser(),
          userMedicalStaff.getHospital(),
          userMedicalStaff.getStaffType(),
          userMedicalStaff.getSpecialization(),
          userMedicalStaff.getRole(),
          userMedicalStaff.getActive(),
          userMedicalStaff.getEndDate());
      })
      .orElseGet(() -> {
        log.error("User medical staff not found for id: {}", id);
        return null; // Or handle the error as needed
      });
  }

  @Override
  public UserMedicalStaffResponse createUserMedicalStaff(CreateUserMedicalStaffRequest request) {
    log.debug("Creating user medical staff with request: {}", request);

    User user = userRepository.findById(request.getUserId()).orElse(null);
    if (user == null) {
      log.error("User not found for userId: {}", request.getUserId());
      return null; // Or handle the error as needed
    }

    UserMedicalStaff userMedicalStaff = UserMedicalStaff.builder()
      .active(true)
      .staffType(request.getStaffType())
      .specialization(request.getSpecialization())
      .role(request.getRole())
      .endDate(request.getEndDate())
      .user(user)
      .hospital(request.getHospital())
      .build();

    UserMedicalStaff savedUserMedicalStaff = userMedicalStaffRepository.save(userMedicalStaff);
    log.info("User medical staff created with id: {}", savedUserMedicalStaff.getId());

    return new UserMedicalStaffResponse(
      savedUserMedicalStaff.getId(),
      savedUserMedicalStaff.getUser(),
      savedUserMedicalStaff.getHospital(),
      savedUserMedicalStaff.getStaffType(),
      savedUserMedicalStaff.getSpecialization(),
      savedUserMedicalStaff.getRole(),
      savedUserMedicalStaff.getActive(),
      savedUserMedicalStaff.getEndDate()
    );
  }

  @Override
  public UserMedicalStaffResponse updateUserMedicalStaff(String id, UserMedicalStaffResponse request) {
    log.debug("Updating user medical staff with id: {} and request: {}", id, request);

    UserMedicalStaff existingUserMedicalStaff = userMedicalStaffRepository.findById(id).orElse(null);
    if (existingUserMedicalStaff == null) {
      log.error("User medical staff not found for id: {}", id);
      return null; // Or handle the error as needed
    }

    existingUserMedicalStaff.setActive(request.isActive());
    existingUserMedicalStaff.setStaffType(request.getStaffType());
    existingUserMedicalStaff.setSpecialization(request.getSpecialization());
    existingUserMedicalStaff.setRole(request.getRole());
    existingUserMedicalStaff.setEndDate(request.getEndDate());
    existingUserMedicalStaff.setHospital(request.getHospital());

    UserMedicalStaff updatedUserMedicalStaff = userMedicalStaffRepository.save(existingUserMedicalStaff);
    log.info("User medical staff updated with id: {}", id);

    return new UserMedicalStaffResponse(
      updatedUserMedicalStaff.getId(),
      updatedUserMedicalStaff.getUser(),
      updatedUserMedicalStaff.getHospital(),
      updatedUserMedicalStaff.getStaffType(),
      updatedUserMedicalStaff.getSpecialization(),
      updatedUserMedicalStaff.getRole(),
      updatedUserMedicalStaff.getActive(),
      updatedUserMedicalStaff.getEndDate()
    );
  }

  @Override
  public void deleteUserMedicalStaff(String id) {
    log.debug("Deleting user medical staff with id: {}", id);

    userMedicalStaffRepository.deleteById(id);
    log.info("User medical staff with id: {} deleted", id);
  }
}
