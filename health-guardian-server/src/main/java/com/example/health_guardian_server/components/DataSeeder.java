package com.example.health_guardian_server.components;

import java.time.ZoneId;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import static com.example.health_guardian_server.enums.Visibility.PUBLIC;
import com.example.health_guardian_server.entities.Account;
import com.example.health_guardian_server.entities.Profile;
import com.example.health_guardian_server.repositories.AccountRepository;
import com.example.health_guardian_server.repositories.ProfileRepository;
import com.github.javafaker.Faker;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class DataSeeder {
                        AccountRepository accountRepository;
                        ProfileRepository profileRepository;

                        @Bean
                        public void seedData() {
                                                Faker faker = new Faker();
                                                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                                                Account admin = Account.builder().username("admin").password(
                                                                                                passwordEncoder.encode("123456"))
                                                                                                .email("nguyenthinhphat3009@gmail.com")
                                                                                                .isActivated(false)
                                                                                                .build();
                                                accountRepository.save(admin);
                                                for (int i = 0; i < 10; i++) {
                                                                        Account account = Account.builder()
                                                                                                                        .username(faker.name().username())
                                                                                                                        .password(passwordEncoder.encode("123456"))
                                                                                                                        .email(faker.internet().emailAddress())
                                                                                                                        .isActivated(true)
                                                                                                                        .build();
                                                                        accountRepository.save(account);
                                                                        Profile profile = Profile.builder()
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
                                                                        profileRepository.save(profile);
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
