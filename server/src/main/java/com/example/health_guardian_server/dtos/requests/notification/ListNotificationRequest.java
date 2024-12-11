package com.example.health_guardian_server.dtos.requests.notification;

import com.example.health_guardian_server.dtos.requests.PageableRequest;
import com.example.health_guardian_server.dtos.requests.PageableWithIdsRequest;
import com.example.health_guardian_server.entities.Notification;
import com.example.health_guardian_server.entities.enums.NotificationType;
import com.example.health_guardian_server.specifications.NotificationSpecification;
import java.sql.Timestamp;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class ListNotificationRequest
    implements PageableRequest<Notification>, PageableWithIdsRequest<String> {

  private Integer page;

  private Integer size;

  private String[] sortFields;

  private Boolean[] desc;

  private String[] ids;

  private String userId;

  private NotificationType notificationType;

  private Timestamp startDate;

  private Timestamp endDate;

  private Boolean readStatus;

  @Override
  public Specification<Notification> toSpecification() {
    return new NotificationSpecification(this);
  }
}
