package com.example.health_guardian_server.entities;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "doctors")
public class Doctor extends Profile {
  @Column
  String specialization;

  @ManyToOne
  @JoinColumn(name = "organization_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_doctor_organization", foreignKeyDefinition = "FOREIGN KEY (organization_id) REFERENCES organizations(id) ON DELETE CASCADE ON UPDATE CASCADE"), nullable = false, updatable = false)
  @JsonBackReference
  Organization organization;

  @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY)
  @JsonManagedReference
  Set<MedicalRecordDetail> medicalRecordDetails;
}
