package com.pajiniweb.oriachad_backend.repositories;

import com.pajiniweb.oriachad_backend.domains.entities.Establishment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EstablishmentRepository extends JpaRepository<Establishment, Long> {
    @Query("SELECT e FROM Establishment e WHERE e.idCommune = :idCommune")
    List<Establishment> findByIdCommune(Long idCommune);
   boolean existsByCode(String code);
}