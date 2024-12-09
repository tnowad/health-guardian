package com.example.health_guardian_server.entities2;

import com.example.health_guardian_server.entities2.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "appointments")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Appointment {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
  private User user;

  @Column(name = "appointment_date")
  private Date appoinmentDate;

  @Column
  private String reason;

  @Column
  private String address;

  @Column
  @Enumerated(EnumType.STRING)
  private AppointmentStatus status;

  @Column
  private String notes;
}
