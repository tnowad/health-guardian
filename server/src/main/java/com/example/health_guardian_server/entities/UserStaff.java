package com.example.health_guardian_server.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.*;

@Entity
@Table(name = "user_staffs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class UserStaff {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column private String firstName;

  @Column private String lastName;

  @Column Date dateOfBirth;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  private String role;

  private String roleType;
}
