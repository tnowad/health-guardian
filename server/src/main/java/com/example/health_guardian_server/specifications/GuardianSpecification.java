package com.example.health_guardian_server.specifications;

import com.example.health_guardian_server.dtos.requests.ListGuardiansRequest;
import com.example.health_guardian_server.entities.Guardian;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public class GuardianSpecification implements Specification<Guardian> {

  private final ListGuardiansRequest request;

  public GuardianSpecification(ListGuardiansRequest request) {
    this.request = request;
  }

  @Override
  public Predicate toPredicate(
      Root<Guardian> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    List<Predicate> predicates = new ArrayList<>();

    // Filter by name
    if (request.getSearch() != null && !request.getSearch().isEmpty()) {
      predicates.add(
          criteriaBuilder.like(
              criteriaBuilder.lower(root.get("name")),
              "%" + request.getSearch().toLowerCase() + "%"));
    }

    // Filter by relationship to patient
    if (request.getRelationshipToPatient() != null
        && !request.getRelationshipToPatient().isEmpty()) {
      predicates.add(
          criteriaBuilder.like(
              criteriaBuilder.lower(root.get("relationshipToPatient")),
              "%" + request.getRelationshipToPatient().toLowerCase() + "%"));
    }

    // Filter by phone number
    if (request.getPhone() != null && !request.getPhone().isEmpty()) {
      predicates.add(
          criteriaBuilder.like(
              criteriaBuilder.lower(root.get("phone")),
              "%" + request.getPhone().toLowerCase() + "%"));
    }

    // Filter by email
    if (request.getEmail() != null && !request.getEmail().isEmpty()) {
      predicates.add(
          criteriaBuilder.like(
              criteriaBuilder.lower(root.get("email")),
              "%" + request.getEmail().toLowerCase() + "%"));
    }

    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
  }
}
