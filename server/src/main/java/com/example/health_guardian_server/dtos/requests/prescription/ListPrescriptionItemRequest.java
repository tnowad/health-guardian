package com.example.health_guardian_server.dtos.requests.prescription;

import com.example.health_guardian_server.entities.enums.PrescriptionItemStatus;
import java.util.Date;
import lombok.Data;

@Data
public class ListPrescriptionItemRequest {
  private String prescriptionId;
  private String medicationName;
  private PrescriptionItemStatus status;
  private String frequency;
  private Date startDate;
  private Date endDate;
  private String dosage;
}
