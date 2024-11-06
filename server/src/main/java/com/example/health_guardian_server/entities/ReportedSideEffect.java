package com.example.health_guardian_server.entities;

import java.util.Date;
import java.util.UUID;

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
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "reported_side_effects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ReportedSideEffect {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "patient_id", referencedColumnName = "id")
  private Patient patient;

  @ManyToOne
  @JoinColumn(name = "side_effect_id", referencedColumnName = "id")
  private SideEffect sideEffect;

  @ManyToOne
  @JoinColumn(name = "prescription_id", referencedColumnName = "id")
  private Prescription prescription;

  @Past
  private Date reportDate;

  @Enumerated(EnumType.STRING)
  private SideEffectSeverity severity;

  @Lob
  private String notes;

  private String reportedBy;

  private String outcome;
}
