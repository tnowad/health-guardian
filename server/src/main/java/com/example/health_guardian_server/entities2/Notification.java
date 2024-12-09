package com.example.health_guardian_server.entities2;

import com.example.health_guardian_server.entities2.enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;

@Entity
@Table(name = "notifications")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Notification {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
  private User user;

  @Column(name = "notification_type")
  @Enumerated(EnumType.STRING)
  private NotificationType notificationType;

  @Column(name = "notification_date")
  private Timestamp notificationDate;

  @Column(name = "read_status")
  private boolean readStatus;
}
