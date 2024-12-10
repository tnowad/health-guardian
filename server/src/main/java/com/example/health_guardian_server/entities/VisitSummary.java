package com.example.health_guardian_server.entities;

import com.example.health_guardian_server.entities.enums.VisitSummaryType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "visit_summaries")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class VisitSummary {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
  private User user;

  @Column(name = "visit_date")
  private Date visitDate;

  @Column(name = "visit_type")
  @Enumerated(EnumType.STRING)
  private VisitSummaryType visitType;

  @Column
  private String summary;

  @Column
  private String notes;
}
