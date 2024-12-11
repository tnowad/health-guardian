package com.example.health_guardian_server.specifications;

import com.example.health_guardian_server.dtos.requests.prescription.ListPrescriptionRequest;
import com.example.health_guardian_server.entities.Prescription;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class PrescriptionSpecification implements Specification<Prescription> {

  private final ListPrescriptionRequest request;

  @Override
  public Predicate toPredicate(
      Root<Prescription> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    List<Predicate> predicates = new ArrayList<>();

    // Filter by user ID
    if (request.getUserId() != null && !request.getUserId().isEmpty()) {
      predicates.add(criteriaBuilder.equal(root.get("user").get("id"), request.getUserId()));
    }

    // Filter by issued by
    if (request.getIssuedBy() != null && !request.getIssuedBy().isEmpty()) {
      predicates.add(criteriaBuilder.like(root.get("issuedBy"), "%" + request.getIssuedBy() + "%"));
    }

    // Filter by valid until date
    if (request.getValidUntil() != null) {
      predicates.add(
          criteriaBuilder.lessThanOrEqualTo(root.get("validUntil"), request.getValidUntil()));
    }

    // Filter by prescription status
    if (request.getStatus() != null) {
      predicates.add(criteriaBuilder.equal(root.get("status"), request.getStatus()));
    }

    // Filter by date range (issued date)
    if (request.getStartDate() != null) {
      predicates.add(
          criteriaBuilder.greaterThanOrEqualTo(root.get("issuedDate"), request.getStartDate()));
    }

    if (request.getEndDate() != null) {
      predicates.add(
          criteriaBuilder.lessThanOrEqualTo(root.get("issuedDate"), request.getEndDate()));
    }

    // Filter by specific IDs
    if (request.getIds() != null && request.getIds().length > 0) {
      predicates.add(root.get("id").in((Object[]) request.getIds()));
    }

    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
  }
}
