package com.example.health_guardian_server.specifications;

import com.example.health_guardian_server.dtos.requests.ListHouseholdMembersRequest;
import com.example.health_guardian_server.entities.Household;
import com.example.health_guardian_server.entities.HouseholdMember;
import com.example.health_guardian_server.entities.Patient;
import jakarta.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class HouseholdMemberSpecification implements Specification<HouseholdMember> {

  private final ListHouseholdMembersRequest request;

  @Override
  public Predicate toPredicate(
      Root<HouseholdMember> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    List<Predicate> predicates = new ArrayList<>();

    // Filter by household ID
    if (request.getHouseholdId() != null && !request.getHouseholdId().isEmpty()) {
      Join<HouseholdMember, Household> householdJoin = root.join("household");
      predicates.add(criteriaBuilder.equal(householdJoin.get("id"), request.getHouseholdId()));
    }

    // Filter by patient ID
    if (request.getPatientId() != null && !request.getPatientId().isEmpty()) {
      Join<HouseholdMember, Patient> patientJoin = root.join("patient");
      predicates.add(criteriaBuilder.equal(patientJoin.get("id"), request.getPatientId()));
    }

    // Filter by relationship to patient
    if (request.getRelationshipToPatient() != null
        && !request.getRelationshipToPatient().isEmpty()) {
      predicates.add(
          criteriaBuilder.like(
              criteriaBuilder.lower(root.get("relationshipToPatient")),
              "%" + request.getRelationshipToPatient().toLowerCase() + "%"));
    }

    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
  }
}
