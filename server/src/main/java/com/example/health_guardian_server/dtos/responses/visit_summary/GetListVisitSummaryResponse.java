package com.example.health_guardian_server.dtos.responses.visit_summary;

import com.example.health_guardian_server.dtos.responses.appointment.AppointmentResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetListVisitSummaryResponse {
  List<VisitSummaryResponse> items;
  String message;
}
