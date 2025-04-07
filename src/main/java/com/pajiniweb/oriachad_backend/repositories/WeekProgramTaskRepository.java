package com.pajiniweb.oriachad_backend.repositories;

import com.pajiniweb.oriachad_backend.domains.entities.WeekProgramTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WeekProgramTaskRepository extends JpaRepository<WeekProgramTask, Long> {

    @Query("SELECT w FROM WeekProgramTask w WHERE w.idWeekProgram = :idWeekProgram ")
    List<WeekProgramTask> findByIdWeekProgram(@Param("idWeekProgram") Long idWeekProgram);
}