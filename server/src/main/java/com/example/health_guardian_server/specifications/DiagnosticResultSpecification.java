package com.example.health_guardian_server.specifications;

import com.example.health_guardian_server.dtos.requests.diagnostic.ListDiagnosticResultRequest;
import com.example.health_guardian_server.entities.DiagnosticResult;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class DiagnosticResultSpecification implements Specification<DiagnosticResult> {
  private final ListDiagnosticResultRequest request;

  @Override
  public Predicate toPredicate(
      Root<DiagnosticResult> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    List<Predicate> predicates = new ArrayList<>();

    // Filter by user ID
    if (request.getUserId() != null && !request.getUserId().isEmpty()) {
      predicates.add(criteriaBuilder.equal(root.get("user").get("id"), request.getUserId()));
    }

    // Filter by test name
    if (request.getTestName() != null && !request.getTestName().isEmpty()) {
      predicates.add(criteriaBuilder.like(root.get("testName"), "%" + request.getTestName() + "%"));
    }

    // Filter by result date range
    if (request.getStartDate() != null) {
      predicates.add(
          criteriaBuilder.greaterThanOrEqualTo(root.get("resultDate"), request.getStartDate()));
    }

    if (request.getEndDate() != null) {
      predicates.add(
          criteriaBuilder.lessThanOrEqualTo(root.get("resultDate"), request.getEndDate()));
    }

    // Filter by result value
    if (request.getResultValue() != null && !request.getResultValue().isEmpty()) {
      predicates.add(
          criteriaBuilder.like(root.get("resultValue"), "%" + request.getResultValue() + "%"));
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
