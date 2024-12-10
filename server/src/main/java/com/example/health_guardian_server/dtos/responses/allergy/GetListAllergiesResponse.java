package com.example.health_guardian_server.dtos.responses.allergy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetListAllergiesResponse {
  private List<AllergyResponse> allergies;
  private String message;
}
