package com.example.health_guardian_server.dtos.responses.family_history;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FamilyHistoryResponse {
  private String id;
  private String userId;
  private String relation;
  private String condition;
  private String description;
}
