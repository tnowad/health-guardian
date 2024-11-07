package com.example.health_guardian_server.services;

import com.example.health_guardian_server.entities.Setting;
import com.example.health_guardian_server.entities.SettingKey;

public interface SettingService {

  Setting getSettingByKey(SettingKey settingKey);
}
