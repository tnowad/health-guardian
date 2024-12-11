package com.example.health_guardian_server.specifications;

import com.example.health_guardian_server.dtos.requests.notification.ListNotificationRequest;
import com.example.health_guardian_server.entities.Notification;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public class NotificationSpecification implements Specification<Notification> {

  private final ListNotificationRequest request;

  public NotificationSpecification(ListNotificationRequest request) {
    this.request = request;
  }

  @Override
  public Predicate toPredicate(
      Root<Notification> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    List<Predicate> predicates = new ArrayList<>();

    if (request.getUserId() != null && !request.getUserId().isEmpty()) {
      predicates.add(criteriaBuilder.equal(root.get("user").get("id"), request.getUserId()));
    }

    if (request.getNotificationType() != null) {
      predicates.add(
          criteriaBuilder.equal(root.get("notificationType"), request.getNotificationType()));
    }

    if (request.getStartDate() != null) {
      predicates.add(
          criteriaBuilder.greaterThanOrEqualTo(
              root.get("notificationDate"), request.getStartDate()));
    }

    if (request.getEndDate() != null) {
      predicates.add(
          criteriaBuilder.lessThanOrEqualTo(root.get("notificationDate"), request.getEndDate()));
    }

    if (request.getReadStatus() != null) {
      predicates.add(criteriaBuilder.equal(root.get("readStatus"), request.getReadStatus()));
    }

    if (request.getIds() != null && request.getIds().length > 0) {
      predicates.add(root.get("id").in((Object[]) request.getIds()));
    }

    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
  }
}
