package com.pajiniweb.oriachad_backend.repositories;

import aj.org.objectweb.asm.commons.Remapper;
import com.pajiniweb.oriachad_backend.domains.dtos.ProfessorDto;
import com.pajiniweb.oriachad_backend.domains.entities.Professor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {

    @Query("select p from Professor as p   " +
            "where (:search IS NULL OR LOWER(CONCAT(p.firstName,' ', p.lastName)) LIKE LOWER(CONCAT('%', :search, '%')))" +
            "and  p.idEstablishment=:idEstablishment " +
            "and  p.idCreatedBy=:idUser " +
            "ORDER BY p.firstName, p.lastName" )
    Page<Professor> search(String search,Long idEstablishment,Long idUser, Pageable pageable);

    List<Professor> findAllByIdEstablishmentAndIdCreatedBy(Long idEstablishment,Long idUser );

    @Query("select c.professor from Classe as c   " +
            "where  c.idEstablishment=:idEstablishment " +
            "and  c.idYear=:idYear " +
            "and c.idProfessor <> null" )
    List<Professor> findAllActive(Long idEstablishment,Long idYear);

    List<Professor> findAllByCoordinatorAndIdEstablishmentAndIdCreatedBy(Boolean coordinator,Long idEstablishment,Long idUser );
}