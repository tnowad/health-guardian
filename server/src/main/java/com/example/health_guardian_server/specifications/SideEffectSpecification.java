package com.example.health_guardian_server.specifications;

import com.example.health_guardian_server.entities.SideEffect;
import com.example.health_guardian_server.entities.SideEffectSeverity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class SideEffectSpecification implements Specification<SideEffect> {

  private final String name;
  private final SideEffectSeverity severity;
  private final String description;

  @Override
  public Predicate toPredicate(
      Root<SideEffect> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    List<Predicate> predicates = new ArrayList<>();

    // Filter by name (partial match, case-insensitive)
    if (name != null && !name.isEmpty()) {
      predicates.add(
          criteriaBuilder.like(
              criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
    }

    // Filter by severity (exact match)
    if (severity != null) {
      predicates.add(criteriaBuilder.equal(root.get("severity"), severity));
    }

    // Filter by description (partial match, case-insensitive)
    if (description != null && !description.isEmpty()) {
      predicates.add(
          criteriaBuilder.like(
              criteriaBuilder.lower(root.get("description")),
              "%" + description.toLowerCase() + "%"));
    }

    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
  }
}
