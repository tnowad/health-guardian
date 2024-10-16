package com.example.health_guardian_server.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.health_guardian_server.dtos.responses.CommonResponse;
import com.example.health_guardian_server.dtos.responses.ProfileResponse;
import com.example.health_guardian_server.entities.Profile;
import com.example.health_guardian_server.mappers.ProfileMapper;
import com.example.health_guardian_server.services.ProfileService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "Profile Apis")
public class ProfileController {

  ProfileService profileService;
  ProfileMapper profileMapper;

  @GetMapping("/find-all")
  public ResponseEntity<CommonResponse<List<Profile>>> getAll() {
    return ResponseEntity.status(HttpStatus.OK)
        .body(CommonResponse.<List<Profile>>builder().errorCode("1231").message("oke").result(profileService.findAll())
            .build());
  }

  @GetMapping("/get-my-all-profiles")
  public ResponseEntity<CommonResponse<List<Profile>>> getMyAllProfiles() {
    return ResponseEntity.status(HttpStatus.OK)
        .body(CommonResponse.<List<Profile>>builder().errorCode("1231").message("oke").result(profileService.findAll())
            .build());
  }

  @PostMapping("/create")
  public ResponseEntity<CommonResponse<Profile>> create(Profile profile) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(CommonResponse.<Profile>builder().errorCode("1231").message("oke").result(profileService.create(profile))
            .build());
  }

}
