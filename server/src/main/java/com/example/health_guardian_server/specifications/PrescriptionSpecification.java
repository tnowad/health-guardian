package com.example.health_guardian_server.specifications;

import com.example.health_guardian_server.dtos.requests.ListPrescriptionRequest;
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

    // Filter by patient ID
    if (request.getPatientId() != null && !request.getPatientId().isEmpty()) {
      predicates.add(criteriaBuilder.equal(root.get("patient").get("id"), request.getPatientId()));
    }

    // Filter by medication ID
    if (request.getMedicationId() != null && !request.getMedicationId().isEmpty()) {
      predicates.add(
          criteriaBuilder.equal(root.get("medication").get("id"), request.getMedicationId()));
    }

    // Filter by prescribed by ID
    if (request.getPrescribedById() != null && !request.getPrescribedById().isEmpty()) {
      predicates.add(
          criteriaBuilder.equal(root.get("prescribedBy").get("id"), request.getPrescribedById()));
    }

    // Filter by status
    if (request.getStatus() != null && !request.getStatus().isEmpty()) {
      predicates.add(criteriaBuilder.equal(root.get("status"), request.getStatus()));
    }

    // // Filter by start date range
    // if (request.getStartDateFrom() != null) {
    // predicates.add(
    // criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"),
    // request.getStartDateFrom()));
    // }
    // if (request.getStartDateTo() != null) {
    // predicates.add(
    // criteriaBuilder.lessThanOrEqualTo(root.get("startDate"),
    // request.getStartDateTo()));
    // }
    //
    // // Filter by end date range
    // if (request.getEndDateFrom() != null) {
    // predicates.add(
    // criteriaBuilder.greaterThanOrEqualTo(root.get("endDate"),
    // request.getEndDateFrom()));
    // }
    // if (request.getEndDateTo() != null) {
    // predicates.add(
    // criteriaBuilder.lessThanOrEqualTo(root.get("endDate"),
    // request.getEndDateTo()));
    // }

    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
  }
}
