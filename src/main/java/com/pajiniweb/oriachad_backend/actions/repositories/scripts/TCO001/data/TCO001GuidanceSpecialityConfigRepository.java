package com.pajiniweb.oriachad_backend.actions.repositories.scripts.TCO001.data;

import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCO001.conditions.TCO001LevelCondition;
import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCO001.data.TCO001GuidanceSpecialityAverage;
import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCO001.data.TCO001GuidanceSpecialityConfig;
import com.pajiniweb.oriachad_backend.domains.enums.TypeEstablishment;
import org.apache.commons.lang3.concurrent.UncheckedFuture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TCO001GuidanceSpecialityConfigRepository extends JpaRepository<TCO001GuidanceSpecialityConfig, Long> {


    List<TCO001GuidanceSpecialityConfig> findAllByIdLevel(Long idLevel);

	@Query("SELECT c FROM TCO001GuidanceSpecialityConfig c WHERE LOWER(c.guidanceSpeciality.title) LIKE LOWER(CONCAT('%', :search, '%'))")
	Page<TCO001GuidanceSpecialityConfig> search(String search, Pageable pageable);}