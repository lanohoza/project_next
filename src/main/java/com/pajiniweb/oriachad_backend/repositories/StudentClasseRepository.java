package com.pajiniweb.oriachad_backend.repositories;

import com.pajiniweb.oriachad_backend.domains.dtos.StudentDto;
import com.pajiniweb.oriachad_backend.domains.entities.StudentClasse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentClasseRepository extends JpaRepository<StudentClasse, Long> {

	Optional<StudentClasse> findByIdStudentAndIdClasse(Long idStudent, Long idClasse);


	Optional<StudentClasse> findByIdStudentAndClasseIdYear(Long idStudent, Long idyear);
	@Query("select sc from StudentClasse sc "
			+ "where  sc.idStudent =:idStudent and  sc.classe.year.current=true  " )
	Optional<StudentClasse> findByIdStudentCurrentYear(@Param("idStudent")  Long idStudent);

	@Modifying
	@Query("update StudentClasse sc set sc.removed= true WHERE sc.idStudent = :idStudent AND sc.idClasse = :idClass")
	void removeByIdStudentAndIdClass(@Param("idStudent") Long idStudent, @Param("idClass") Long idClass);


	@Query("select sc from StudentClasse sc   "
			+ "LEFT JOIN sc.student s "
			+ "where  sc.removed = true "
			+ "AND (:search IS NULL OR LOWER(CONCAT(s.firstName,' ', s.lastName)) LIKE LOWER(CONCAT('%', :search, '%')))"
			+ "and sc.classe.idYear=:idYear "
			+ "and   sc.classe.idEstablishment=:idEstablishment "
			+ "ORDER BY s.firstName, s.lastName")
	Page<StudentClasse> removedSearch(String search, Long idEstablishment, Long idYear, Pageable pageable);




	@Modifying
	@Query("update StudentClasse sc set sc.removed= false WHERE sc.id = :idStudentClass")
	void restoreStudent(Long idStudentClass);
}