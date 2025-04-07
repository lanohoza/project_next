package com.pajiniweb.oriachad_backend.actions.repositories;

import com.pajiniweb.oriachad_backend.actions.domains.entities.RequiredProcedures;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequiredProceduresRepository extends JpaRepository<RequiredProcedures, Long> {
}
