package com.example.health_guardian_server.dtos.requests;

import com.example.health_guardian_server.entities.ReportedSideEffect;
import com.example.health_guardian_server.specifications.ReportedSideEffectSpecification;
import java.util.Date;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class ListReportedSideEffectsRequest implements PageableRequest<ReportedSideEffect> {

  private Integer page = 0;

  private Integer size = 10;

  private String[] sortFields = new String[] { "id" };

  private Boolean[] desc = new Boolean[] { false };

  private String patientId;

  private String sideEffectId;

  private String prescriptionId;

  private String severity;

  private Date startDate;

  private Date endDate;

  @Override
  public Specification<ReportedSideEffect> toSpecification() {
    return new ReportedSideEffectSpecification(this);
  }
}
