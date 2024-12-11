package com.example.health_guardian_server.repositories;

import com.example.health_guardian_server.entities.Household;
import com.example.health_guardian_server.entities.HouseholdMember;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseholdMemberRepository
    extends JpaRepository<HouseholdMember, String>, JpaSpecificationExecutor<HouseholdMember> {
  Optional<HouseholdMember> findByIdAndHousehold(String id, Household household);
  List<HouseholdMember> findAllByHousehold(Household household);
}
