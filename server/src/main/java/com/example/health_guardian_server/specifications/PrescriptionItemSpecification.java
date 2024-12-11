package com.example.health_guardian_server.specifications;

import com.example.health_guardian_server.dtos.requests.prescription.ListPrescriptionItemRequest;
import com.example.health_guardian_server.entities.PrescriptionItem;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class PrescriptionItemSpecification implements Specification<PrescriptionItem> {
  private final ListPrescriptionItemRequest request;

  @Override
  public Predicate toPredicate(
      Root<PrescriptionItem> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    List<Predicate> predicates = new ArrayList<>();

    // Filter by prescription id
    if (request.getPrescriptionId() != null && !request.getPrescriptionId().isEmpty()) {
      predicates.add(
          criteriaBuilder.equal(root.get("prescription").get("id"), request.getPrescriptionId()));
    }

    // Filter by medication name
    if (request.getMedicationName() != null && !request.getMedicationName().isEmpty()) {
      predicates.add(
          criteriaBuilder.like(
              root.get("medicationName"), "%" + request.getMedicationName() + "%"));
    }

    // Filter by status
    if (request.getStatus() != null) {
      predicates.add(criteriaBuilder.equal(root.get("status"), request.getStatus()));
    }

    // Filter by frequency
    if (request.getFrequency() != null && !request.getFrequency().isEmpty()) {
      predicates.add(
          criteriaBuilder.like(root.get("frequency"), "%" + request.getFrequency() + "%"));
    }

    // Filter by start date range
    if (request.getStartDate() != null) {
      predicates.add(
          criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), request.getStartDate()));
    }

    // Filter by end date range
    if (request.getEndDate() != null) {
      predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), request.getEndDate()));
    }

    // Filter by dosage
    if (request.getDosage() != null && !request.getDosage().isEmpty()) {
      predicates.add(criteriaBuilder.like(root.get("dosage"), "%" + request.getDosage() + "%"));
    }

    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
  }
}
