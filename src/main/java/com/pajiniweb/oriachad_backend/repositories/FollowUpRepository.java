package com.pajiniweb.oriachad_backend.repositories;

import com.pajiniweb.oriachad_backend.domains.entities.FollowUp;
import com.pajiniweb.oriachad_backend.domains.enums.FollowupStatus;
import com.pajiniweb.oriachad_backend.domains.enums.FollowUpType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowUpRepository extends JpaRepository<FollowUp, Long> {

	@Query("select f from FollowUp as f " +
			"left join f.guidanceGroup as g " +
			"left join f.student as s " +
			"where f.idYear = :idYear " +
			"and f.idCreatedBy = :idUser " +
			"and (:status IS NULL OR f.status = :status) " +
			"and (:type IS NULL OR f.type = :type) " +
			"and ((g IS NOT NULL AND LOWER(g.title) LIKE LOWER(CONCAT('%', :search, '%'))) " +
			"or (s IS NOT NULL AND LOWER(CONCAT(s.firstName, ' ', s.lastName)) LIKE LOWER(CONCAT('%', :search, '%'))))")
	Page<FollowUp> search(String search, Long idYear, FollowUpType type, FollowupStatus status, Long idUser, Pageable pageable);

	@Query("SELECT  MAX(f.number) " + "FROM FollowUp f " + "WHERE f.idCreatedBy = :idUser ")
	Optional<Long> lastNumberOfFlowUp(Long idUser);

	@Query("select f from FollowUp as f " +
			"where  f.idYear=:idYear " +
			" and  f.idCreatedBy=:idUser "+
			" and  f.type=:type " +
			"and f.status <> com.pajiniweb.oriachad_backend.domains.enums.FollowupStatus.done"
	)
	List<FollowUp> getFlowUpCurrentYear(FollowUpType type, Long idYear, Long idUser);
	@Query("SELECT i.createdDate, i FROM FollowUp i WHERE i.idCreatedBy=:idUser " +
			"AND i.idYear = :idYear " +
			"ORDER BY i.createdDate")
	List<Object[]> getFollowUpByYear(Long idUser, Long idYear);
}