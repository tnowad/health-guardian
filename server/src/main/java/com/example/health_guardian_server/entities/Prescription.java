package com.example.health_guardian_server.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Past;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
  @JsonIgnore
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
