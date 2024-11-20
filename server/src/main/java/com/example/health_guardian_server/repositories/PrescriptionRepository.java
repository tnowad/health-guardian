package com.example.health_guardian_server.repositories;

import com.example.health_guardian_server.entities.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, String> {
  List<Prescription> findByPatientId(String patientId);

  List<Prescription> findByDoctorId(String doctorId);

  List<Prescription> findByMedicationId(String medicationId);

  List<Prescription> findByStatus(String status);

  List<Prescription> findByPatientIdAndDoctorId(String patientId, String doctorId);

  List<Prescription> findByPatientIdAndMedicationId(String patientId, String medicationId);

  List<Prescription> findByPatientIdAndDate(String patientId, String date);

  List<Prescription> findByPatientIdAndStatus(String patientId, String status);
}
