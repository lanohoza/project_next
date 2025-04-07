package com.pajiniweb.oriachad_backend.actions.repositories.scripts.TCO001.condtions;

import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCO001.conditions.TCO001StudentCondition;
import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCO001.conditions.TCO001SubjectCondition;
import org.apache.commons.lang3.concurrent.UncheckedFuture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface  TCO001SubjectConditionRepository extends JpaRepository<TCO001SubjectCondition, Long> {

	@Query("SELECT c FROM TCO001SubjectCondition c WHERE LOWER(c.shedSetting.syndromeDiagnostic) LIKE LOWER(CONCAT('%', :search, '%'))")
	Page<TCO001SubjectCondition> search(String search, Pageable pageable);

}