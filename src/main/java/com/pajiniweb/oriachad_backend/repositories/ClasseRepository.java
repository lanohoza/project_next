package com.pajiniweb.oriachad_backend.repositories;

import com.pajiniweb.oriachad_backend.domains.dtos.CurrentStudentDto;
import com.pajiniweb.oriachad_backend.domains.dtos.StudentDto;
import com.pajiniweb.oriachad_backend.domains.entities.Classe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClasseRepository extends JpaRepository<Classe, Long> {
	Page<Classe> findByIdYearAndIdEstablishmentAndTitleContainingOrNumber(Long idYear, Long idEstablishment, String title, Integer number, Pageable pageable);

	List<Classe> findByIdYearAndIdEstablishment(Long idYear, Long idEstablishment);
	@Query("SELECT new com.pajiniweb.oriachad_backend.domains.dtos.CurrentStudentDto(sc.classe.title, sc.idStudent, s.firstName, s.lastName) " +
			"FROM Classe c " +
			"JOIN c.studentClasses sc " +
			"JOIN sc.student s " +
			"WHERE c.idYear = :yearId " +
			"AND c.idEstablishment = :establishmentId")
	List<CurrentStudentDto> findStudentDtosByYearAndEstablishment(Long yearId, Long establishmentId);
	Optional<Classe> getFirstByIdYearAndIdEstablishmentAndNumberAndIdLevelAndIdSpeciality(Long idYear, Long idEstablishment, int number, Long idLevel, Long idSpeciality);

	int countByIdLevelAndIdYearAndIdEstablishment(Long idLevel, Long idYear, Long idEstablishment);

	int countByIdSpecialityAndIdYearAndIdEstablishment(Long idSpeciality, Long idYear, Long idEstablishment);

	List<Classe> getAllByIdYearAndIdEstablishment(Long idYear,Long idEstablishment);
	List<Classe> getAllByIdYearAndIdEstablishmentAndIdLevel(Long idYear,Long idEstablishment,Long idLevel);

	@Query("select c from Classe as c " +
			"left  join c.studentClasses as sc " +
			"where  sc.idStudent=:idStudent "+
			"and  c.idYear=:idYear"
	)
	Classe findByIdYearAndStudent(Long idYear, Long idStudent);
	List<Classe> findByIdYearAndIdEstablishmentAndIdProfessorIsNotNull(Long idYear, Long idEstablishment);

	List<Classe> findByIdEstablishment(Long idEstablishment);

	List<Classe> findByIdProfessor(Long idProfessor);

}