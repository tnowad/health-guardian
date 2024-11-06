package com.example.health_guardian_server.controllers;

import static org.springframework.http.HttpStatus.OK;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.health_guardian_server.dtos.responses.AccountInfoResponse;
import com.example.health_guardian_server.dtos.responses.FullAccountInfoResponse;
import com.example.health_guardian_server.dtos.responses.PermissionResponse;
import com.example.health_guardian_server.dtos.responses.RoleResponse;
import com.example.health_guardian_server.entities.Role;
import com.example.health_guardian_server.mappers.AccountMapper;
import com.example.health_guardian_server.mappers.PermissionMapper;
import com.example.health_guardian_server.mappers.RoleMapper;
import com.example.health_guardian_server.services.AccountService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "Account Apis")
public class AccountController {
  AccountService accountService;
  AccountMapper accountMapper;
  RoleMapper roleMapper;
  PermissionMapper permissionMapper;

  @GetMapping("/find-all")
  public ResponseEntity<List<FullAccountInfoResponse>> getAll() {
    List<FullAccountInfoResponse> result = accountService.findAll().stream()
        .map(account -> {
          AccountInfoResponse accountInfoResponse = accountMapper.toAccountInfoResponse(account);

          List<RoleResponse> roles = account.getRoles().stream().map(roleMapper::toRoleResponse).toList();
          List<PermissionResponse> permissions = account.getRoles().stream()
              .map(Role::getPermissions) // Extract the set of permissions from each role
              .flatMap(List::stream) // Flatten the permissions to a single stream
              .map(permissionMapper::toPermissionResponse) // Convert to PermissionResponse
              .distinct() // Optional: to avoid duplicate permissions
              .toList(); // Create the wrapper object
          return new FullAccountInfoResponse(accountInfoResponse, roles, permissions);
        })
        .toList();
    return ResponseEntity.status(OK)
        .body(result);
  }

}
