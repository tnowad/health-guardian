package com.example.health_guardian_server.dtos.responses.prescription;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrescriptionItemResponse {

  private String id;
  private String dosage;
  private String medicationName;
  private String image;
  private String frequency;
  private Date startDate;
  private Date endDate;
  private String status;
}
