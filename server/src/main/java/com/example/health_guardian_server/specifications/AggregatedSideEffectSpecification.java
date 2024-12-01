package com.example.health_guardian_server.specifications;

import com.example.health_guardian_server.dtos.requests.ListAggregatedSideEffectsRequest;
import com.example.health_guardian_server.entities.AggregatedSideEffect;
import com.example.health_guardian_server.entities.Medication;
import com.example.health_guardian_server.entities.SideEffect;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class AggregatedSideEffectSpecification implements Specification<AggregatedSideEffect> {

  private final ListAggregatedSideEffectsRequest request;

  @Override
  public Predicate toPredicate(
      Root<AggregatedSideEffect> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    List<Predicate> predicates = new ArrayList<>();

    // Filter by side effect ID
    if (request.getSideEffectId() != null && !request.getSideEffectId().isEmpty()) {
      Join<AggregatedSideEffect, SideEffect> sideEffectJoin = root.join("sideEffect");
      predicates.add(criteriaBuilder.equal(sideEffectJoin.get("id"), request.getSideEffectId()));
    }

    // Filter by medication ID
    if (request.getMedicationId() != null && !request.getMedicationId().isEmpty()) {
      Join<AggregatedSideEffect, Medication> medicationJoin = root.join("medication");
      predicates.add(criteriaBuilder.equal(medicationJoin.get("id"), request.getMedicationId()));
    }

    // Filter by period start date
    if (request.getPeriodStart() != null) {
      predicates.add(
          criteriaBuilder.greaterThanOrEqualTo(root.get("periodStart"), request.getPeriodStart()));
    }

    // Filter by period end date
    if (request.getPeriodEnd() != null) {
      predicates.add(
          criteriaBuilder.lessThanOrEqualTo(root.get("periodEnd"), request.getPeriodEnd()));
    }

    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
  }
}
