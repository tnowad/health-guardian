package com.example.health_guardian_server.dtos.requests.prescription;

import com.example.health_guardian_server.dtos.requests.PageableRequest;
import com.example.health_guardian_server.entities.Prescription;
import com.example.health_guardian_server.entities.enums.PrescriptionStatus;
import com.example.health_guardian_server.specifications.PrescriptionSpecification;
import java.util.Date;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

@Data
public class ListPrescriptionRequest implements PageableRequest<Prescription> {

  private String[] ids;

  private Integer page = 0;

  private Integer size = 10;

  private String[] sortFields = new String[] { "id" };

  private Boolean[] desc = new Boolean[] { false };

  private String userId;

  private String issuedBy;

  private Date validUntil;

  private PrescriptionStatus status;

  private Date startDate;

  private Date endDate;

  @Override
  public Specification<Prescription> toSpecification() {
    return new PrescriptionSpecification(this);
  }

}
