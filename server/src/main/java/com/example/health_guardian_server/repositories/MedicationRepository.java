package com.example.health_guardian_server.repositories;

import com.example.health_guardian_server.entities.Medication;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationRepository
    extends JpaRepository<Medication, String>, JpaSpecificationExecutor<Medication> {
  List<Medication> findByName(String name);
}
