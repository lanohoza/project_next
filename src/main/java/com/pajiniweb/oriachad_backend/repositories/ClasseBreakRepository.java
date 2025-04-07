package com.pajiniweb.oriachad_backend.repositories;

import com.pajiniweb.oriachad_backend.domains.entities.ClasseBreak;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClasseBreakRepository extends JpaRepository<ClasseBreak, Long> {

boolean deleteByIdClasse(Long idClasse);
}