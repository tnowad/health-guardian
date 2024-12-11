package com.example.health_guardian_server.dtos.requests.prescription;

import com.example.health_guardian_server.entities.enums.PrescriptionStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Date;
import lombok.Data;

@Data
public class CreatePrescriptionRequest {

  @NotNull(message = "User ID cannot be null")
  private String userId;

  @NotNull(message = "Issued By cannot be null")
  @Size(min = 3, max = 100, message = "Issued By must be between 3 and 100 characters")
  private String issuedBy;

  @NotNull(message = "Issued Date cannot be null")
  @Past(message = "Issued Date must be a past date")
  private Timestamp issuedDate;

  @NotNull(message = "Valid Until cannot be null")
  @Future(message = "Valid Until must be a future date")
  private Date validUntil;

  @NotNull(message = "Prescription Status cannot be null")
  private PrescriptionStatus status;
}
