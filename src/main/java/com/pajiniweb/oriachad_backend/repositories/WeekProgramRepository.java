package com.pajiniweb.oriachad_backend.repositories;

import com.pajiniweb.oriachad_backend.domains.entities.WeekProgram;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WeekProgramRepository extends JpaRepository<WeekProgram, Long> {

    @Query("SELECT wp FROM WeekProgram wp WHERE wp.createdBy.id = :userId")
    Page<WeekProgram> findByCreatedById(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT wp FROM WeekProgram wp WHERE wp.idCreatedBy = :idUser AND wp.idMonth = :month AND wp.weekNumber = :weekNumber")
    WeekProgram findByMonthAndWeekNumberAndUser(@Param("idUser") Long idUser,@Param("month") int month, @Param("weekNumber") int weekNumber);

}