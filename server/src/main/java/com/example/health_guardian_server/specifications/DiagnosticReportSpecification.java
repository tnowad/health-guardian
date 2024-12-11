package com.example.health_guardian_server.specifications;

import com.example.health_guardian_server.dtos.requests.diagnostic.ListDiagnosticReportRequest;
import com.example.health_guardian_server.entities.DiagnosticReport;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class DiagnosticReportSpecification implements Specification<DiagnosticReport> {
  private final ListDiagnosticReportRequest request;

  @Override
  public Predicate toPredicate(
      Root<DiagnosticReport> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    List<Predicate> predicates = new ArrayList<>();

    // Filter by user ID
    if (request.getUserId() != null && !request.getUserId().isEmpty()) {
      predicates.add(criteriaBuilder.equal(root.get("user").get("id"), request.getUserId()));
    }

    // Filter by report type
    if (request.getReportType() != null) {
      predicates.add(criteriaBuilder.equal(root.get("reportType"), request.getReportType()));
    }

    // Filter by report date range
    if (request.getStartDate() != null) {
      predicates.add(
          criteriaBuilder.greaterThanOrEqualTo(root.get("reportDate"), request.getStartDate()));
    }

    if (request.getEndDate() != null) {
      predicates.add(
          criteriaBuilder.lessThanOrEqualTo(root.get("reportDate"), request.getEndDate()));
    }

    // Filter by summary
    if (request.getSummary() != null && !request.getSummary().isEmpty()) {
      predicates.add(criteriaBuilder.like(root.get("summary"), "%" + request.getSummary() + "%"));
    }

    // Filter by notes
    if (request.getNotes() != null && !request.getNotes().isEmpty()) {
      predicates.add(criteriaBuilder.like(root.get("notes"), "%" + request.getNotes() + "%"));
    }

    // Filter by specific IDs
    if (request.getIds() != null && request.getIds().length > 0) {
      predicates.add(root.get("id").in((Object[]) request.getIds()));
    }

    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
  }
}
