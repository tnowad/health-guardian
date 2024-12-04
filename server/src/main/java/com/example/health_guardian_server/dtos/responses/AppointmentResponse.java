package com.example.health_guardian_server.dtos.responses;

import com.example.health_guardian_server.entities.AppointmentStatus;
import java.util.Date;
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
  private String doctorId;
  private String patientId;
  private Date appointmentDate;
  private String reasonForVisit;
  private AppointmentStatus status;
}
