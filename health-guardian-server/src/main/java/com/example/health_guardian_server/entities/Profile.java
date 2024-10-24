package com.example.health_guardian_server.entities;

import java.time.LocalDate;
import java.util.List;

import com.example.health_guardian_server.enums.Visibility;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "profiles")
public class Profile extends AbstractEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  String id;

  @OneToOne(mappedBy = "profile")
  @JoinColumn(name = "account_id", nullable = true)
  Account account;

  @Column(nullable = false)
  String fullName;

  @Column
  String bio;

  @Column(nullable = false)
  String phoneNunmber;

  @Column
  String address;

  @Column(nullable = false)
  LocalDate dateOfBirth;

  @Column
  String avatarUrl;

  @Column
  Visibility visibility;

  @ManyToMany
  List<Family> families;

}
