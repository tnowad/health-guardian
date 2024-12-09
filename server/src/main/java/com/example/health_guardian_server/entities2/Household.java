package com.example.health_guardian_server.entities2;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "households")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Household {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  public String id;

  @Column(nullable = false)
  private String name;

  @Column
  private String avatar;

  @ManyToOne
  @JoinColumn(name = "head", nullable = false, referencedColumnName = "id")
  private User head;

  @OneToMany(mappedBy = "household", cascade = CascadeType.ALL, orphanRemoval = true)
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private List<HouseholdMember> householdMembers;
}
