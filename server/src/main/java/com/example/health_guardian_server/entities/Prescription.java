package com.example.health_guardian_server.entities;

import com.example.health_guardian_server.entities.enums.PrescriptionStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "prescriptions")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EntityListeners(AuditingEntityListener.class)
public class Prescription {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  public String id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
  private User user;

  @Column(name = "issued_by")
  private String issuedBy;

  @Column(name = "issued_date")
  private Timestamp issuedDate;

  @Column(name = "valid_until")
  private Date validUntil;

  @Column
  @Enumerated(EnumType.STRING)
  private PrescriptionStatus status;

  @CreatedDate
  @Column(nullable = false, updatable = false)
  private Timestamp createdAt;

  @LastModifiedDate
  @Column(nullable = false)
  private Timestamp updatedAt;

  @OneToMany(mappedBy = "prescription", cascade = CascadeType.ALL, orphanRemoval = true)
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private List<PrescriptionItem> prescriptionItems;
}
