package com.example.health_guardian_server.entities2;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "diagnostic_results")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DiagnosticResult {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  public String id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
  private User user;

  @Column(name = "test_name")
  private String testName;

  @Column(name = "result_date")
  private Date resultDate;

  @Column(name = "result_value")
  private String resultValue;

  @Column
  private String notes;
}
