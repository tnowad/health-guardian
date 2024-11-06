package com.example.health_guardian_server.repositories;

import com.example.health_guardian_server.entities.PatientLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientLogRepository extends JpaRepository<PatientLog, String> {}
