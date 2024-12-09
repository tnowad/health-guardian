package com.example.health_guardian_server.entities2;

import com.example.health_guardian_server.entities2.enums.ReportType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "diagnostic_reports")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DiagnosticReport {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  public String id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
  private User user;

  @Column(name = "report_date")
  private Date reportDate;

  @Column(name = "report_type")
  @Enumerated(EnumType.STRING)
  private ReportType reportType;

  @Column
  private String summary;

  @Column
  private String notes;
}
