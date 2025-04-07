package com.pajiniweb.oriachad_backend.repositories;

import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCE002.projections.TCE002ResultBySubject;
import com.pajiniweb.oriachad_backend.domains.entities.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {
	List<Result> findByStudentIdAndTrimestreId(Long idStudent, Long idTrimestre);

	@Query("SELECT new com.pajiniweb.oriachad_backend.actions.domains.scripts.TCE002.projections.TCE002ResultBySubject( " +
			"r.subjectLevel.subject, SUM(r.value) / COUNT(r)) " +
			"FROM Result r " +
			"WHERE r.student.id = :idStudent " +
			"AND r.trimestre.idYear = :idYear " +
			"AND r.trimestre.number IN (1, 2, 3) " +
			"GROUP BY r.subjectLevel.subject")
	List<TCE002ResultBySubject> findByResultOfYear(Long idStudent, Long idYear);


	void deleteByStudentIdAndTrimestreId(Long idStudent, Long idTrimestre);

	Optional<Result> findByStudentIdAndTrimestreIdAndIdSubjectLevel(Long idStudent, Long idTrimestre, Long idSubjectLevel);
	@Query("SELECT r.value " +
			"FROM Result r " +
			"WHERE r.student.id = :idStudent " +
			"AND r.trimestre.idYear = :idYear " +
			"AND r.trimestre.number = :trimesetNumber " +
			"AND r.subjectLevel.idSubject=:idSubject")

    Optional<Double> findSubjectNote(Long idStudent,Long idSubject, Long idYear, Integer trimesetNumber);

}