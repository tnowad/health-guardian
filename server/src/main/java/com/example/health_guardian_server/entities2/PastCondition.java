package com.example.health_guardian_server.entities2;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "past_conditions")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PastCondition {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
  private User user;

  @Column
  private String condition;

  @Column
  private String description;

  @Column(name = "date_diagnosed")
  private Date dateDiagnosed;
}
