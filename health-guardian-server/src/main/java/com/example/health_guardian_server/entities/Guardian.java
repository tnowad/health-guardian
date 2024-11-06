package com.example.health_guardian_server.entities;

@Entity
@Table(name = "guardians")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Guardian {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private UUID id;

  @NotBlank
  private String name;

  @NotBlank
  private String relationshipToPatient;

  @NotBlank
  @Pattern(regexp = "^\\+?[0-9]*$")
  private String phone;

  @Email
  private String email;
}
