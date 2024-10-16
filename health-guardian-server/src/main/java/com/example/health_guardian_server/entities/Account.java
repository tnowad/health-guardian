package com.example.health_guardian_server.entities;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "accounts")
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class Account extends AbstractEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  String id;

  @Column(unique = true, nullable = false, length = 50)
  String username;

  @Column(nullable = false)
  String password;

  @Column(unique = true, nullable = false, length = 100)
  String email;

  @Column(nullable = false)
  @Builder.Default
  boolean isActivated = false;

  @Builder.Default
  @Column(nullable = false)
  boolean acceptTerms = false;

  @OneToOne
  Profile profile;

  @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  List<Verification> verifications;
}
