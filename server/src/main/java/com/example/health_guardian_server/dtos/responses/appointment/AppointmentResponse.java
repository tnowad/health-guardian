package com.example.health_guardian_server.dtos.responses.appointment;

import java.util.Date;

import com.example.health_guardian_server.entities.enums.AppointmentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponse {
  private String id;
  private String userId;
  private Date appointmentDate;
  private String reason;
  private String address;
  private AppointmentStatus status;
  private String notes;
}
