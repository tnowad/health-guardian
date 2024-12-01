package com.example.health_guardian_server.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "prescriptions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode
public class Prescription {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne
  @JoinColumn(name = "patient_id", referencedColumnName = "id")
  private Patient patient;

  @ManyToOne
  @JoinColumn(name = "medication_id", referencedColumnName = "id")
  private Medication medication;

  @ManyToOne
  @JoinColumn(name = "prescribed_by", referencedColumnName = "id")
  private User prescribedBy;

  private String dosage;

  private String frequency;

  @Past private Date startDate;

  @Future private Date endDate;

  @Enumerated(EnumType.STRING)
  private PrescriptionStatus status;
}
