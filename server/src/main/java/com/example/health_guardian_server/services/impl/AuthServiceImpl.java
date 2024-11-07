package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.RefreshTokenRequest;
import com.example.health_guardian_server.dtos.requests.SignInRequest;
import com.example.health_guardian_server.dtos.responses.GetCurrentUserPermissionsResponse;
import com.example.health_guardian_server.dtos.responses.RefreshTokenResponse;
import com.example.health_guardian_server.dtos.responses.SignInResponse;
import com.example.health_guardian_server.entities.AccountStatus;
import com.example.health_guardian_server.services.AccountService;
import com.example.health_guardian_server.services.AuthService;
import com.example.health_guardian_server.services.LocalProviderService;
import com.example.health_guardian_server.services.PermissionService;
import com.example.health_guardian_server.services.RoleService;
import com.example.health_guardian_server.services.TokenService;
import com.example.health_guardian_server.services.UserService;
import java.util.HashSet;
import java.util.Set;
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
    System.out.println(
        localProviderService.verifyLocalProviderPassword(
            request.getEmail(), request.getPassword()));
    if (!localProviderService.verifyLocalProviderPassword(
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
    var permissionNames = permissionService.getPermissionNamesByRoleIds(roleIds);
    var tokens = tokenService.generateTokens(user, permissionNames);

    return SignInResponse.builder().tokens(tokens).message("Sign in successfully").build();
  }

  @Override
  public RefreshTokenResponse refresh(RefreshTokenRequest request) {
    var tokens = tokenService.refreshTokens(request.getRefreshToken());
    return RefreshTokenResponse.builder()
        .tokens(tokens)
        .message("Refresh token successfully")
        .build();
  }

  @Override
  public GetCurrentUserPermissionsResponse getCurrentUserPermissions(String accessToken) {
    Set<String> permissionNames = new HashSet<>();
    if (accessToken != null) {
      var roleIds = roleService.getDefaultRoleIds();
      permissionNames = permissionService.getPermissionNamesByRoleIds(roleIds);
    } else {
      permissionNames = tokenService.extractPermissionNames(accessToken);
    }
    return GetCurrentUserPermissionsResponse.builder()
        .items(permissionNames)
        .message("Get current user permissions successfully")
        .build();
  }
}
