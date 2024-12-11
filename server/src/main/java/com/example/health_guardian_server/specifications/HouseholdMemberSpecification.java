package com.example.health_guardian_server.specifications;

import com.example.health_guardian_server.dtos.requests.household.ListHouseholdMembersRequest;
import com.example.health_guardian_server.entities.HouseholdMember;
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

    if (request.getHouseholdId() != null && !request.getHouseholdId().isEmpty()) {
      predicates.add(
          criteriaBuilder.equal(root.get("household").get("id"), request.getHouseholdId()));
    }

    if (request.getMemberId() != null && !request.getMemberId().isEmpty()) {
      predicates.add(criteriaBuilder.equal(root.get("user").get("id"), request.getMemberId()));
    }

    if (request.getUserId() != null && !request.getUserId().isEmpty()) {
      predicates.add(criteriaBuilder.equal(root.get("user").get("id"), request.getUserId()));
    }

    if (request.getIds() != null && request.getIds().length > 0) {
      predicates.add(root.get("id").in((Object[]) request.getIds()));
    }

    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
  }
}
