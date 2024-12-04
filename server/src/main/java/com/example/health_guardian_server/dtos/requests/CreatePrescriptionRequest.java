package com.example.health_guardian_server.dtos.requests;

import com.example.health_guardian_server.entities.PrescriptionStatus;
import lombok.Data;

import java.util.Date;

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
