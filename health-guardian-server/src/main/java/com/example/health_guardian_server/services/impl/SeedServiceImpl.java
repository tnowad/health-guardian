package com.example.health_guardian_server.services.impl;

import org.springframework.stereotype.Service;

import com.example.health_guardian_server.repositories.AccountRepository;
import com.example.health_guardian_server.repositories.OrganizationRepository;
import com.example.health_guardian_server.repositories.PermissionRepository;
import com.example.health_guardian_server.repositories.ProfileRepository;
import com.example.health_guardian_server.repositories.RoleRepository;
import com.example.health_guardian_server.services.SeedService;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import static com.example.health_guardian_server.enums.Visibility.PUBLIC;
import com.example.health_guardian_server.entities.Account;
import com.example.health_guardian_server.entities.Doctor;
import com.example.health_guardian_server.entities.Organization;
import com.example.health_guardian_server.entities.Patient;
import com.example.health_guardian_server.entities.Permission;
import com.example.health_guardian_server.entities.Profile;
import com.example.health_guardian_server.entities.Role;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class SeedServiceImpl implements SeedService {
  AccountRepository accountRepository;
  ProfileRepository profileRepository;
  OrganizationRepository organizationRepository;
  RoleRepository roleRepository;
  PermissionRepository permissionRepository;

  public void clear() {
    accountRepository.deleteAllInBatch();
    profileRepository.deleteAllInBatch();
    organizationRepository.deleteAllInBatch();
    roleRepository.deleteAllInBatch();
    permissionRepository.deleteAllInBatch();
  }

  public void defaultSeed() {
    Map<String, List<String>> rolePermissionsMap = Map.of(
        "ADMIN", List.of("READ", "WRITE", "DELETE", "UPDATE", "CREATE", "READ_ALL"),
        "DOCTOR", List.of("READ", "WRITE", "DELETE", "CREATE", "READ_ALL"),
        "PATIENT", List.of("READ", "DELETE", "CREATE"));

    // Ensure permissions exist
    List<Permission> allPermissions = rolePermissionsMap.values().stream()
        .flatMap(List::stream)
        .distinct()
        .map(permissionName -> permissionRepository.findByName(permissionName)
            .orElseGet(() -> Permission.builder().name(permissionName).build()))
        .collect(Collectors.toList());

    permissionRepository.saveAll(allPermissions);

    // Ensure roles exist and assign permissions
    rolePermissionsMap.forEach((roleName, permissionNames) -> {
      Role role = roleRepository.findByName(roleName)
          .orElseGet(() -> Role.builder().name(roleName).build());

      List<Permission> rolePermissions = allPermissions.stream()
          .filter(permission -> permissionNames.contains(permission.getName()))
          .collect(Collectors.toList());

      role.setPermissions(rolePermissions);
      roleRepository.save(role);
    });

  }

  public void mockSeed() {
    Faker faker = new Faker();
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    Account admin = Account.builder().password(
        passwordEncoder.encode("Password@123"))
        .email("admin@health-guardian.com")
        .isActivated(false)
      .acceptTerms(true)
        .roles(Set.of(roleRepository.findByName("ADMIN").get()))
        .build();

    for (int i = 0; i < 10; i++) {
      Organization organization = Organization.builder()
          .name(faker.company().name())
          .address(faker.address().fullAddress())
          .build();
      organizationRepository.save(organization);
    }
    accountRepository.save(admin);
    for (int i = 0; i < 10; i++) {
      Account account = Account.builder()
          .password(passwordEncoder.encode("123456"))
          .email(faker.internet().emailAddress())
          .isActivated(true)
          .roles(Set.of(roleRepository.findByName("PATIENT").get()))
          .build();
      accountRepository.save(account);
      Patient patient = Patient.builder()
          .account(account)
          .fullName(faker.name().fullName())
          .bio(faker.lorem().sentence())
          .phoneNunmber(faker.phoneNumber().cellPhone())
          .address(faker.address().fullAddress())
          .dateOfBirth(faker.date().birthday().toInstant().atZone(
              ZoneId.systemDefault())
              .toLocalDate())
          .avatarUrl(faker.internet().avatar())
          .visibility(PUBLIC)
          .build();
      profileRepository.save(patient);
    }

    for (int i = 0; i < 10; i++) {
      Account account = Account.builder()
          .password(passwordEncoder.encode("123456"))
          .email(faker.internet().emailAddress())
          .isActivated(true)
          .roles(Set.of(roleRepository.findByName("DOCTOR").get()))
          .build();
      accountRepository.save(account);
      Doctor doctor = Doctor.builder()
          .account(account)
          .fullName(faker.name().fullName())
          .bio(faker.lorem().sentence())
          .phoneNunmber(faker.phoneNumber().cellPhone())
          .address(faker.address().fullAddress())
          .dateOfBirth(faker.date().birthday().toInstant().atZone(
              ZoneId.systemDefault())
              .toLocalDate())
          .avatarUrl(faker.internet().avatar())
          .visibility(PUBLIC)
          .specialization(faker.lorem().word())
          .organization(organizationRepository.findAll().get(
              faker.random().nextInt(0, 9)))
          .build();
      profileRepository.save(doctor);
    }
    for (int i = 0; i < 10; i++) {
      Profile profile = Profile.builder()
          .fullName(faker.name().fullName())
          .bio(faker.lorem().sentence())
          .phoneNunmber(faker.phoneNumber().cellPhone())
          .address(faker.address().fullAddress())
          .dateOfBirth(faker.date().birthday().toInstant().atZone(
              ZoneId.systemDefault())
              .toLocalDate())
          .avatarUrl(faker.internet().avatar())
          .visibility(PUBLIC)
          .build();
      profileRepository.save(profile);
    }

  }
}
