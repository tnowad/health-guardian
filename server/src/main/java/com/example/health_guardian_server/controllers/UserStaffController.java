package com.example.health_guardian_server.controllers;

import com.example.health_guardian_server.dtos.requests.CreateUserStaffRequest;
import com.example.health_guardian_server.dtos.requests.ListUserStaffRequest;
import com.example.health_guardian_server.dtos.responses.GetListUserStaffResponse;
import com.example.health_guardian_server.dtos.responses.UserStaffResponse;
import com.example.health_guardian_server.entities.UserStaff;
import com.example.health_guardian_server.mappers.UserStaffMapper;
import com.example.health_guardian_server.services.UserStaffService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userstaff")
@RequiredArgsConstructor
public class UserStaffController {

  private final UserStaffService userStaffService;
  private final UserStaffMapper userStaffMapper;

  @GetMapping("/all")
  public ResponseEntity<Page<UserStaffResponse>> getAllUserStaffs(@ModelAttribute ListUserStaffRequest request) {
    Page<UserStaffResponse> userStaffs = userStaffService.getAllUserStaffs(request);
    return new ResponseEntity<>(userStaffs, HttpStatus.OK);
  }

  @GetMapping("/id/{id}")
  public ResponseEntity<UserStaffResponse> getUserStaffById(@PathVariable String id) {

  }

  @PostMapping("/create")
  public ResponseEntity<UserStaffResponse> createUserStaff(@RequestBody CreateUserStaffRequest userStaff) {
    UserStaffResponse createdUserStaff = userStaffService.createUserStaff(userStaff);
    return new ResponseEntity<>(createdUserStaff, HttpStatus.CREATED);
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<UserStaffResponse> updateUserStaff(@PathVariable String id, @RequestBody UserStaffResponse userStaff) {
    UserStaffResponse updatedUserStaff = userStaffService.updateUserStaff(id, userStaff);
    return new ResponseEntity<>(updatedUserStaff, HttpStatus.OK);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> deleteUserStaff(@PathVariable String id) {
    userStaffService.deleteUserStaff(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}


