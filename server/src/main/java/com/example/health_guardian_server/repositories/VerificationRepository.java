package com.example.health_guardian_server.repositories;

import com.example.health_guardian_server.dtos.enums.VerificationType;
import com.example.health_guardian_server.entities.Allergy;
import com.example.health_guardian_server.entities.LocalProvider;
import com.example.health_guardian_server.entities.Verification;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationRepository extends JpaRepository<Verification, String>, JpaSpecificationExecutor<Verification> {
  List<Verification> findByLocalProviderAndVerificationType(
      LocalProvider localProvider, VerificationType type);

  Optional<Verification> findByCode(String code);
}
