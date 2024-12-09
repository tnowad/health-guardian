package com.example.health_guardian_server.entities2;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "family_histories")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FamilyHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
  private User user;

  @Column
  private String relation;

  @Column
  private String condition;

  @Column
  private String description;
}
