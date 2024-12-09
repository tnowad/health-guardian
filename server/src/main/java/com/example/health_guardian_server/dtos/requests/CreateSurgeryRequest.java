package com.example.health_guardian_server.dtos.requests;

import lombok.Data;

import java.util.Date;

@Data
public class CreateSurgeryRequest {
  private String userId;
  private Date date;
  private String description;
  private String notes;
}
