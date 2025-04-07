package com.pajiniweb.oriachad_backend.actions.repositories.scripts.TCO001.condtions;

import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCO001.conditions.TCO001StudentCondition;
import com.pajiniweb.oriachad_backend.administration.domains.dtos.scripts.TCO001.conditions.TCO001StudentConditionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TCO001StudentConditionRepository extends JpaRepository<TCO001StudentCondition, Long> {
	@Query("SELECT c FROM TCO001StudentCondition c WHERE LOWER(c.shedSetting.syndromeDiagnostic) LIKE LOWER(CONCAT('%', :search, '%'))")
	Page<TCO001StudentCondition> search(String search, Pageable pageable);
}