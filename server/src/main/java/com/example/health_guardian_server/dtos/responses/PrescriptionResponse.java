package com.example.health_guardian_server.dtos.responses;

import com.example.health_guardian_server.entities.PrescriptionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionResponse {

  private String id;

  private String patientId;

  private String medicationId;

  private String prescribedById;

  private String dosage;

  private String frequency;

  private PrescriptionStatus status;

  private Date startDate;

  private Date endDate;
}
