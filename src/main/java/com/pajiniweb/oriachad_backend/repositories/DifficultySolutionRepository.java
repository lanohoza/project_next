package com.pajiniweb.oriachad_backend.repositories;

import com.pajiniweb.oriachad_backend.domains.entities.DifficultySolution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DifficultySolutionRepository extends JpaRepository<DifficultySolution, Long> {

    @Query("SELECT ds FROM DifficultySolution ds WHERE ds.idDifficulty.id = :difficultyId")
    List<DifficultySolution> findByDifficultyId(Long difficultyId);

    @Query("SELECT ds FROM DifficultySolution ds WHERE ds.idDifficulty.id = :difficultyId AND ds.createdBy.id = :userId")
    List<DifficultySolution> findByDifficultyIdAndUserId(Long difficultyId, Long userId);
}