package com.pajiniweb.oriachad_backend.actions.repositories;

import com.pajiniweb.oriachad_backend.actions.domains.entities.Script;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScriptRepository extends JpaRepository<Script, Long> {

	Optional<Script> findByCode(String code);

}