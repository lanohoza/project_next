package com.pajiniweb.oriachad_backend.repositories;

import com.pajiniweb.oriachad_backend.domains.entities.Interview;
import com.pajiniweb.oriachad_backend.domains.enums.InterviewStatus;
import com.pajiniweb.oriachad_backend.domains.enums.InterviewType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long> {

	@Query("select i from Interview as i " +
			"left join i.guidanceGroup as g " +
			"left join i.student as s " +
			"where i.idYear = :idYear " +
			"and i.idCreatedBy = :idUser " +
			"and (:status IS NULL OR i.status = :status) " +
			"and (:type IS null or  i.type = :type) " +
			"and ((g IS NOT NULL AND LOWER(g.title) LIKE LOWER(CONCAT('%', :search, '%'))) " +
			"or (s IS NOT NULL AND LOWER(CONCAT(s.firstName, ' ', s.lastName)) LIKE LOWER(CONCAT('%', :search, '%'))))")
	Page<Interview> search(String search, Long idYear, InterviewType type, InterviewStatus status, Long idUser, Pageable pageable);
	@Query("SELECT  MAX(i.number) " +
			"FROM Interview i " +
			"WHERE i.idCreatedBy = :idUser " )
	Optional<Long> lastNumberOfInterview(Long idUser);
	@Query("SELECT i.createdDate, i FROM Interview i WHERE i.idCreatedBy=:idUser " +
			"AND i.idYear = :idYear " +
			"ORDER BY i.createdDate")
	List<Object[]> getInterviewsByYear(@Param("idUser") Long idUser, @Param("idYear") Long idYear);

}