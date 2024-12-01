package com.example.health_guardian_server.specifications;

import com.example.health_guardian_server.dtos.requests.ListReportedSideEffectsRequest;
import com.example.health_guardian_server.entities.Patient;
import com.example.health_guardian_server.entities.Prescription;
import com.example.health_guardian_server.entities.ReportedSideEffect;
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
public class ReportedSideEffectSpecification implements Specification<ReportedSideEffect> {

  private final ListReportedSideEffectsRequest request;

  @Override
  public Predicate toPredicate(
      Root<ReportedSideEffect> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    List<Predicate> predicates = new ArrayList<>();

    // Filter by patient ID
    if (request.getPatientId() != null && !request.getPatientId().isEmpty()) {
      Join<ReportedSideEffect, Patient> patientJoin = root.join("patient");
      predicates.add(criteriaBuilder.equal(patientJoin.get("id"), request.getPatientId()));
    }

    // Filter by side effect ID
    if (request.getSideEffectId() != null && !request.getSideEffectId().isEmpty()) {
      Join<ReportedSideEffect, SideEffect> sideEffectJoin = root.join("sideEffect");
      predicates.add(criteriaBuilder.equal(sideEffectJoin.get("id"), request.getSideEffectId()));
    }

    // Filter by prescription ID
    if (request.getPrescriptionId() != null && !request.getPrescriptionId().isEmpty()) {
      Join<ReportedSideEffect, Prescription> prescriptionJoin = root.join("prescription");
      predicates.add(
          criteriaBuilder.equal(prescriptionJoin.get("id"), request.getPrescriptionId()));
    }

    // Filter by severity
    if (request.getSeverity() != null && !request.getSeverity().isEmpty()) {
      predicates.add(criteriaBuilder.equal(root.get("severity"), request.getSeverity()));
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

    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
  }
}
