package com.example.health_guardian_server.repositories;

import com.example.health_guardian_server.entities.Prescription;
import com.example.health_guardian_server.entities.PrescriptionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface PrescriptionItemRepository
    extends JpaRepository<PrescriptionItem, String>, JpaSpecificationExecutor<PrescriptionItem> {
  List<PrescriptionItem> findAllByPrescription(Prescription prescription);
}
