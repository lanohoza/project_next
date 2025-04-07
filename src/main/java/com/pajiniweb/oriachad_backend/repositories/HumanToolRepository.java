package com.pajiniweb.oriachad_backend.repositories;

import com.pajiniweb.oriachad_backend.domains.entities.HumanTool;
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
public interface HumanToolRepository extends JpaRepository<HumanTool, Long> {

    @Query("SELECT ht FROM HumanTool ht WHERE ht.createdBy.id = :userId")
    Page<HumanTool> findByCreatedBy(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT ht FROM HumanTool ht WHERE ht.idCreatedBy = :userId AND ht.source = com.pajiniweb.oriachad_backend.domains.enums.SourceTechnicalCard.user")
    List<HumanTool> findByCreatedBy(@Param("userId") Long userId);

    @Query("SELECT ht FROM HumanTool ht WHERE ht.idAdmin = :idAdmin AND ht.source = com.pajiniweb.oriachad_backend.domains.enums.SourceTechnicalCard.admin")
    List<HumanTool> findCreatedByAdmin(@Param("idAdmin") Long idAdmin);

    Optional<HumanTool> findByFirstNameAndLastNameAndIdCreatedBy(String firstName, String LastName, Long idUser);

}