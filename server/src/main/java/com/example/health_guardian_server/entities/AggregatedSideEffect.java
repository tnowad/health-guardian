package com.example.health_guardian_server.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Past;
import java.util.Date;

import lombok.*;

@Entity
@Table(name = "aggregated_side_effects")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class AggregatedSideEffect {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne
  @JoinColumn(name = "side_effect_id", referencedColumnName = "id")
  private SideEffect sideEffect;

  @ManyToOne
  @JoinColumn(name = "medication_id", referencedColumnName = "id")
  private Medication medication;

  private int totalReports;

  @Past private Date periodStart;

  @Future private Date periodEnd;
}
