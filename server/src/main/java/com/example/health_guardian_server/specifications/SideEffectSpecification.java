package com.example.health_guardian_server.specifications;

import com.example.health_guardian_server.entities.SideEffect;
import com.example.health_guardian_server.entities.SideEffectSeverity;
import org.springframework.data.jpa.domain.Specification;

public class SideEffectSpecification {

  // Specification for filtering by side effect name (case-insensitive)
  public static Specification<SideEffect> nameLike(String name) {
    if (name == null || name.isEmpty()) {
      return null;
    }
    return (root, query, builder) -> builder.like(builder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
  }

  // Specification for filtering by severity
  public static Specification<SideEffect> severityEqual(SideEffectSeverity severity) {
    if (severity == null) {
      return null;
    }
    return (root, query, builder) -> builder.equal(root.get("severity"), severity);
  }
}
