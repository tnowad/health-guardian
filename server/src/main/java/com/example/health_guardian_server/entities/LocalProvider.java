package com.example.health_guardian_server.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "local_providers")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class LocalProvider {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(nullable = false)
  private String email;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = true, referencedColumnName = "id")
  private User user;

  @Column(name = "password_hash")
  private String passwordHash;

  @Column(name = "is_verified")
  private boolean isVerified;
}
