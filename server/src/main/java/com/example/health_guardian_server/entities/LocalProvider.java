package com.example.health_guardian_server.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "local_providers")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class LocalProvider {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(name = "account_id", insertable = false, updatable = false)
  private String accountId;

  @ManyToOne
  @JoinColumn(name = "account_id", referencedColumnName = "id")
  private Account account;

  @OneToMany(mappedBy = "localProvider", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonBackReference
  List<Verification> verifications;

  @Email
  private String email;

  @Column(name = "password_hash")
  private String passwordHash;
}
