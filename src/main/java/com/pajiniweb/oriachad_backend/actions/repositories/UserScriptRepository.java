package com.pajiniweb.oriachad_backend.actions.repositories;

import com.pajiniweb.oriachad_backend.actions.domains.entities.UserScript;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserScriptRepository extends JpaRepository<UserScript, Long> {

	@Query("SELECT us FROM UserScript us where  us.idUser=:idUser and us.script.code=:code and us.idYear=:idYear")
	Optional<UserScript> findByUserIdAndCode(Long idUser,Long idYear, String code);
	@Modifying
	@Transactional
	@Query("DELETE FROM UserScript us WHERE us.idUser= :idUser AND us.idYear=:idYear and us.idScript = :idScript")
	void delete(Long idUser, Long idYear, Long idScript);
}