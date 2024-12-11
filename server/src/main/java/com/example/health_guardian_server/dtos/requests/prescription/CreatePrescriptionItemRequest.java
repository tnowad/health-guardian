
package com.example.health_guardian_server.dtos.requests.prescription;

import java.util.Date;
import com.example.health_guardian_server.entities.enums.PrescriptionItemStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Data
@Builder
public class CreatePrescriptionItemRequest {

  @NotBlank(message = "Prescription ID cannot be blank")
  private String prescriptionId;

  @NotBlank(message = "Dosage cannot be blank")
  private String dosage;

  @NotBlank(message = "Medication name cannot be blank")
  private String medicationName;

  @Pattern(regexp = "(http|https):\\/\\/.*", message = "Image must be a valid URL")
  private String image;

  @NotBlank(message = "Frequency cannot be blank")
  private String frequency;

  @NotNull(message = "Start date cannot be null")
  @FutureOrPresent(message = "Start date must be today or in the future")
  private Date startDate;

  @NotNull(message = "End date cannot be null")
  @Future(message = "End date must be in the future")
  private Date endDate;

  @NotNull(message = "Status cannot be null")
  private PrescriptionItemStatus status;
}
