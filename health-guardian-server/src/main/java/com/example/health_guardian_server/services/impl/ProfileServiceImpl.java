package com.example.health_guardian_server.services.impl;

import java.util.List;
import org.springframework.stereotype.Service;

import com.example.health_guardian_server.dtos.requests.CreateProfileRequest;
import com.example.health_guardian_server.entities.Account;
import com.example.health_guardian_server.entities.Profile;
import com.example.health_guardian_server.repositories.ProfileRepository;
import com.example.health_guardian_server.services.AccountService;
import com.example.health_guardian_server.services.ProfileService;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class ProfileServiceImpl implements ProfileService {

  ProfileRepository profileRepository;

  AccountService accountService;

  @Override
  public List<Profile> findAll() {
    return profileRepository.findAll();
  }

  @Override
  public Profile create(CreateProfileRequest createProfileRequest) {
    throw new UnsupportedOperationException("Unimplemented method 'create'");
  }

  @Override
  public Profile getCurrentProfile() {
    Account account = accountService.getCurrentAccount();
    Profile currentProfile = account.getProfile();
    return currentProfile;
  }

}
