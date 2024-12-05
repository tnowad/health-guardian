package com.example.health_guardian_server.dtos.responses;

import com.example.health_guardian_server.entities.SideEffectSeverity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SideEffectResponse {
  private String id;
  private String name;
  private SideEffectSeverity severity;
  private String description;
}
