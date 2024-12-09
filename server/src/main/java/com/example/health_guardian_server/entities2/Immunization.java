package com.example.health_guardian_server.entities2;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "Immunizations")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Immunization {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  public String id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
  private User user;

  @Column(name = "vaccination_date")
  private Date vaccinationDate;

  @Column(name = "vaccine_name", nullable = false)
  private String vaccineName;

  @Column(name = "batch_number")
  private String batchNumber;

  @Column
  private String notes;
}
