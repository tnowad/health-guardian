package com.example.health_guardian_server.entities;

import com.example.health_guardian_server.enums.VerificationType;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
