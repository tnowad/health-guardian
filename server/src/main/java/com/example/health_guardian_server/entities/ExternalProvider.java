package com.example.health_guardian_server.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "external_providers")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ExternalProvider {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(name = "provider_name")
  private String providerName;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
  private User user;

  @Column(name = "provider_user_id")
  private String providerUserId;

  @Column private String providerUserEmail;

  @Column private String token;
}
