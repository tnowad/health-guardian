package com.example.health_guardian_server.dtos.responses;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class MedicalStaffInforResponse {
  private String id;
  private String specialization;
}
