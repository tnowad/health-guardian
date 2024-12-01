package com.example.health_guardian_server.dtos.requests;

import com.example.health_guardian_server.entities.SideEffect;
import com.example.health_guardian_server.entities.SideEffectSeverity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

@Getter
@RequiredArgsConstructor
public class ListSideEffectRequest {

  private String name;
  private SideEffectSeverity severity;
  private int page = 0;
  private int size = 20;

  public Specification<SideEffect> toSpecification() {
    return Specification.where(nameLike(name)).and(severityEqual(severity));
  }

  // Converts the request into a Pageable object for pagination
  public Pageable toPageable() {
    return PageRequest.of(page, size);
  }

  private Specification<SideEffect> nameLike(String name) {
    if (name == null || name.isEmpty()) {
      return null;
    }
    return (root, query, builder) -> builder.like(builder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
  }

  private Specification<SideEffect> severityEqual(SideEffectSeverity severity) {
    if (severity == null) {
      return null;
    }
    return (root, query, builder) -> builder.equal(root.get("severity"), severity);
  }
}
