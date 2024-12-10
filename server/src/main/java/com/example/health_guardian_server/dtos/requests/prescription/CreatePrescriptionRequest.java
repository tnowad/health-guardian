package com.example.health_guardian_server.dtos.requests.prescription;

import com.example.health_guardian_server.entities.enums.PrescriptionStatus;
import java.util.Date;
import lombok.Data;

@Data
public class CreatePrescriptionRequest {
  private String patientId;

  private String medicationId;

  private String prescribedBy;

  private String dosage;

  private String frequency;

  private PrescriptionStatus status;

  private Date startDate;

  private Date endDate;
}
