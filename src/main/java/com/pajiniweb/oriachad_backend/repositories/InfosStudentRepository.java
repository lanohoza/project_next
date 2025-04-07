package com.pajiniweb.oriachad_backend.repositories;

import com.pajiniweb.oriachad_backend.domains.entities.InfosStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface InfosStudentRepository extends JpaRepository<InfosStudent, Long> {
    Optional<InfosStudent> findByIdStudent(Long id);
    boolean existsByIdStudent(Long id);
    Integer deleteByIdStudent(Long id);
}