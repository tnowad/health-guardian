package com.example.health_guardian_server.entities;

import com.example.health_guardian_server.entities.enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;

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

  @Column
  private String title;

  @Column(name = "notification_type")
  @Enumerated(EnumType.STRING)
  private NotificationType notificationType;

  @Column(name = "notification_date")
  private Timestamp notificationDate;

  @Column(name = "read_status")
  private boolean readStatus;
}
