package com.pajiniweb.oriachad_backend.actions.repositories;

import com.pajiniweb.oriachad_backend.actions.domains.entities.ActionTask;
import com.pajiniweb.oriachad_backend.actions.domains.enums.ActionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionTaskRepository extends JpaRepository<ActionTask, Long> {


	@Query("SELECT COUNT(ats) > 0 FROM ActionTask  as at " +
			"left join at.action as ac  " +
			"left join ac.dependencies as dp " +
			"left join dp.actionTasks ats WHERE at.id = :idActionTask AND ats.status <> :status")
	boolean existsDependenciesNotStatus(@Param("idActionTask") Long idActionTask, @Param("status") ActionStatus status);
	@Query("SELECT COUNT(ats) > 0 FROM ActionTask  as at " +
			"left join at.task as tc  " +
			"left join tc.actions as ats "+
			"WHERE at.id = :idActionTask AND ats.status <> :status")
	boolean allRelatedActionTaskNotStatus(@Param("idActionTask") Long idActionTask, @Param("status") ActionStatus status);
}

