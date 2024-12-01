package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.CreateUserMedicalStaffRequest;
import com.example.health_guardian_server.dtos.responses.UserMedicalStaffResponse;
import com.example.health_guardian_server.entities.User;
import com.example.health_guardian_server.entities.UserMedicalStaff;
import com.example.health_guardian_server.repositories.UserMedicalStaffRepository;
import com.example.health_guardian_server.repositories.UserRepository;
import com.example.health_guardian_server.repositories.UserStaffRepository;
import com.example.health_guardian_server.services.UserMedicalStaffService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class UserMedicalStaffServiceImpl implements UserMedicalStaffService {
  // Implement methods

  private final UserMedicalStaffRepository userMedicalStaffRepository;
  private final UserRepository userRepository;

  public UserMedicalStaffServiceImpl(UserMedicalStaffRepository userMedicalStaffRepository, UserRepository userRepository) {
    this.userMedicalStaffRepository = userMedicalStaffRepository;
    this.userRepository = userRepository;
  }

  @Override
  public Page<UserMedicalStaffResponse> getAllUserMedicalStaffs(int page, int size) {

    return userMedicalStaffRepository.findAll(PageRequest.of(page,size))
  .map(userMedicalStaff -> new UserMedicalStaffResponse(userMedicalStaff.getId(),userMedicalStaff.getUser(),userMedicalStaff.getHospital(),userMedicalStaff.getStaffType(),userMedicalStaff.getSpecialization(),userMedicalStaff.getRole(),userMedicalStaff.getActive(),userMedicalStaff.getEndDate()));
  }

  @Override
  public UserMedicalStaffResponse getUserMedicalStaffById(String id) {
    return userMedicalStaffRepository.findById(id)
  .map(userMedicalStaff -> new UserMedicalStaffResponse(userMedicalStaff.getId(),userMedicalStaff.getUser(),userMedicalStaff.getHospital(),userMedicalStaff.getStaffType(),userMedicalStaff.getSpecialization(),userMedicalStaff.getRole(),userMedicalStaff.getActive(),userMedicalStaff.getEndDate()))
  .orElse(null);
  }

  @Override
  public UserMedicalStaffResponse createUserMedicalStaff(CreateUserMedicalStaffRequest userMedicalStaff) {
    User user = userRepository.findById(userMedicalStaff.getUserId()).orElse(null);
    UserMedicalStaff userMedicalStaff1 = UserMedicalStaff.builder()
      .active(true)
      .staffType(userMedicalStaff.getStaffType())
      .specialization(userMedicalStaff.getSpecialization())
      .role(userMedicalStaff.getRole())
      .endDate(userMedicalStaff.getEndDate())
      .user(user)
      .hospital(userMedicalStaff.getHospital())
      .build();
    return new UserMedicalStaffResponse(userMedicalStaffRepository.save(userMedicalStaff1).getId(),userMedicalStaff1.getUser(),userMedicalStaff1.getHospital(),userMedicalStaff1.getStaffType(),userMedicalStaff1.getSpecialization(),userMedicalStaff1.getRole(),userMedicalStaff1.getActive(),userMedicalStaff1.getEndDate());
  }

  @Override
  public UserMedicalStaffResponse updateUserMedicalStaff(String id, UserMedicalStaffResponse userMedicalStaff) {

    UserMedicalStaff userMedicalStaff1 = userMedicalStaffRepository.findById(id).orElse(null);
    if (userMedicalStaff1 == null) {
      return null;
    }
    userMedicalStaff1.setActive(userMedicalStaff.isActive());
    userMedicalStaff1.setStaffType(userMedicalStaff.getStaffType());
    userMedicalStaff1.setSpecialization(userMedicalStaff.getSpecialization());
    userMedicalStaff1.setRole(userMedicalStaff.getRole());
    userMedicalStaff1.setEndDate(userMedicalStaff.getEndDate());
    userMedicalStaff1.setHospital(userMedicalStaff.getHospital());
    return new UserMedicalStaffResponse(userMedicalStaffRepository.save(userMedicalStaff1).getId(),userMedicalStaff1.getUser(),userMedicalStaff1.getHospital(),userMedicalStaff1.getStaffType(),userMedicalStaff1.getSpecialization(),userMedicalStaff1.getRole(),userMedicalStaff1.getActive(),userMedicalStaff1.getEndDate());
  }

  @Override
  public void deleteUserMedicalStaff(String id) {
    userMedicalStaffRepository.deleteById(id);
  }

}
