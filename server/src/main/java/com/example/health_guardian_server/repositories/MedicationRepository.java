package com.example.health_guardian_server.repositories;

import com.example.health_guardian_server.entities.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, String> {
  List<Medication> findByName(String name);
}
