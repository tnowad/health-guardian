package com.example.health_guardian_server.repositories;

import com.example.health_guardian_server.entities.Prescription;
import com.example.health_guardian_server.entities.VisitSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitSummaryRepository extends JpaRepository<VisitSummary, String>, JpaSpecificationExecutor<VisitSummary> {
}
