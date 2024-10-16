package com.example.health_guardian_server.entities;

import java.util.Set;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import lombok.*;
import jakarta.persistence.*;

@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Table(name = "organizations")
public class Organization extends AbstractEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  String id;

  @Column
  String name;

  @Column
  String address;

  @OneToMany(mappedBy = "organization")
  Set<Doctor> doctors;
}
