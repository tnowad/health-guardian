package com.example.health_guardian_server.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Past;
import java.util.Date;
import lombok.*;

@Entity
@Table(name = "reported_side_effects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode
public class ReportedSideEffect {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne
  @JoinColumn(name = "patient_id", referencedColumnName = "id")
  private Patient patient;

  @ManyToOne
  @JoinColumn(name = "side_effect_id", referencedColumnName = "id")
  private SideEffect sideEffect;

  @ManyToOne
  @JoinColumn(name = "prescription_id", referencedColumnName = "id")
  private Prescription prescription;

  @Past private Date reportDate;

  @Enumerated(EnumType.STRING)
  private SideEffectSeverity severity;

  @Lob private String notes;

  private String reportedBy;

  private String outcome;
}
