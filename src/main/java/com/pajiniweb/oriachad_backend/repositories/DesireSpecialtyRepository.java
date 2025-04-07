package com.pajiniweb.oriachad_backend.repositories;

import com.pajiniweb.oriachad_backend.domains.entities.DesireSpecialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface DesireSpecialtyRepository extends JpaRepository<DesireSpecialty, Long> {

	@Modifying
	@Transactional
	@Query("DELETE FROM DesireSpecialty desireSpecialty WHERE desireSpecialty.idDesire=:idDesire " +
			"and (desireSpecialty.order= :order or desireSpecialty.idGuidanceSpeciality = :idGuidanceSpeciality)")
	void deleteByOrderOrGuidanceSpeciality(Long idDesire,Integer order, Long idGuidanceSpeciality);
	@Modifying
	@Transactional
	@Query("DELETE FROM DesireSpecialty desireSpecialty WHERE desireSpecialty.idYear=:idYear " +
			"and desireSpecialty.idGuidanceSpeciality = :idGuidanceSpeciality " +
			"and desireSpecialty.idStudent=:idStudent " +
			"and desireSpecialty.idUser=:idUser")
	void deleteByGuidanceSpeciality(Long idYear, Long idUser, Long idStudent, Long idGuidanceSpeciality);
	@Query("select desireSpecialty FROM DesireSpecialty desireSpecialty WHERE desireSpecialty.idYear=:idYear " +
			"and desireSpecialty.idStudent=:idStudent " +
			"and desireSpecialty.idUser=:idUser")
	List<DesireSpecialty> findAllByStudent(Long idYear, Long idUser, Long idStudent);

	@Query("select desireSpecialty.idDesire FROM DesireSpecialty desireSpecialty WHERE desireSpecialty.idYear=:idYear " +
			"and desireSpecialty.idGuidanceSpeciality = :idGuidanceSpeciality " +
			"and desireSpecialty.idStudent=:idStudent " +
			"and desireSpecialty.idUser=:idUser")
	Optional<Long> findByIdDesire(Long idYear, Long idUser, Long idStudent, Long idGuidanceSpeciality);

	@Query("select desireSpecialty FROM DesireSpecialty desireSpecialty WHERE desireSpecialty.idYear=:idYear " +
			"and desireSpecialty.order=:order  " +
			"and desireSpecialty.idStudent=:idStudent " +
			"and desireSpecialty.idUser=:idUser")
	DesireSpecialty findByOrder(Long idYear, Long idUser, Long idStudent,Integer order);
	@Query("select count(desireSpecialty.id) > 0 FROM DesireSpecialty desireSpecialty WHERE desireSpecialty.idYear=:idYear " +
			"and desireSpecialty.order=:order " +
			"and desireSpecialty.idGuidanceSpeciality = :idGuidanceSpeciality " +
			"and desireSpecialty.idStudent=:idStudent " +
			"and desireSpecialty.idUser=:idUser")
	Boolean isOrder(Long idYear, Long idUser, Long idStudent, Long idGuidanceSpeciality, Integer order);

}