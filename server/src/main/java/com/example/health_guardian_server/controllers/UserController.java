package com.example.health_guardian_server.controllers;

import com.example.health_guardian_server.dtos.requests.CreateUserRequest;
import com.example.health_guardian_server.dtos.requests.ListUsersRequest;
import com.example.health_guardian_server.dtos.requests.UpdateUserRequest;
import com.example.health_guardian_server.dtos.responses.CurrentUserInfomationResponse;
import com.example.health_guardian_server.dtos.responses.UserResponse;
import com.example.health_guardian_server.services.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserController {
  UserService userService;

  @GetMapping
  public ResponseEntity<Page<UserResponse>> listUsers(@ModelAttribute ListUsersRequest request) {
    Page<UserResponse> response = userService.listUsers(request);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/current-user/information")
  public ResponseEntity<CurrentUserInfomationResponse> getCurrentUserInformation() {
    CurrentUserInfomationResponse response = userService.getCurrentUserInformation();
    return ResponseEntity.ok(response);
  }

  @PostMapping
  public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest request) {
    UserResponse response = userService.createUser(request);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{userId}")
  public ResponseEntity<UserResponse> getUser(@PathVariable String userId) {
    UserResponse response = userService.getUser(userId);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{userId}")
  public ResponseEntity<UserResponse> updateUser(
      @PathVariable String userId, @RequestBody UpdateUserRequest request) {
    UserResponse response = userService.updateUser(userId, request);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
    userService.deleteUser(userId);
    return ResponseEntity.noContent().build();
  }
}
