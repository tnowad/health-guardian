package com.example.health_guardian_server.dtos.responses.immunization;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetListImmunizationsResponse {
  private List<ImmunizationResponse> immunizations;
  private String message;
}
