package com.example.health_guardian_server.services;

import java.util.List;

import com.example.health_guardian_server.dtos.requests.CreateProfileRequest;
import com.example.health_guardian_server.entities.Profile;

public interface ProfileService {

  List<Profile> findAll();

  Profile getCurrentProfile();

  Profile create(CreateProfileRequest createProfileRequest);
}
