package com.example.health_guardian_server.specifications;

import com.example.health_guardian_server.entities.Guardian;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class GuardianSpecification implements Specification<Guardian> {

  private final String name;
  private final String relationshipToPatient;
  private final String phone;
  private final String email;

  @Override
  public Predicate toPredicate(
      Root<Guardian> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    List<Predicate> predicates = new ArrayList<>();

    // Filter by name (partial match, case-insensitive)
    if (name != null && !name.isEmpty()) {
      predicates.add(
          criteriaBuilder.like(
              criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
    }

    // Filter by relationship to patient (exact match)
    if (relationshipToPatient != null && !relationshipToPatient.isEmpty()) {
      predicates.add(
          criteriaBuilder.equal(root.get("relationshipToPatient"), relationshipToPatient));
    }

    // Filter by phone (exact match)
    if (phone != null && !phone.isEmpty()) {
      predicates.add(criteriaBuilder.equal(root.get("phone"), phone));
    }

    // Filter by email (exact match)
    if (email != null && !email.isEmpty()) {
      predicates.add(criteriaBuilder.equal(root.get("email"), email));
    }

    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
  }
}
