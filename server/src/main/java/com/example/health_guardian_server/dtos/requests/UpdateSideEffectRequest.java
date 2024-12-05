package com.example.health_guardian_server.dtos.requests;

import com.example.health_guardian_server.entities.SideEffectSeverity;
import lombok.Data;

@Data
public class UpdateSideEffectRequest {
  private String id;
  private String name;
  private SideEffectSeverity severity;
  private String description;
}
