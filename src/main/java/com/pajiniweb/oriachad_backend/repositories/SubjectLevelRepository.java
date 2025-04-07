package com.pajiniweb.oriachad_backend.repositories;

import com.pajiniweb.oriachad_backend.domains.entities.Subject;
import com.pajiniweb.oriachad_backend.domains.entities.SubjectLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectLevelRepository extends JpaRepository<SubjectLevel, Long> {


	List<SubjectLevel> findAllByIdSpecialityAndIdLevel(Long idSpeciality, Long idLevel);
	}