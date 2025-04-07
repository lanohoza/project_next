package com.pajiniweb.oriachad_backend.repositories;

import com.pajiniweb.oriachad_backend.domains.entities.Audience;
import com.pajiniweb.oriachad_backend.domains.entities.Desire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface DesireRepository extends JpaRepository<Desire, Long> {


	Optional<Desire> getByIdUserAndIdYearAndIdStudent(Long idUser, Long idYear, Long idStudent);

@Transactional
	@Modifying
	@Query("DELETE FROM Desire desire WHERE desire.id = :idDesire AND SIZE(desire.desireSpecialties) = 0")
	void deleteByCountDesireSpecialty(Long idDesire);
}