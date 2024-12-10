package com.example.health_guardian_server.repositories;

import com.example.health_guardian_server.entities.DiagnosticReport;
import com.example.health_guardian_server.entities.DiagnosticResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DiagnosticResultRepository extends JpaRepository<DiagnosticResult, String>, JpaSpecificationExecutor<DiagnosticResult> {
}
