package com.pajiniweb.oriachad_backend.actions.repositories.scripts.TCO001.condtions;

import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCO001.conditions.TCO001ClasseCondition;
import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCO001.conditions.TCO001StudentCondition;
import org.apache.commons.lang3.concurrent.UncheckedFuture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface   TCO001ClasseConditionRepository  extends JpaRepository<TCO001ClasseCondition, Long> {

	@Query("SELECT c FROM TCO001ClasseCondition c WHERE LOWER(c.shedSetting.syndromeDiagnostic) LIKE LOWER(CONCAT('%', :search, '%'))")
	Page<TCO001ClasseCondition> search(String search, Pageable pageable);
}