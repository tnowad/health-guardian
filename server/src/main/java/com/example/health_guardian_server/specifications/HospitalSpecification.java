package com.example.health_guardian_server.specifications;

import com.example.health_guardian_server.dtos.requests.ListHospitalRequest;
import com.example.health_guardian_server.entities.Account;
import com.example.health_guardian_server.entities.Hospital;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class HospitalSpecification  implements Specification<Hospital> {

  String search;
  String type;
  String[] ids;

  public HospitalSpecification(ListHospitalRequest request) {
    this.search = request.getSearch();
    this.type = request.getType();
    this.ids = request.getIds();
  }

  @Override
  public Predicate toPredicate(Root<Hospital> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    List<Predicate> predicates = new ArrayList<>();

    if (search != null && !search.isEmpty()) {
      predicates.add(criteriaBuilder.like(root.get("id"), "%" + search + "%"));
    }

    if (type != null && !type.isEmpty()) {
      predicates.add(criteriaBuilder.equal(root.get("type"), type));
    }

    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
  }
}
