package com.pajiniweb.oriachad_backend.repositories;

import com.pajiniweb.oriachad_backend.domains.entities.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

	/* @Query("SELECT t FROM Task t WHERE t.start >= :startOfWeek AND t.start <= :endOfWeek")
	 Page<Task> findTasksForCurrentWeek(@Param("startOfWeek") LocalDate startOfWeek, @Param("endOfWeek") LocalDate endOfWeek, Pageable pageable);
 */
	@Query("SELECT t FROM Task t WHERE t.technicalCard.id = :idTask")
	List<Task> findByIdTechnicalCard(Long idTask);


	@Query("SELECT t FROM Task t WHERE t.idYear = :idYear and t.idCreatedBy= :idUser")
	List<Task> findByIdYear(@Param("idYear") Long idYear,@Param("idUser") Long idUser);

	@Query("SELECT t FROM Task t WHERE t.technicalCard.createdBy.id = :idUser")
	List<Task> findByUser(@Param("idUser") Long idUser);

	@Query("SELECT t FROM Task t WHERE t.idCreatedBy = :idUser "
			+ "AND t.idYear= :idYear "
			+ "and ( (t.technicalCard.runMonth=:month  and t.technicalCard.runWeek=:week) or t.technicalCard.type=com.pajiniweb.oriachad_backend.domains.enums.TypeTc.permanent)  " +
			"and t.status <> com.pajiniweb.oriachad_backend.domains.enums.TaskStatus.finish  order by t.technicalCard.type ")
	List<Task> getCurrentWeekTasks(@Param("idYear") Long idYear, @Param("idUser") Long idUser, @Param("month") int month, @Param("week") int week);

	@Query("SELECT ts FROM Task ts WHERE ts.idCreatedBy = :idUser " + "AND ts.idYear=:idYear " + "AND  LOWER(ts.technicalCard.title) LIKE LOWER(CONCAT('%', :search, '%')) " + "AND (:idTcCategory = -1 OR  ts.technicalCard.category.id=:idTcCategory) " + "AND (:month = -1 OR  ts.technicalCard. runMonth=:month)")
	Page<Task> yearProgramSearch(@Param("search") String search, @Param("idTcCategory") Long idTcCategory, @Param("month") int month, @Param("idYear") Long idYear, @Param("idUser") Long idUser, Pageable pageable);
	@Query("SELECT ts FROM Task ts WHERE ts.status=com.pajiniweb.oriachad_backend.domains.enums.TaskStatus.finish "
			+ "AND ts.idCreatedBy = :idUser "
			+ "AND ts.idYear=:idYear " + "AND  LOWER(ts.technicalCard.title) LIKE LOWER(CONCAT('%', :search, '%')) "
			+ "AND (:idTcCategory = -1 OR  ts.technicalCard.category.id=:idTcCategory) "
			+ "AND (:month = -1 OR  ts.technicalCard. runMonth=:month)")
	Page<Task> donneSearch(@Param("search") String search, @Param("idTcCategory") Long idTcCategory, @Param("month") int month, @Param("idYear") Long idYear, @Param("idUser") Long idUser, Pageable pageable);

	@Query("SELECT ts FROM Task ts  where ts.idCreatedBy=:idUser and ts.status= com.pajiniweb.oriachad_backend.domains.enums.TaskStatus.finish" +
			" AND  ts.createdDate >= :start " +
			" AND  ts.createdDate <= :end ")
	List<Task> getFinishedTaskByDates(@Param("idUser") Long idUser, @Param("start") LocalDate start, @Param("end") LocalDate end);

	@Query("SELECT ts FROM Task ts WHERE ts.idCreatedBy = :idUser " +
			"AND ts.idYear = :idYear ")
	List<Task> getAllByUserAndYear(@Param("idYear") Long idYear, @Param("idUser") Long idUser);

	@Query("SELECT ts FROM Task ts WHERE ts.idCreatedBy = :idUser " +
			"AND ts.technicalCard.type = com.pajiniweb.oriachad_backend.domains.enums.TypeTc.permanent " +
			"AND ts.idYear = :idYear ")
	List<Task> getAllPermanentByUserAndYear(@Param("idYear") Long idYear, @Param("idUser") Long idUser);
}
