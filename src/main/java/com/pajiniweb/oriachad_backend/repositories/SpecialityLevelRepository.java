package com.pajiniweb.oriachad_backend.repositories;

import com.pajiniweb.oriachad_backend.domains.entities.Speciality;
import com.pajiniweb.oriachad_backend.domains.entities.SpecialityLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpecialityLevelRepository extends JpaRepository<SpecialityLevel, Long> {


}