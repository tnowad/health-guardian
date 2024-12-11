package com.example.health_guardian_server.dtos.requests.visit_summary;

import com.example.health_guardian_server.dtos.requests.PageableRequest;
import com.example.health_guardian_server.dtos.requests.PageableWithIdsRequest;
import com.example.health_guardian_server.entities.Appointment;
import com.example.health_guardian_server.entities.VisitSummary;
import com.example.health_guardian_server.entities.enums.AppointmentStatus;
import com.example.health_guardian_server.entities.enums.VisitSummaryType;
import com.example.health_guardian_server.specifications.AppointmentSpecification;
import com.example.health_guardian_server.specifications.VisitSummarySpecification;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

@Data
public class ListVisitSummaryRequest implements PageableRequest<VisitSummary>, PageableWithIdsRequest<String> {

  String[] ids;

  private Integer page = 0;

  private Integer size = 10;

  private String[] sortFields = new String[] {"visitDate"};

  private Boolean[] desc = new Boolean[] {false};

  public String userId;

  public Date visitDate;

  private VisitSummaryType visitType;

  private String summary;

  private String notes;

  @Override
  public Specification<VisitSummary> toSpecification() {
    return new VisitSummarySpecification(this);
  }

  @Override
  public Pageable toPageable() {
    if (sortFields != null && sortFields.length > 0) {
      String sortBy = sortFields[0];
      boolean isDescending = desc != null && desc.length > 0 && desc[0];
      return PageRequest.of(
        page, size, isDescending ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending());
    }
    return PageRequest.of(page, size); // Default page and size if no sorting
  }
}
