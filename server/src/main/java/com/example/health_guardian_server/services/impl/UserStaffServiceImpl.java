package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.CreateUserStaffRequest;
import com.example.health_guardian_server.dtos.requests.ListUserStaffRequest;
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
  public Page<UserStaffResponse> getAllUserStaffs(ListUserStaffRequest request) {

    Page<UserStaff> userStaffs = userStaffRepository.findAll(PageRequest.of(request.getPage(), request.getSize()));
    return userStaffs.map(userStaff -> new UserStaffResponse(userStaff.getId(),userStaff.getUser(),userStaff.getRole(),userStaff.getRoleType()));
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
  public UserStaffResponse updateUserStaff(UserStaffResponse userStaff) {

      UserStaff userStaff1 = userStaffRepository.findById(userStaff.getId()).orElse(null);
      if (userStaff1 == null) {
        return null;
      }
      userStaff1.setRole(userStaff.getRole());
      userStaff1.setRoleType(userStaff.getRoleType());
      return new UserStaffResponse(userStaffRepository.save(userStaff1).getId(),userStaff1.getUser(),userStaff1.getRole(),userStaff1.getRoleType());
  }

  @Override
  public void deleteUserStaff(String id) {

    userStaffRepository.deleteById(id);
  }

}
