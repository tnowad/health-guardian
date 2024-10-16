package com.example.health_guardian_server.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "medical_record_details", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "doctor_id", "medical_record_id" })
})
public class MedicalRecordDetail {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "doctor_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_medical_record_details_doctor", foreignKeyDefinition = "FOREIGN KEY (doctor_id) REFERENCES doctors(id) ON DELETE CASCADE ON UPDATE CASCADE"), nullable = false, updatable = false)
  @JsonBackReference
  Doctor doctor;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "medical_record_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_medical_record_details_medical_record", foreignKeyDefinition = "FOREIGN KEY (medical_record_id) REFERENCES medical_records(id) ON DELETE CASCADE ON UPDATE CASCADE"), nullable = false, updatable = false)
  @JsonBackReference
  MedicalRecord medicalRecord;

  @Column
  String doctorCommonInfo;

  @Column
  String treatment;

  @Column
  String diagnosis;

  @Column
  String prescription;

  @Column
  String examinationResult;
}
