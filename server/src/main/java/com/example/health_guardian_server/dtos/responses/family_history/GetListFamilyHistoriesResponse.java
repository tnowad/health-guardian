package com.example.health_guardian_server.dtos.responses.family_history;

import com.example.health_guardian_server.dtos.responses.appointment.AppointmentResponse;
import com.example.health_guardian_server.entities.FamilyHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetListFamilyHistoriesResponse {
  List<FamilyHistoryResponse> items;
  String message;
}
