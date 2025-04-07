package com.pajiniweb.oriachad_backend.actions.repositories;

import com.pajiniweb.oriachad_backend.actions.domains.entities.ShedCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShedCategoryRepository extends JpaRepository<ShedCategory, Long> {
}
