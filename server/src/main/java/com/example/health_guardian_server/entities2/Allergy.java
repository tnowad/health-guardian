package com.example.health_guardian_server.entities2;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "allergies")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Allergy {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  public String id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
  private User user;

  @Column(name = "allergy_name", nullable = false)
  private String allergyName;

  @Column
  private String severity;

  @Column(name = "reaction_description")
  private String reactionDescription;
}
