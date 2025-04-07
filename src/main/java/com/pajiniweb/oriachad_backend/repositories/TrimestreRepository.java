package com.pajiniweb.oriachad_backend.repositories;

import com.pajiniweb.oriachad_backend.domains.entities.Trimestre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TrimestreRepository extends JpaRepository<Trimestre, Long> {
    List<Trimestre> findAllByIdYear(Long idYear);
}