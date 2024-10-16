package com.example.health_guardian_server.entities;

import com.example.health_guardian_server.enums.VerificationType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "verifications")
public class Verification extends AbstractEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  String token;

  @Column(length = 6)
  String code;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false)
  Date expiryTime;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  VerificationType verificationType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_verifications_accounts", foreignKeyDefinition = "FOREIGN KEY (account_id) REFERENCES accounts(id) ON DELETE CASCADE ON UPDATE CASCADE"), nullable = false, updatable = false)
  @JsonBackReference
  Account account;

}
