package com.example.health_guardian_server.entities;

import java.time.LocalDate;
import java.util.List;

import com.example.health_guardian_server.enums.Visibility;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
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

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "account_id", nullable = true)
  @JsonBackReference
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
