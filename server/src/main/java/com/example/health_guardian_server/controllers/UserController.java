package com.example.health_guardian_server.controllers;

import static com.example.health_guardian_server.exceptions.file.FileErrorCode.CAN_NOT_STORE_FILE;
import static com.example.health_guardian_server.utils.Utils.convertMultipartFileToFile;
import static com.example.health_guardian_server.utils.Utils.generateFileName;

import com.example.health_guardian_server.dtos.requests.user.CreateUserRequest;
import com.example.health_guardian_server.dtos.requests.user.ListUsersRequest;
import com.example.health_guardian_server.dtos.requests.user.UpdateUserRequest;
import com.example.health_guardian_server.dtos.responses.user.CurrentUserInfomationResponse;
import com.example.health_guardian_server.dtos.responses.user.UserResponse;
import com.example.health_guardian_server.exceptions.file.FileException;
import com.example.health_guardian_server.services.MinioClientService;
import com.example.health_guardian_server.services.UserService;
import java.io.File;
import java.nio.file.Files;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserController {
  UserService userService;
  MinioClientService minioClientService;

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

  @PostMapping("/upload-avatar")
  public ResponseEntity<UserResponse> uploadAvatar(@RequestPart("avatar") MultipartFile avatar) {
    String userId = SecurityContextHolder.getContext().getAuthentication().getName();
    String contentType = avatar.getContentType();
    long size = avatar.getSize();
    String fileName = generateFileName(contentType.split("/")[0], contentType.split("/")[1]);
    try {
      File file = convertMultipartFileToFile(avatar, fileName);
      minioClientService.storeObject(file, fileName, contentType, "user-avatars");
      Files.delete(file.toPath());
      log.info("File uploaded successfully: {}", file.toPath());
    } catch (Exception e) {
      throw new FileException(CAN_NOT_STORE_FILE, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    return ResponseEntity.ok(
        userService.updateUser(userId, UpdateUserRequest.builder().avatar(fileName).build()));
  }
}
