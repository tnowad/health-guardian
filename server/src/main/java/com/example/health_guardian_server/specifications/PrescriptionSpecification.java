package com.example.health_guardian_server.specifications;

import com.example.health_guardian_server.entities.Medication;
import com.example.health_guardian_server.entities.Patient;
import com.example.health_guardian_server.entities.Prescription;
import com.example.health_guardian_server.entities.PrescriptionStatus;
import com.example.health_guardian_server.entities.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class PrescriptionSpecification implements Specification<Prescription> {

  private final String patientId;
  private final String medicationId;
  private final String prescribedById;
  private final String status;
  private final Date startDate;
  private final Date endDate;

  @Override
  public Predicate toPredicate(
      Root<Prescription> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    List<Predicate> predicates = new ArrayList<>();

    // Filter by patient ID
    if (patientId != null && !patientId.isEmpty()) {
      Join<Prescription, Patient> patientJoin = root.join("patient");
      predicates.add(criteriaBuilder.equal(patientJoin.get("id"), patientId));
    }

    // Filter by medication ID
    if (medicationId != null && !medicationId.isEmpty()) {
      Join<Prescription, Medication> medicationJoin = root.join("medication");
      predicates.add(criteriaBuilder.equal(medicationJoin.get("id"), medicationId));
    }

    // Filter by prescribedBy ID
    if (prescribedById != null && !prescribedById.isEmpty()) {
      Join<Prescription, User> prescribedByJoin = root.join("prescribedBy");
      predicates.add(criteriaBuilder.equal(prescribedByJoin.get("id"), prescribedById));
    }

    // Filter by status
    if (status != null && !status.isEmpty()) {
      predicates.add(criteriaBuilder.equal(root.get("status"), PrescriptionStatus.valueOf(status)));
    }

    // Filter by start date (after or equal to)
    if (startDate != null) {
      predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), startDate));
    }

    // Filter by end date (before or equal to)
    if (endDate != null) {
      predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), endDate));
    }

    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
  }
}
