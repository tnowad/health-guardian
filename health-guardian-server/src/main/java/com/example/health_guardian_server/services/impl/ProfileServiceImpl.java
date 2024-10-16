package com.example.health_guardian_server.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.health_guardian_server.entities.Profile;
import com.example.health_guardian_server.repositories.ProfileRepository;
import com.example.health_guardian_server.services.ProfileService;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class ProfileServiceImpl implements ProfileService {

  ProfileRepository profileRepository;

  @Override
  public List<Profile> findAll() {
    return profileRepository.findAll();
  }

  @Override
  public Profile create(Profile profile) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'create'");
  }

}
