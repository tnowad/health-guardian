package com.example.health_guardian_server.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "accounts")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private String profileType;

  @Column(name = "user_id", insertable = false, updatable = false)
  private String userId;

  @OneToOne(mappedBy = "account", cascade = CascadeType.REMOVE)
  private User user;

  @Enumerated(EnumType.STRING)
  private AccountStatus status;

  @OneToMany(mappedBy = "account")
  private Set<LocalProvider> localProviders;

  @OneToMany(mappedBy = "account")
  private Set<ExternalProvider> externalProviders;
}
