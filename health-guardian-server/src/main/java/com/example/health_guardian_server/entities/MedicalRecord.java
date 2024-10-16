package com.example.health_guardian_server.entities;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Table(name = "medical_records")
public class MedicalRecord extends AbstractEntity {
  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.UUID)
  String id;

  @Column
  @Temporal(TemporalType.TIMESTAMP)
  LocalDateTime examinationDate;

  @Column
  String examinationPlace;

  @OneToMany(mappedBy = "medicalRecord", fetch = FetchType.EAGER)
  @JsonManagedReference
  List<MedicalRecordDetail> medicalRecordDetails;
}
