package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.entities.*;
import com.example.health_guardian_server.repositories.*;
import com.example.health_guardian_server.services.SeedService;
import com.example.health_guardian_server.utils.Constants;
import com.example.health_guardian_server.utils.Constants.PermissionName;
import com.github.javafaker.Faker;
import jakarta.transaction.Transactional;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SeedServiceImpl implements SeedService {

  AccountRepository accountRepository;
  AggregatedSideEffectRepository aggregatedSideEffectRepository;
  AppointmentRepository appointmentRepository;
  ConsentFormRepository consentFormRepository;
  ExternalProviderRepository externalProviderRepository;
  GuardianRepository guardianRepository;
  HospitalRepository hospitalRepository;
  HouseholdMemberRepository householdMemberRepository;
  HouseholdRepository householdRepository;
  LocalProviderRepository localProviderRepository;
  MedicationRepository medicationRepository;
  NotificationRepository notificationRepository;
  PatientLogRepository patientLogRepository;
  PatientRepository patientRepository;
  PermissionRepository permissionRepository;
  PrescriptionRepository prescriptionRepository;
  ReportedSideEffectRepository reportedSideEffectRepository;
  RoleRepository roleRepository;
  SideEffectRepository sideEffectRepository;
  UserMedicalStaffRepository userMedicalStaffRepository;
  UserPatientRepository userPatientRepository;
  UserRepository userRepository;
  UserStaffRepository userStaffRepository;
  PasswordEncoder passwordEncoder;

  Faker faker = new Faker();

  @Override
  @Transactional
  public void clear() {
    accountRepository.deleteAllInBatch();
    aggregatedSideEffectRepository.deleteAllInBatch();
    appointmentRepository.deleteAllInBatch();
    consentFormRepository.deleteAllInBatch();
    externalProviderRepository.deleteAllInBatch();
    guardianRepository.deleteAllInBatch();
    hospitalRepository.deleteAllInBatch();
    householdMemberRepository.deleteAllInBatch();
    householdRepository.deleteAllInBatch();
    localProviderRepository.deleteAllInBatch();
    medicationRepository.deleteAllInBatch();
    notificationRepository.deleteAllInBatch();
    patientLogRepository.deleteAllInBatch();
    patientRepository.deleteAllInBatch();
    permissionRepository.deleteAllInBatch();
    prescriptionRepository.deleteAllInBatch();
    reportedSideEffectRepository.deleteAllInBatch();
    roleRepository.deleteAllInBatch();
    sideEffectRepository.deleteAllInBatch();
    userMedicalStaffRepository.deleteAllInBatch();
    userPatientRepository.deleteAllInBatch();
    userRepository.deleteAllInBatch();
    userStaffRepository.deleteAllInBatch();
    log.info("Database cleared");
  }

  @Override
  @Transactional
  public void initial() {
    List<Permission> permissions =
        List.copyOf(EnumSet.allOf(Constants.PermissionName.class)).stream()
            .map(permissionName -> Permission.builder().name(permissionName.name()).build())
            .collect(Collectors.toList());

    permissionRepository.saveAll(permissions);

    List<Map<String, List<String>>> permissionRoleMapping = new ArrayList<>();
    Map<String, List<String>> adminRolePermissions = new HashMap<>();
    adminRolePermissions.put(
        "admin",
        Arrays.asList(
            PermissionName.ACCOUNT_VIEW_OWN.name(),
            PermissionName.PATIENT_VIEW_OWN.name(),
            PermissionName.APPOINTMENT_CREATE_OWN.name(),
            PermissionName.PRESCRIPTION_CREATE_OWN.name(),
            PermissionName.MEDICATION_ADD_OWN.name(),
            PermissionName.REPORT_CREATE_OWN.name()));

    Map<String, List<String>> doctorRolePermissions = new HashMap<>();
    doctorRolePermissions.put(
        "doctor",
        Arrays.asList(
            PermissionName.PATIENT_VIEW_OWN.name(),
            PermissionName.PATIENT_UPDATE_OWN.name(),
            PermissionName.APPOINTMENT_VIEW_OWN.name(),
            PermissionName.PRESCRIPTION_VIEW_OWN.name(),
            PermissionName.REPORT_VIEW_OWN.name()));

    Map<String, List<String>> nurseRolePermissions = new HashMap<>();
    nurseRolePermissions.put(
        "nurse",
        Arrays.asList(
            PermissionName.PATIENT_VIEW_OWN.name(),
            PermissionName.NOTIFICATION_VIEW_OWN.name(),
            PermissionName.REPORT_VIEW_OWN.name()));

    permissionRoleMapping.add(adminRolePermissions);
    permissionRoleMapping.add(doctorRolePermissions);
    permissionRoleMapping.add(nurseRolePermissions);

    List<Role> roles =
        permissionRoleMapping.stream()
            .map(
                rolePermissions -> {
                  Role role = new Role();
                  role.setName(rolePermissions.keySet().iterator().next());
                  role.setPermissions(
                      permissions.stream()
                          .filter(
                              permission ->
                                  rolePermissions
                                      .get(role.getName())
                                      .contains(permission.getName()))
                          .collect(Collectors.toSet()));
                  return role;
                })
            .collect(Collectors.toList());

    roleRepository.saveAll(roles);

    User adminUser =
        User.builder()
            .roles(
                adminRolePermissions.keySet().stream()
                    .map(
                        roleName ->
                            roles.stream()
                                .filter(role -> role.getName().equals(roleName))
                                .findFirst()
                                .get())
                    .collect(Collectors.toSet()))
            .build();
    userRepository.save(adminUser);

    Account adminAccount = Account.builder().user(adminUser).status(AccountStatus.ACTIVE).build();
    accountRepository.save(adminAccount);
    adminUser.setAccount(adminAccount);
    userRepository.save(adminUser);

    LocalProvider adminLocalProvider =
        LocalProvider.builder()
            .email("admin@health-guardian.com")
            .passwordHash(passwordEncoder.encode("Password@123"))
            .account(adminAccount)
            .build();
    localProviderRepository.save(adminLocalProvider);
  }

  @Override
  public void mock() {
    List<Patient> patients = patientRepository.findAll();
    List<UserMedicalStaff> medicalStaff = userMedicalStaffRepository.findAll();

    for (Patient patient : patients) {
      for (UserMedicalStaff doctor : medicalStaff) {
        Appointment appointment =
            new Appointment(
                faker.idNumber().valid(),
                patient,
                doctor,
                faker.date().future(30, TimeUnit.DAYS),
                "Checkup",
                AppointmentStatus.SCHEDULED);
        appointmentRepository.save(appointment);
      }
    }
  }
}
