package com.example.health_guardian_server.specifications;

import com.example.health_guardian_server.dtos.requests.ListConsentFormsRequest;
import com.example.health_guardian_server.entities.ConsentForm;
import com.example.health_guardian_server.entities.Patient;
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
public class ConsentFormSpecification implements Specification<ConsentForm> {

  private final ListConsentFormsRequest request;

  @Override
  public Predicate toPredicate(
      Root<ConsentForm> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    List<Predicate> predicates = new ArrayList<>();

    // Filter by patient ID
    if (request.getPatientId() != null && !request.getPatientId().isEmpty()) {
      Join<ConsentForm, Patient> patientJoin = root.join("patient");
      predicates.add(criteriaBuilder.equal(patientJoin.get("id"), request.getPatientId()));
    }

    // Filter by form name
    if (request.getFormName() != null && !request.getFormName().isEmpty()) {
      predicates.add(
          criteriaBuilder.like(
              criteriaBuilder.lower(root.get("formName")),
              "%" + request.getFormName().toLowerCase() + "%"));
    }

    // Filter by consent date
    if (request.getConsentDate() != null) {
      predicates.add(criteriaBuilder.equal(root.get("consentDate"), request.getConsentDate()));
    }

    // Filter by consent status
    if (request.getStatus() != null && !request.getStatus().isEmpty()) {
      predicates.add(criteriaBuilder.equal(root.get("status"), request.getStatus()));
    }

    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
  }
}
