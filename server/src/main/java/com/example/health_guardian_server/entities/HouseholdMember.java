package com.example.health_guardian_server.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "household_members")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class HouseholdMember {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  public String id;

  @ManyToOne
  @JoinColumn(name = "household_id", nullable = false, referencedColumnName = "id")
  private Household household;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
  private User user;
}
