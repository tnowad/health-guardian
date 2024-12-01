package com.example.health_guardian_server.specifications;

import com.example.health_guardian_server.dtos.requests.ListGuardianRequest;
import com.example.health_guardian_server.entities.Guardian;
import com.example.health_guardian_server.entities.Patient;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class GuardianSpecification implements Specification<Guardian> {
  String search;
  String type;
  String[] ids;

  public GuardianSpecification(ListGuardianRequest request) {
    this.search = request.getSearch();
    this.type = request.getType();
    this.ids = request.getIds();
  }

  @Override
  public Predicate toPredicate(Root<Guardian> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    List<Predicate> predicates = new ArrayList<>();

    if (search != null && !search.isEmpty()) {
      predicates.add(criteriaBuilder.like(root.get("id"), "%" + search + "%"));
    }

    if (type != null && !type.isEmpty()) {
      predicates.add(criteriaBuilder.equal(root.get("type"), type));
    }

    if (ids != null && ids.length > 0) {
      Join<Guardian, Patient> join = root.join("patients");
      predicates.add(join.get("id").in((Object[]) ids));
    }

    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
  }
}
