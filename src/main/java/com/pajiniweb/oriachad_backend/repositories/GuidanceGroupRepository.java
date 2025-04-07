package com.pajiniweb.oriachad_backend.repositories;

import com.pajiniweb.oriachad_backend.domains.dtos.GuidanceGroupDto;
import com.pajiniweb.oriachad_backend.domains.entities.GuidanceGroup;
import org.apache.commons.lang3.concurrent.UncheckedFuture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GuidanceGroupRepository extends JpaRepository<GuidanceGroup, Long> {

	@Query("select new com.pajiniweb.oriachad_backend.domains.dtos.GuidanceGroupDto(f, SIZE(f.students)) from GuidanceGroup as f " +
			"where f.idYear=:idYear " +
			"and f.idCreatedBy=:idUser " +
			"AND (:search IS NULL OR LOWER(f.title) LIKE LOWER(CONCAT('%', :search, '%')))")
	Page<GuidanceGroupDto> search(String search, Long idYear, Long idUser, Pageable pageable);

	@Query("select new com.pajiniweb.oriachad_backend.domains.dtos.GuidanceGroupDto(f) from GuidanceGroup as f " +
			"where f.id=:id ")
	GuidanceGroupDto findByIdDto(Long id);

	List<GuidanceGroup> findByIdYearAndIdCreatedBy(Long idYear,Long idCreatedBy);
}