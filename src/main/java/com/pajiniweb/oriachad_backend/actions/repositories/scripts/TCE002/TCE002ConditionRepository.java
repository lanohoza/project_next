package com.pajiniweb.oriachad_backend.actions.repositories.scripts.TCE002;

import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCE002.DiagnosticType;
import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCE002.TCE002Condition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TCE002ConditionRepository extends JpaRepository<TCE002Condition, Long> {


	List<TCE002Condition> findByTarget(DiagnosticType type);
	@Query("SELECT c FROM TCE002Condition c WHERE LOWER(c.shedSetting.syndromeDiagnostic) LIKE LOWER(CONCAT('%', :search, '%'))")
	Page<TCE002Condition> search(@Param("search") String search, Pageable pageable);
}