package com.example.health_guardian_server.specifications;

import com.example.health_guardian_server.dtos.requests.family_history.ListFamilyHistoryRequest;
import com.example.health_guardian_server.entities.FamilyHistory;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class FamilyHistorySpecification implements Specification<FamilyHistory> {
  private final ListFamilyHistoryRequest request;

  @Override
  public Predicate toPredicate(
      Root<FamilyHistory> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    List<Predicate> predicates = new ArrayList<>();

    // Filter by user ID
    if (request.getUserId() != null && !request.getUserId().isEmpty()) {
      predicates.add(criteriaBuilder.equal(root.get("user").get("id"), request.getUserId()));
    }

    // Filter by relation
    if (request.getRelation() != null && !request.getRelation().isEmpty()) {
      predicates.add(criteriaBuilder.like(root.get("relation"), "%" + request.getRelation() + "%"));
    }

    // Filter by condition
    if (request.getCondition() != null && !request.getCondition().isEmpty()) {
      predicates.add(
          criteriaBuilder.like(root.get("condition"), "%" + request.getCondition() + "%"));
    }

    // Filter by description
    if (request.getDescription() != null && !request.getDescription().isEmpty()) {
      predicates.add(
          criteriaBuilder.like(root.get("description"), "%" + request.getDescription() + "%"));
    }

    // Filter by specific IDs
    if (request.getIds() != null && request.getIds().length > 0) {
      predicates.add(root.get("id").in((Object[]) request.getIds()));
    }

    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
  }
}
