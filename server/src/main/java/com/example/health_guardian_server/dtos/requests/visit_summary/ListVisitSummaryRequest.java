package com.example.health_guardian_server.dtos.requests.visit_summary;

import com.example.health_guardian_server.dtos.requests.PageableRequest;
import com.example.health_guardian_server.dtos.requests.PageableWithIdsRequest;
import com.example.health_guardian_server.entities.VisitSummary;
import com.example.health_guardian_server.entities.enums.VisitSummaryType;
import com.example.health_guardian_server.specifications.VisitSummarySpecification;
import java.util.Date;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class ListVisitSummaryRequest
    implements PageableRequest<VisitSummary>, PageableWithIdsRequest<String> {

  private Integer page;

  private Integer size;

  private String[] sortFields;

  private Boolean[] desc;

  private String[] ids;

  private String userId;

  private Date startDate;

  private Date endDate;

  private VisitSummaryType visitType;

  private String summary;

  private String notes;

  @Override
  public Specification<VisitSummary> toSpecification() {
    return new VisitSummarySpecification(this);
  }
}
