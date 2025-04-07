package com.pajiniweb.oriachad_backend.repositories;

import com.pajiniweb.oriachad_backend.domains.entities.GeneralObjective;
import com.pajiniweb.oriachad_backend.domains.enums.SourceTechnicalCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GeneralObjectiveRepository extends JpaRepository<GeneralObjective, Long> {
    @Query("SELECT go FROM GeneralObjective go WHERE go.createdBy.id = :userId")
    Page<GeneralObjective> findByCreatedBy(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT go FROM GeneralObjective go WHERE (go.idCreatedBy = :userId or go.idCreatedBy is null) AND go.source = com.pajiniweb.oriachad_backend.domains.enums.SourceTechnicalCard.user")
    List<GeneralObjective> findByCreatedBy(@Param("userId") Long userId);

    @Query("SELECT go FROM GeneralObjective go WHERE (go.idAdmin = :adminId or go.idAdmin is null) AND go.source = com.pajiniweb.oriachad_backend.domains.enums.SourceTechnicalCard.admin")
    List<GeneralObjective> findCreatedByAdmin(@Param("adminId") Long adminId);

    @Query("SELECT CASE WHEN COUNT(tc) > 0 THEN true ELSE false END " +
            "FROM GeneralObjective go " +
            "JOIN go.technicalCards tc " +
            "WHERE go.id = :generalObjectiveId")
    boolean existsWithTechnicalCard(@Param("generalObjectiveId") Long generalObjectiveId);

    Optional<GeneralObjective> findByContentAndIdCreatedBy(String title, Long idUser);
}
