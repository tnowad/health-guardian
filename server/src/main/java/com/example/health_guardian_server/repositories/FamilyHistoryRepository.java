package com.example.health_guardian_server.repositories;

import com.example.health_guardian_server.entities.Allergy;
import com.example.health_guardian_server.entities.FamilyHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyHistoryRepository extends JpaRepository<FamilyHistory, String>, JpaSpecificationExecutor<FamilyHistory> {
}
