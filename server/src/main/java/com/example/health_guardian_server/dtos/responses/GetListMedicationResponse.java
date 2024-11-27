package com.example.health_guardian_server.dtos.responses;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetListMedicationResponse {
  List<MedicationResponse> items;
  String message;
}
