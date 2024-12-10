package com.example.health_guardian_server.repositories;

import com.example.health_guardian_server.entities.FamilyHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamilyHistoryRepository extends JpaRepository<FamilyHistory, String> {
}
