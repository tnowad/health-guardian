package com.example.health_guardian_server.entities;

import java.time.LocalDate;
import com.example.health_guardian_server.enums.Visibility;
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

  @OneToOne(mappedBy = "profile")
  @JoinColumn(name = "account_id")
  Account account;

  @Column
  String fullName;

  @Column
  String bio;

  @Column
  String phoneNunmber;

  @Column
  String address;

  @Column
  LocalDate dateOfBirth;

  @Column
  String avatarUrl;

  @Column
  Visibility visibility;

}
