package com.example.health_guardian_server.repositories;

import com.example.health_guardian_server.entities.Prescription;
import com.example.health_guardian_server.entities.enums.PrescriptionStatus;
import jakarta.validation.constraints.Future;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionRepository
    extends JpaRepository<Prescription, String>, JpaSpecificationExecutor<Prescription> {

}
