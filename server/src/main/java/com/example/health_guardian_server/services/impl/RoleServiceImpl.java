package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.entities.SettingKey;
import com.example.health_guardian_server.entities.User;
import com.example.health_guardian_server.repositories.RoleRepository;
import com.example.health_guardian_server.services.RoleService;
import com.example.health_guardian_server.services.SettingService;
import com.example.health_guardian_server.services.UserService;
import java.util.Set;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j // Enable logging for the class
public class RoleServiceImpl implements RoleService {

  RoleRepository roleRepository;
  SettingService settingService;
  UserService userService;

  @Override
  public Set<String> getRoleIdsByUserId(String userId) {
    log.debug("Fetching role IDs for user ID: {}", userId);
    Set<String> roleIds = roleRepository.findRoleIdsByUserId(userId);
    log.debug("Role IDs fetched for user ID {}: {}", userId, roleIds);
    return roleIds;
  }

  @Override
  public Set<String> getDefaultRoleIds() {
    log.debug("Fetching default role IDs");
    var setting = settingService.getSettingByKey(SettingKey.ROLE_DEFAULT_IDS);
    Set<String> defaultRoleIds = Set.copyOf(setting.getStringArrayValue());
    log.info("Default role IDs fetched: {}", defaultRoleIds);
    return defaultRoleIds;
  }

  @Override
  public Set<String> getDefaultRoleIdsForPatient() {
    log.debug("Fetching default role IDs for patient");
    var setting = settingService.getSettingByKey(SettingKey.ROLE_DEFAULT_IDS);
    Set<String> defaultRoleIdsForPatient = Set.copyOf(setting.getStringArrayValue());
    log.info("Default role IDs for patient fetched: {}", defaultRoleIdsForPatient);
    return defaultRoleIdsForPatient;
  }

  @Override
  public void assignRolesToUser(String userId, Set<String> roleIds) {
    log.debug("Assigning roles to user with ID: {}", userId);
    log.debug("Roles to be assigned: {}", roleIds);
    User user = userService.getUserById(userId);

    if (user == null) {
      log.error("User with ID {} not found", userId);
      throw new RuntimeException("User not found");
    }

    user.setRoles(roleRepository.findAllByIds(roleIds));
    userService.saveUser(user);
    log.info("Roles {} successfully assigned to user with ID: {}", roleIds, userId);
  }
}
