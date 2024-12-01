package com.example.health_guardian_server.controllers;

import com.example.health_guardian_server.dtos.requests.CreateUserStaffRequest;
import com.example.health_guardian_server.dtos.responses.GetListUserStaffResponse;
import com.example.health_guardian_server.dtos.responses.UserStaffResponse;
import com.example.health_guardian_server.entities.UserStaff;
import com.example.health_guardian_server.services.UserStaffService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userstaff")
public class UserStaffController {

  private final UserStaffService userStaffService;

  public UserStaffController(UserStaffService userStaffService) {
    this.userStaffService = userStaffService;
  }

  // Define methods

  @GetMapping("/all")
  public ResponseEntity<Page<UserStaffResponse>> getAllUserStaffs(@RequestParam (defaultValue = "0") int page, @RequestParam (defaultValue = "10") int size) {
    Page<UserStaffResponse> userStaffs = userStaffService.getAllUserStaffs(page, size);

    return new ResponseEntity<>(userStaffs, HttpStatus.OK);
  }


  @GetMapping("/id/{id}")
  public ResponseEntity<UserStaffResponse> getUserStaffById(String id) {
    UserStaffResponse userStaff = userStaffService.getUserStaffById(id);
    return new ResponseEntity<>(userStaff, HttpStatus.OK);
  }


  @PostMapping("/create")
  public ResponseEntity<UserStaffResponse> createUserStaff(@RequestBody CreateUserStaffRequest userStaff) {
    UserStaffResponse createdUserStaff = userStaffService.createUserStaff(userStaff);
    return new ResponseEntity<>(createdUserStaff, HttpStatus.CREATED);
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<UserStaffResponse> updateUserStaff(String id, UserStaffResponse userStaff) {
    UserStaffResponse updatedUserStaff = userStaffService.updateUserStaff(id, userStaff);
    return new ResponseEntity<>(updatedUserStaff, HttpStatus.OK);
  }


  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> deleteUserStaff(String id) {
    userStaffService.deleteUserStaff(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
