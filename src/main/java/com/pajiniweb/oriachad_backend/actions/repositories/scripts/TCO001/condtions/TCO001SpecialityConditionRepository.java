package com.pajiniweb.oriachad_backend.actions.repositories.scripts.TCO001.condtions;

import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCO001.conditions.TCO001LevelCondition;
import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCO001.conditions.TCO001SpecialityCondition;
import org.apache.commons.lang3.concurrent.UncheckedFuture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface TCO001SpecialityConditionRepository extends JpaRepository<TCO001SpecialityCondition, Long> {

	@Query("SELECT c FROM TCO001SpecialityCondition c WHERE LOWER(c.shedSetting.syndromeDiagnostic) LIKE LOWER(CONCAT('%', :search, '%'))")
	Page<TCO001SpecialityCondition> search(String search, Pageable pageable);
}