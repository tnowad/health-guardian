package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.entities.Setting;
import com.example.health_guardian_server.entities.SettingKey;
import com.example.health_guardian_server.repositories.SettingRepository;
import com.example.health_guardian_server.services.SettingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SettingServiceImpl implements SettingService {

  SettingRepository settingRepository;

  @Override
  public Setting getSettingByKey(SettingKey settingKey) {
    return settingRepository.findByKey(settingKey).orElse(null);
  }
}
