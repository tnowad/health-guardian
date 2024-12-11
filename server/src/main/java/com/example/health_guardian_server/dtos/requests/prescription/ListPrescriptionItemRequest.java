package com.example.health_guardian_server.dtos.requests.prescription;

import com.example.health_guardian_server.dtos.requests.PageableRequest;
import com.example.health_guardian_server.dtos.requests.PageableWithIdsRequest;
import com.example.health_guardian_server.entities.enums.PrescriptionItemStatus;
import com.example.health_guardian_server.entities.PrescriptionItem;
import com.example.health_guardian_server.specifications.PrescriptionItemSpecification;

import java.util.Date;

import org.springframework.data.jpa.domain.Specification;

import lombok.Data;

@Data
public class ListPrescriptionItemRequest implements PageableRequest<PrescriptionItem> {
  private String[] ids;
  private Integer page = 0;
  private Integer size = 10;
  private String[] sortFields = new String[] { "status" };
  private Boolean[] desc = new Boolean[] { true };

  private String prescriptionId;
  private String medicationName;
  private PrescriptionItemStatus status;
  private String frequency;
  private Date startDate;
  private Date endDate;
  private String dosage;

  @Override
  public Specification<PrescriptionItem> toSpecification() {
    return new PrescriptionItemSpecification(this);
  }
}
