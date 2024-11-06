package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.entities.Role;
import com.example.health_guardian_server.repositories.RoleRepository;
import com.example.health_guardian_server.services.RoleService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

  RoleRepository roleRepository;

  @Override
  public List<Role> getRolesByUserId(String userId) {
    return roleRepository.findAllByUserId(userId);
  }
}
