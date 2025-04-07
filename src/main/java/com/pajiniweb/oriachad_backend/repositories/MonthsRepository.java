package com.pajiniweb.oriachad_backend.repositories;

import com.pajiniweb.oriachad_backend.domains.entities.Months;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonthsRepository extends JpaRepository<Months, Long> {
}