package com.example.health_guardian_server.controllers;

import com.example.health_guardian_server.dtos.requests.ListUsersRequest;
import com.example.health_guardian_server.dtos.responses.UserResponse;
import com.example.health_guardian_server.services.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
}
