package com.example.health_guardian_server.repositories;

import com.example.health_guardian_server.entities.Allergy;
import com.example.health_guardian_server.entities.DiagnosticReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DiagnosticReportRepository extends JpaRepository<DiagnosticReport, String>, JpaSpecificationExecutor<DiagnosticReport> {
}
