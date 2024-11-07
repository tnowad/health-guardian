package com.example.health_guardian_server.repositories;

import com.example.health_guardian_server.entities.Setting;
import com.example.health_guardian_server.entities.SettingKey;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingRepository extends JpaRepository<Setting, String> {
  Optional<Setting> findByKey(SettingKey key);
}
