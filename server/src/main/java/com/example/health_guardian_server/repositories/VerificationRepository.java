package com.example.health_guardian_server.repositories;

import com.example.health_guardian_server.dtos.enums.VerificationType;
import com.example.health_guardian_server.entities.LocalProvider;
import com.example.health_guardian_server.entities.Verification;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationRepository extends JpaRepository<Verification, String> {
  Verification findByEmailAndType(String email, VerificationType type);

  List<Verification> findByLocalProviderAndVerificationType(
      LocalProvider localProvider, VerificationType type);
}
