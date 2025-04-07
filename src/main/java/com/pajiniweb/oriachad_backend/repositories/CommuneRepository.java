package com.pajiniweb.oriachad_backend.repositories;

import com.pajiniweb.oriachad_backend.domains.entities.Commune;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CommuneRepository extends JpaRepository<Commune, Long> {

    List<Commune> findByIdWilaya(Long idWilaya);
    Commune findByCode(String code);

}