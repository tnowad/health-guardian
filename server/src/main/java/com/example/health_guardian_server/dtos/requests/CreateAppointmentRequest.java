package com.example.health_guardian_server.dtos.requests;

import com.example.health_guardian_server.entities.AppointmentStatus;
import java.util.Date;
import lombok.Data;

@Data
public class CreateAppointmentRequest {
  private String doctorId;
  private String patientId;
  private Date appointmentDate;
  private String reasonForVisit;
  private AppointmentStatus status;
}
