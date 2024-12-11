package com.example.health_guardian_server.specifications;

import com.example.health_guardian_server.dtos.requests.visit_summary.ListVisitSummaryRequest;
import com.example.health_guardian_server.entities.VisitSummary;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class VisitSummarySpecification implements Specification<VisitSummary> {

  private final ListVisitSummaryRequest request;

  @Override
  public Predicate toPredicate(
      Root<VisitSummary> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    List<Predicate> predicates = new ArrayList<>();

    if (request.getUserId() != null && !request.getUserId().isEmpty()) {
      predicates.add(criteriaBuilder.equal(root.get("user").get("id"), request.getUserId()));
    }

    if (request.getStartDate() != null) {
      predicates.add(
          criteriaBuilder.greaterThanOrEqualTo(root.get("visitDate"), request.getStartDate()));
    }

    if (request.getEndDate() != null) {
      predicates.add(
          criteriaBuilder.lessThanOrEqualTo(root.get("visitDate"), request.getEndDate()));
    }

    if (request.getVisitType() != null) {
      predicates.add(criteriaBuilder.equal(root.get("visitType"), request.getVisitType()));
    }

    if (request.getSummary() != null && !request.getSummary().isEmpty()) {
      predicates.add(criteriaBuilder.like(root.get("summary"), "%" + request.getSummary() + "%"));
    }

    if (request.getNotes() != null && !request.getNotes().isEmpty()) {
      predicates.add(criteriaBuilder.like(root.get("notes"), "%" + request.getNotes() + "%"));
    }

    if (request.getIds() != null && request.getIds().length > 0) {
      predicates.add(root.get("id").in((Object[]) request.getIds()));
    }

    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
  }
}
