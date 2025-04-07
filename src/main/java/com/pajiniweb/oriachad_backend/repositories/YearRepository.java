package com.pajiniweb.oriachad_backend.repositories;

import com.pajiniweb.oriachad_backend.domains.entities.Year;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
@Repository
public interface YearRepository extends JpaRepository<Year, Long> {

    @Query("SELECT y FROM Year y WHERE :today BETWEEN y.start AND y.end")
    Optional<Year> findCurrentYear(@Param("today") LocalDate today);

    Optional<Year> findFirstByCurrentIsTrue();


    Optional<Year> findFirstByOrder(Integer order);
}