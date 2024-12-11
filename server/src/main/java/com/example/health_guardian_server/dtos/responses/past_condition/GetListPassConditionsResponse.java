package com.example.health_guardian_server.dtos.responses.past_condition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetListPassConditionsResponse {
  private List<PastConditionResponse> passConditions;
  private String message;
}
