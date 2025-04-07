package com.pajiniweb.oriachad_backend.administration.repositories;

import com.pajiniweb.oriachad_backend.administration.domains.entities.PlanUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlanUserRepository extends JpaRepository<PlanUser, Long> {

    @Query("SELECT pu FROM PlanUser pu WHERE pu.idUser = :idUser")
    List<PlanUser> findByIdUser(Long idUser);

    @Query("SELECT pu FROM PlanUser pu WHERE pu.idPlan = :idPlan")
    List<PlanUser> findByIdPlan(Long idPlan);

    @Query("SELECT pu FROM PlanUser pu WHERE pu.idUser = :idUser AND pu.idPlan = :idPlan")
    List<PlanUser> findByIdUserAndIdPlan(Long idUser, Long idPlan);
}