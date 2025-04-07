package com.pajiniweb.oriachad_backend.actions.repositories.scripts.TCE002;

import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCE002.TCE002Average;
import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCE002.projections.SubjectAverageDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TCE002AverageRepository extends JpaRepository<TCE002Average, Long> {


	@Modifying
	@Transactional
	@Query("delete from TCE002Average tCE002Average where tCE002Average.idStudent = :idStudent and tCE002Average.idYear= :idYear and tCE002Average.idCreatedBy=:idCreatBy")
	void deleteBys(Long idStudent, Long idYear,Long  idCreatBy);
	@Modifying
	@Transactional
	@Query("delete from TCE002Average tCE002Average where  tCE002Average.idYear= :idYear and tCE002Average.idCreatedBy=:idCreatBy")
	void deleteBys(Long idYear,Long  idCreatBy);

	@Query(value = """
			    SELECT 
			        NEW com.pajiniweb.oriachad_backend.actions.domains.scripts.TCE002.projections.SubjectAverageDto(
			        	'overallAverage',
			            a.value,
			            CASE
			                WHEN a.value >= 18.01 THEN 'category_18_20'
			                WHEN a.value >= 16.01 THEN 'category_16_18'
			                WHEN a.value >= 14.01 THEN 'category_14_16'
			                WHEN a.value >= 12.01 THEN 'category_12_14'
			                WHEN a.value >= 10.01 THEN 'category_10_12'
			                WHEN a.value >= 10 THEN 'category_greater_10'
			                ELSE 'category_less_10'
			            END
			        )
			    FROM TCE002Average a
			    WHERE a.idClasse = :idClass AND a.idYear = :idYear AND a.idCreatedBy = :idCreatedBy
			""")
	List<SubjectAverageDto> findOverallAverageClassWithCategory(@Param("idClass") Long idClasse, @Param("idYear") Long idYear, @Param("idCreatedBy") Long idCreatedBy);

	@Query(value = """
			    SELECT 
			        NEW com.pajiniweb.oriachad_backend.actions.domains.scripts.TCE002.projections.SubjectAverageDto(
			        	'overallAverage',
			            avg(a.value),
			            CASE
			                WHEN a.value >= 18.01 THEN 'category_18_20'
			                WHEN a.value >= 16.01 THEN 'category_16_18'
			                WHEN a.value >= 14.01 THEN 'category_14_16'
			                WHEN a.value >= 12.01 THEN 'category_12_14'
			                WHEN a.value >= 10.01 THEN 'category_10_12'
			                WHEN a.value >= 10 THEN 'category_greater_10'
			                ELSE 'category_less_10'
			            END
			        )
			    FROM TCE002Average a
			    WHERE a.idLevel = :idLevel AND a.idYear = :idYear AND a.idCreatedBy = :idCreatedBy 
     		     GROUP BY 
					CASE
					   		WHEN a.value >= 18.01 THEN 'category_18_20'
			                WHEN a.value >= 16.01 THEN 'category_16_18'
			                WHEN a.value >= 14.01 THEN 'category_14_16'
			                WHEN a.value >= 12.01 THEN 'category_12_14'
			                WHEN a.value >= 10.01 THEN 'category_10_12'
			                WHEN a.value >= 10 THEN 'category_greater_10'
			                ELSE 'category_less_10'
					END
			""")
	List<SubjectAverageDto> findOverallAverageLevelWithCategory(@Param("idLevel") Long idLevel, @Param("idYear") Long idYear, @Param("idCreatedBy") Long idCreatedBy);
	@Query(value = """
			    SELECT 
			        NEW com.pajiniweb.oriachad_backend.actions.domains.scripts.TCE002.projections.SubjectAverageDto(
			        	'overallAverage',
			            avg(a.value),
			            CASE
			                WHEN a.value >= 18.01 THEN 'category_18_20'
			                WHEN a.value >= 16.01 THEN 'category_16_18'
			                WHEN a.value >= 14.01 THEN 'category_14_16'
			                WHEN a.value >= 12.01 THEN 'category_12_14'
			                WHEN a.value >= 10.01 THEN 'category_10_12'
			                WHEN a.value >= 10 THEN 'category_greater_10'
			                ELSE 'category_less_10'
			            END
			        )
			    FROM TCE002Average a
			    WHERE a.idYear = :idYear AND a.idCreatedBy = :idCreatedBy 
     		     GROUP BY 
					CASE
					   		WHEN a.value >= 18.01 THEN 'category_18_20'
			                WHEN a.value >= 16.01 THEN 'category_16_18'
			                WHEN a.value >= 14.01 THEN 'category_14_16'
			                WHEN a.value >= 12.01 THEN 'category_12_14'
			                WHEN a.value >= 10.01 THEN 'category_10_12'
			                WHEN a.value >= 10 THEN 'category_greater_10'
			                ELSE 'category_less_10'
					END
			""")
	List<SubjectAverageDto> findOverallAverageWithCategory( @Param("idYear") Long idYear, @Param("idCreatedBy") Long idCreatedBy);
	@Query(value = """
			    SELECT 
			        NEW com.pajiniweb.oriachad_backend.actions.domains.scripts.TCE002.projections.SubjectAverageDto(
			        	'overallAverage',
			            avg(a.value),
			            CASE
			                WHEN a.value >= 18.01 THEN 'category_18_20'
			                WHEN a.value >= 16.01 THEN 'category_16_18'
			                WHEN a.value >= 14.01 THEN 'category_14_16'
			                WHEN a.value >= 12.01 THEN 'category_12_14'
			                WHEN a.value >= 10.01 THEN 'category_10_12'
			                WHEN a.value >= 10 THEN 'category_greater_10'
			                ELSE 'category_less_10'
			            END
			        )
			    FROM TCE002Average a
			    WHERE a.idLevel = :idLevel and a.classe.idSpeciality=:idSpeciality AND a.idYear = :idYear AND a.idCreatedBy = :idCreatedBy 
			  		     GROUP BY 
					CASE
					   		WHEN a.value >= 18.01 THEN 'category_18_20'
			                WHEN a.value >= 16.01 THEN 'category_16_18'
			                WHEN a.value >= 14.01 THEN 'category_14_16'
			                WHEN a.value >= 12.01 THEN 'category_12_14'
			                WHEN a.value >= 10.01 THEN 'category_10_12'
			                WHEN a.value >= 10 THEN 'category_greater_10'
			                ELSE 'category_less_10'
					END
			""")
	List<SubjectAverageDto> findOverallAverageSpecialityWithCategory(  @Param("idSpeciality") Long idSpeciality, @Param("idLevel") Long idLevel, @Param("idYear") Long idYear, @Param("idCreatedBy") Long idCreatedBy);
}