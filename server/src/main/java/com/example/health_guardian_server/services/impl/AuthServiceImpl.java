package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.SignInRequest;
import com.example.health_guardian_server.dtos.responses.SignInResponse;
import com.example.health_guardian_server.entities.AccountStatus;
import com.example.health_guardian_server.services.AccountService;
import com.example.health_guardian_server.services.AuthService;
import com.example.health_guardian_server.services.LocalProviderService;
import com.example.health_guardian_server.services.PermissionService;
import com.example.health_guardian_server.services.RoleService;
import com.example.health_guardian_server.services.TokenService;
import com.example.health_guardian_server.services.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthServiceImpl implements AuthService {

  LocalProviderService localProviderService;
  AccountService accountService;
  RoleService roleService;
  UserService userService;
  PermissionService permissionService;
  TokenService tokenService;

  @Override
  public SignInResponse signIn(SignInRequest request) {
    var localProvider = localProviderService.getLocalProviderByEmail(request.getEmail());
    if (localProvider == null) {
      throw new RuntimeException("User not found");
    }
    if (localProviderService.verifyLocalProviderPassword(
        request.getEmail(), request.getPassword())) {
      throw new RuntimeException("Invalid password");
    }

    var status = accountService.checkAccountStatus(localProvider.getAccountId());
    if (status == AccountStatus.DELETED) {
      throw new RuntimeException("Account deleted");
    } else if (status == AccountStatus.INACTIVE) {
      throw new RuntimeException("Account inactive");
    }
    var user = userService.getUserByAccountId(localProvider.getAccountId());
    var roleIds = roleService.getRoleIdsByUserId(user.getId());
    var permissions = permissionService.getPermissionIdsByRoleIds(roleIds);
    var tokens = tokenService.generateTokens(user, permissions);

    return SignInResponse.builder().tokens(tokens).message("Sign in successfully").build();
  }
}
