package com.example.health_guardian_server.dtos.requests;

import com.example.health_guardian_server.entities.AppointmentStatus;
import lombok.Data;

import java.util.Date;

@Data
public class UpdateAppointmentRequest {
  private String id;
  private String doctorId;
  private String patientId;
  private Date appointmentDate;
  private String reasonForVisit;
  private AppointmentStatus status;
}
