package com.pajiniweb.oriachad_backend.administration.repositories;

import com.pajiniweb.oriachad_backend.administration.domains.entities.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long> {
}