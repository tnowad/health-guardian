package com.example.health_guardian_server.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "patients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class Patient {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @NotBlank private String firstName;

  @NotBlank private String lastName;

  @Past private Date dob;

  @NotBlank private String gender;

  @Column private Date dateOfBirth;

  @ManyToOne
  @JoinColumn(name = "guardian_id", referencedColumnName = "id")
  private Guardian guardian;

  @Enumerated(EnumType.STRING)
  private MedicalStatus status;

  @CreationTimestamp private Date createdAt;

  @UpdateTimestamp private Date updatedAt;
}
