package com.example.health_guardian_server.dtos.responses;

import com.example.health_guardian_server.entities.AppointmentStatus;
import com.example.health_guardian_server.entities.Patient;
import com.example.health_guardian_server.entities.UserMedicalStaff;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponse {
  private String id;
  private Patient patient;
  private UserMedicalStaff doctor;
  private Date appointmentDate;
  private String reasonForVisit;
  private AppointmentStatus status;
}
