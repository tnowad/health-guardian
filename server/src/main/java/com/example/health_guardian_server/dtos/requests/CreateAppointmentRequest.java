package com.example.health_guardian_server.dtos.requests;

import com.example.health_guardian_server.entities.enums.AppointmentStatus;
import java.util.Date;
import lombok.Data;

@Data
public class CreateAppointmentRequest {
  private String userId;
  private Date appointmentDate;
  private String reason;
  private String address;
  private AppointmentStatus status;
  private String notes;
}
