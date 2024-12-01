package com.example.health_guardian_server.repositories;

import com.example.health_guardian_server.entities.Prescription;
import com.example.health_guardian_server.entities.PrescriptionStatus;
import jakarta.validation.constraints.Future;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, String> {
  List<Prescription> findByPatientId(String patientId);

  List<Prescription> findByMedicationId(String medicationId);

  List<Prescription> findByStatus(PrescriptionStatus status);

  List<Prescription> findByPatientIdAndMedicationId(String patientId, String medicationId);

  List<Prescription> findByPatientIdAndEndDate(String patient_id, @Future Date endDate);

  List<Prescription> findByPatientIdAndStatus(String patient_id, PrescriptionStatus status);
}
