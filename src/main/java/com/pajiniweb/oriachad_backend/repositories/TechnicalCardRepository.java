package com.pajiniweb.oriachad_backend.repositories;

import com.pajiniweb.oriachad_backend.domains.entities.TechnicalCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TechnicalCardRepository extends JpaRepository<TechnicalCard, Long> {

    @Query("SELECT tc FROM TechnicalCard tc WHERE tc.idCreatedBy = :idUser " +
            "AND  LOWER(tc.title) LIKE LOWER(CONCAT('%', :search, '%')) "+
			"AND (:idTcCategory = -1 OR  tc.category.id=:idTcCategory)"+
			"AND (:month = -1 OR  tc.runMonth=:month)"
	)
    Page<TechnicalCard> findByCreatedBy(@Param("search") String search,@Param("idTcCategory") Long idTcCategory,@Param("month") int month,@Param("idUser") Long idUser, Pageable pageable);
    @Query("SELECT CASE WHEN COUNT(ts) > 0 THEN true ELSE false END " +
            "FROM TechnicalCard tc " +
            "JOIN tc.tasks  ts " +
            "WHERE tc.id = :id "+
			"and ts.status <> 'todo' ")
    boolean canEdit(@Param("id") Long id);
	@Query("SELECT CASE WHEN COUNT(ts) > 0 THEN true ELSE false END " +
			"FROM TechnicalCard tc " +
			"JOIN tc.tasks  ts " +
			"WHERE tc.id = :id  and ts.status <> com.pajiniweb.oriachad_backend.domains.enums.TaskStatus.todo")
	boolean canDelete(@Param("id") Long id);
	@Query("SELECT tc " +
			"FROM TechnicalCard tc " +
			"WHERE tc.idCreatedBy = :idUser " +
			"AND LOWER(tc.title) LIKE LOWER(CONCAT('%', :search, '%')) " +
			"AND NOT EXISTS (" +
			"  SELECT ts " +
			"  FROM Task ts " +
			"  WHERE ts.technicalCard = tc " +
			"  AND ts.idYear = :idYear" +
			")")
	Page<TechnicalCard>  findAllWithNotTask(@Param("search")String search,
											@Param("idUser") Long idUser,
											@Param("idYear") Long idYear,
											Pageable pageable);

	List<TechnicalCard> findByIdIn(List<Long> ids);
	@Query("SELECT  MAX(tc.number) " +
			"FROM TechnicalCard tc " +
			"WHERE tc.idCreatedBy = :idUser " +
			"And tc.idTcCategory=:cardCategoryId " +
			"And tc.defaultTc=false")
	Optional<Long> lastNumberOfTechnicalCard(Long idUser, Long cardCategoryId);

	List<TechnicalCard> findByCode(String code);
	boolean existsByCodeAndIdCreatedBy(String code,Long idUser);
	List<TechnicalCard> findByIdCreatedBy(Long idUser);
}


