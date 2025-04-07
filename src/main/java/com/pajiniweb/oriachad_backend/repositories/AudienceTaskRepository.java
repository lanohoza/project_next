package com.pajiniweb.oriachad_backend.repositories;

import com.pajiniweb.oriachad_backend.domains.entities.TcAudience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AudienceTaskRepository extends JpaRepository<TcAudience, Long> {

    @Query("SELECT at FROM TcAudience at WHERE at.technicalCard.id = :idTaskId")
    List<TcAudience> findByIdTechnicalCard_Id(@Param("idTaskId") Long idTaskId);
}