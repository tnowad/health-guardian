package com.example.health_guardian_server.services.impl;

import org.springframework.stereotype.Service;

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

@Service
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
    var roles = roleService.getRolesByUserId(user.getId());
    var permissions = permissionService.getPermissionsByRoles(roles);
    var tokens = tokenService.generateTokens(user, roles, permissions);

    return SignInResponse.builder().tokens(tokens).message("Sign in successfully").build();
  }

}
