package com.example.health_guardian_server.repositories;

import com.example.health_guardian_server.entities.ExternalProvider;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExternalProviderRepository extends JpaRepository<ExternalProvider, String> {
  ExternalProvider findByProviderUserId(String id);

  Optional<ExternalProvider> findByProviderNameAndProviderUserId(
      String provider, String providerUserId);
}
