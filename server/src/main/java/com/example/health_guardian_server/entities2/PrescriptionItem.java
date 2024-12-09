package com.example.health_guardian_server.entities2;

import com.example.health_guardian_server.entities2.enums.PrescriptionItemStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "prescription_items")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PrescriptionItem {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  public String id;

  @ManyToOne
  @JoinColumn(name = "prescription_id", nullable = false, referencedColumnName = "id")
  private Prescription prescription;

  @Column
  private String dosage;

  @Column(name = "medication_name")
  private String medicationName;

  @Column
  private String image;

  @Column
  private String frequency;

  @Column(name = "start_date")
  private Date startDate;

  @Column(name = "end_date")
  private Date endDate;

  @Column
  @Enumerated(EnumType.STRING)
  private PrescriptionItemStatus status;
}
