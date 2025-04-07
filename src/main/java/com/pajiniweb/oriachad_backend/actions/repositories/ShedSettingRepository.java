package com.pajiniweb.oriachad_backend.actions.repositories;

import com.pajiniweb.oriachad_backend.actions.domains.dtos.ShedSettingConditionDto;
import com.pajiniweb.oriachad_backend.actions.domains.dtos.ShedSettingWithSupportDto;
import com.pajiniweb.oriachad_backend.actions.domains.entities.ShedSetting;
import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCE002.DiagnosticType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShedSettingRepository extends JpaRepository<ShedSetting, Long> {
	Optional<ShedSetting> findFirstByShedCategory_IdOrderByReferenceDesc(Long idShedCategory);

	@Query("SELECT s FROM ShedSetting s WHERE LOWER(s.syndromeDiagnostic) LIKE LOWER(CONCAT('%', :search, '%'))")
	Page<ShedSetting> search(@Param("search") String search, Pageable pageable);

	@Query("SELECT MAX(ss.number) FROM ShedSetting ss WHERE ss.idShedCategory = :idShedCategory")
	Optional<Long> lastNumberOfShedSetting(@Param("idShedCategory") Long idShedCategory);

	@Query("SELECT new com.pajiniweb.oriachad_backend.actions.domains.dtos.ShedSettingConditionDto(s.id,s.shedCategory.name,s.reference,s.syndromeDiagnostic)  FROM ShedSetting s WHERE s.target=:target " +
			"and (:idShedCategory is null or s.idShedCategory=:idShedCategory) ")

	List<ShedSettingConditionDto> findByTargetAndIdShedCategory(DiagnosticType target, Long idShedCategory);
	@Query("SELECT new com.pajiniweb.oriachad_backend.actions.domains.dtos.ShedSettingConditionDto(s.id,s.shedCategory.name,s.reference,s.syndromeDiagnostic)  FROM ShedSetting s WHERE s.target=:target " +
			"and (:idShedCategory is null or s.idShedCategory=:idShedCategory) " +
			"and (:idSpeciality is null or s.idSpeciality=:idSpeciality)")
	List<ShedSettingConditionDto> allFilter(DiagnosticType target, Long idSpeciality, Long idShedCategory);
	@Query("SELECT new com.pajiniweb.oriachad_backend.actions.domains.dtos.ShedSettingConditionDto(s.id,s.shedCategory.name,s.reference,s.syndromeDiagnostic)  FROM ShedSetting s " +
			"WHERE s.idShedCategory=:idShedCategory " )
	List<ShedSettingConditionDto> allByCategory(Long idShedCategory);


	@Query("SELECT DISTINCT s FROM ShedSetting s " +
			"LEFT JOIN FETCH s.directionSheds " +
			"LEFT JOIN FETCH s.supportStudents " +
			"LEFT JOIN FETCH s.supportCounselors " +
			"LEFT JOIN FETCH s.requiredProcedures " +
			"JOIN s.interviews i " +
			"WHERE i.id = :idInterview")
	List<ShedSetting> findWithAssociationsByInterviewId(Long idInterview);

	@Query("SELECT DISTINCT s FROM ShedSetting s " +
			"LEFT JOIN FETCH s.directionSheds " +
			"LEFT JOIN FETCH s.supportStudents " +
			"LEFT JOIN FETCH s.supportCounselors " +
			"LEFT JOIN FETCH s.requiredProcedures " +
			"JOIN s.followUps f " +
			"WHERE f.id = :idFollowUp")
	List<ShedSetting> findWithAssociationsByFollowUpId(Long idFollowUp);
}
