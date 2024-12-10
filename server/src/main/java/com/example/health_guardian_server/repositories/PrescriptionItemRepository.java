package com.example.health_guardian_server.repositories;

import com.example.health_guardian_server.entities.LocalProvider;
import com.example.health_guardian_server.entities.PrescriptionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionItemRepository extends JpaRepository<PrescriptionItem, String>, JpaSpecificationExecutor<PrescriptionItem> {
}
