package com.example.health_guardian_server.entities2;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "physician_notes")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PhysicianNote {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  public String id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
  private User user;

  @Column
  private Date date;

  @Column
  private String note;
}
