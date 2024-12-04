package com.example.health_guardian_server.entities;

import jakarta.persistence.*;
import java.util.Set;
import lombok.*;

@Entity
@Table(name = "accounts")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true) // Include only specific fields
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // Include only specific fields
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @ToString.Include
  @EqualsAndHashCode.Include
  private String id;

  @ToString.Include
  private String profileType;

  @Column(name = "user_id", insertable = false, updatable = false)
  private String userId;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  @Enumerated(EnumType.STRING)
  @ToString.Include
  private AccountStatus status;

  @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
  @ToString.Exclude // Exclude collections to avoid stack overflow
  @EqualsAndHashCode.Exclude
  private Set<LocalProvider> localProviders;

  @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private Set<ExternalProvider> externalProviders;
}
