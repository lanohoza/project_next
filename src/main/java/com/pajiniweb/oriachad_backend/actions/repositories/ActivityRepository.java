package com.pajiniweb.oriachad_backend.actions.repositories;

import com.pajiniweb.oriachad_backend.actions.domains.entities.Activity;
import com.pajiniweb.oriachad_backend.actions.domains.enums.ActivityType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

	@Query("SELECT act FROM Activity as  act " +
			"WHERE act.idUser = :idUser " +
			"AND (:search IS NULL OR LOWER(act.content) LIKE LOWER(CONCAT('%', :search, '%'))) " +
			"AND (:startDate IS NULL OR act.createdDate >= :startDate)" +
			"AND (:endDate IS NULL OR act.createdDate <= :endDate) ")
	Page<Activity> searchActivities(@Param("search") String search, @Param("idUser") Long idUser, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, Pageable pageable);

	@Query("SELECT at FROM Activity at  where at.idUser=:idUser and at.createdDate=:day")
	List<Activity> getActivitiesByDay(@Param("idUser") Long idUser, @Param("day") LocalDate day);


	// In your repository interface
	@Query("SELECT at.createdDate, at FROM Activity at WHERE at.idUser=:idUser " +
			"AND at.createdDate >= :start " +
			"AND at.createdDate <= :end " +
			"ORDER BY at.createdDate")
	List<Object[]> getActivitiesByYear(@Param("idUser") Long idUser, @Param("start") LocalDate start, @Param("end") LocalDate end);


	@Query("SELECT CASE WHEN COUNT(at) > 0 THEN TRUE ELSE FALSE END " +
			"FROM Activity at " +
			"WHERE at.idUser = :idUser " +
			"AND at.type = :type " +
			"AND at.createdDate = :today")
	boolean hasTypeToday(@Param("idUser") Long idUser,
						 @Param("type") ActivityType type,
						 @Param("today") LocalDate today);}

