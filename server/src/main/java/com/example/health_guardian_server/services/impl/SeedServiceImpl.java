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
  SettingRepository settingRepository;
  PasswordEncoder passwordEncoder;

  Faker faker = new Faker();

  @Override
  @Transactional
  public void clear() {
    settingRepository.deleteAllInBatch();
    permissionRepository.deleteAllInBatch();
    roleRepository.deleteAllInBatch();

    userMedicalStaffRepository.deleteAllInBatch();
    userPatientRepository.deleteAllInBatch();
    userStaffRepository.deleteAllInBatch();

    localProviderRepository.deleteAllInBatch();
    accountRepository.deleteAllInBatch();
    userRepository.deleteAllInBatch();

    aggregatedSideEffectRepository.deleteAllInBatch();
    appointmentRepository.deleteAllInBatch();
    consentFormRepository.deleteAllInBatch();
    externalProviderRepository.deleteAllInBatch();
    guardianRepository.deleteAllInBatch();
    hospitalRepository.deleteAllInBatch();
    householdMemberRepository.deleteAllInBatch();
    householdRepository.deleteAllInBatch();
    medicationRepository.deleteAllInBatch();
    notificationRepository.deleteAllInBatch();
    patientLogRepository.deleteAllInBatch();
    patientRepository.deleteAllInBatch();
    prescriptionRepository.deleteAllInBatch();
    reportedSideEffectRepository.deleteAllInBatch();
    sideEffectRepository.deleteAllInBatch();

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

    Map<String, List<String>> anonymousRolePermissions = new HashMap<>();
    anonymousRolePermissions.put(
        "anonymous",
        Arrays.asList(
            PermissionName.ACCOUNT_CREATE_OWN.name(),
            PermissionName.ACCOUNT_SIGNIN_OWN.name(),
            PermissionName.ACCOUNT_SIGNOUT_OWN.name()));

    Map<String, List<String>> adminRolePermissions = new HashMap<>();
    adminRolePermissions.put(
        "admin",
        Arrays.asList(
            PermissionName.VIEW_MAIN_DASHBOARD.name(),
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

    permissionRoleMapping.add(anonymousRolePermissions);
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

    List<Setting> settings = new ArrayList<>();
    Setting roleSetting =
        Setting.builder()
            .key(SettingKey.ROLE_DEFAULT_IDS)
            .description("Default role for new users")
            .type(SettingType.STRING_ARRAY)
            .build();

    roleSetting.setStringArrayValue(
        roles.stream()
            .filter(role -> role.getName().equals("anonymous"))
            .map(Role::getId)
            .collect(Collectors.toList()));

    settings.add(roleSetting);

    settingRepository.saveAll(settings);

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
            .type(UserType.STAFF)
            .build();
    userRepository.save(adminUser);

    Account adminAccount = Account.builder().user(adminUser).status(AccountStatus.ACTIVE).build();
    accountRepository.save(adminAccount);
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
    List<Medication> medications = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      String name = faker.medical().medicineName();
      String activeIngredient = faker.medical().symptoms();
      String dosageForm = faker.options().option("Tablet", "Capsule", "Injection", "Syrup");
      String standardDosage = faker.number().numberBetween(10, 500) + " mg";
      String manufacturer = faker.company().name();
      medications.add(
          Medication.builder()
              .name(name)
              .activeIngredient(activeIngredient)
              .dosageForm(dosageForm)
              .standardDosage(standardDosage)
              .manufacturer(manufacturer)
              .build());
    }
    medicationRepository.saveAll(medications);
  }
}
