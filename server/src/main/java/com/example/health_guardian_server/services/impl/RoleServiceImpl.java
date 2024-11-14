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
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RoleServiceImpl implements RoleService {

  RoleRepository roleRepository;
  SettingService settingService;
  UserService userService;

  @Override
  public Set<String> getRoleIdsByUserId(String userId) {
    return roleRepository.findRoleIdsByUserId(userId);
  }

  @Override
  public Set<String> getDefaultRoleIds() {
    var setting = settingService.getSettingByKey(SettingKey.ROLE_DEFAULT_IDS);
    return Set.copyOf(setting.getStringArrayValue());
  }

  @Override
  public Set<String> getDefaultRoleIdsForPatient() {
    var setting = settingService.getSettingByKey(SettingKey.ROLE_DEFAULT_IDS_FOR_PATIENT);
    return Set.copyOf(setting.getStringArrayValue());
  }

  @Override
  public void assignRolesToUser(String userId, Set<String> roleIds) {
    User user = userService.getUserById(userId);
    if (user == null) {
      throw new RuntimeException("User not found");
    }

    user.setRoles(roleRepository.findAllByIds(roleIds));
    userService.saveUser(user);
  }
}
