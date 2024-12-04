package com.example.health_guardian_server.controllers;

import com.example.health_guardian_server.dtos.requests.CreateUserMedicalStaffRequest;
import com.example.health_guardian_server.dtos.requests.ListUserMedicalStaffRequest;
import com.example.health_guardian_server.dtos.responses.UserMedicalStaffResponse;
import com.example.health_guardian_server.services.UserMedicalStaffService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-medical-staffs")
public class UserMedicalStaffController {
  // Define methods
  private final UserMedicalStaffService userMedicalStaffService;

  public UserMedicalStaffController(UserMedicalStaffService userMedicalStaffService) {
    this.userMedicalStaffService = userMedicalStaffService;
  }

  // Define methods

  @GetMapping
  public ResponseEntity<Page<UserMedicalStaffResponse>> getAllUserMedicalStaffs(
      @ModelAttribute ListUserMedicalStaffRequest request) {
    Page<UserMedicalStaffResponse> userMedicalStaffs = userMedicalStaffService.getAllUserMedicalStaffs(request);

    return new ResponseEntity<>(userMedicalStaffs, HttpStatus.OK);
  }

  @GetMapping("/id/{id}")
  public ResponseEntity<UserMedicalStaffResponse> getUserMedicalStaffById(String id) {
    UserMedicalStaffResponse userMedicalStaff = userMedicalStaffService.getUserMedicalStaffById(id);
    return new ResponseEntity<>(userMedicalStaff, HttpStatus.OK);
  }

  @PostMapping("/create")

  public ResponseEntity<UserMedicalStaffResponse> createUserMedicalStaff(@RequestBody CreateUserMedicalStaffRequest userMedicalStaff) {
    UserMedicalStaffResponse createdUserMedicalStaff = userMedicalStaffService.createUserMedicalStaff(userMedicalStaff);
    return new ResponseEntity<>(createdUserMedicalStaff, HttpStatus.CREATED);
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<UserMedicalStaffResponse> updateUserMedicalStaff(String id, UserMedicalStaffResponse userMedicalStaff) {
    UserMedicalStaffResponse updatedUserMedicalStaff = userMedicalStaffService.updateUserMedicalStaff(id, userMedicalStaff);
    return new ResponseEntity<>(updatedUserMedicalStaff, HttpStatus.OK);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> deleteUserMedicalStaff(String id) {
    userMedicalStaffService.deleteUserMedicalStaff(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
