package com.example.health_guardian_server.listeners;

import java.time.LocalDateTime;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.health_guardian_server.entities.AbstractEntity;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class AuditEntityListener {

  @PrePersist
  public void setCreatedByAndCreatedDate(AbstractEntity entity) {
    entity.setCreatedAt(LocalDateTime.now());
    entity.setCreatedBy(getCurrentUser());
  }

  @PreUpdate
  public void setUpdatedByAndUpdatedDate(AbstractEntity entity) {
    entity.setUpdatedAt(LocalDateTime.now());
    entity.setUpdatedBy(getCurrentUser());
  }

  private String getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication != null ? authentication.getName() : "systemUser";
  }
}
