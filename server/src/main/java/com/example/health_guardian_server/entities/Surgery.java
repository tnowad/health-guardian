package com.example.health_guardian_server.entities;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;

@Entity
@Table(name = "surgeries")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Surgery {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  public String id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
  private User user;

  @Column(nullable = false)
  private Date date;

  @Column
  private String description;

  @Column
  private String notes;
}
