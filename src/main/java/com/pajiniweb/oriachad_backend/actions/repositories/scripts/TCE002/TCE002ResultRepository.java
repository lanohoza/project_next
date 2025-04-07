package com.pajiniweb.oriachad_backend.actions.repositories.scripts.TCE002;

import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCE002.projections.CategoryCountDto;
import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCE002.TCE002Result;
import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCE002.projections.SubjectAverageDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TCE002ResultRepository extends JpaRepository<TCE002Result, Long> {

	@Modifying
	@Transactional
	@Query("DELETE FROM TCE002Result tce002Result WHERE tce002Result.idStudent = :idStudent AND tce002Result.idYear= :idYear AND tce002Result.idCreatedBy = :idCreatBy")
	void deleteBys(Long idStudent, Long idYear, Long idCreatBy);

	@Modifying
	@Transactional
	@Query("DELETE FROM TCE002Result tce002Result WHERE tce002Result.idYear= :idYear AND tce002Result.idCreatedBy = :idCreatBy")
	void deleteBys(Long idYear, Long idCreatBy);

	@Query("select  tce002Result FROM TCE002Result tce002Result WHERE tce002Result.idStudent = :idStudent AND tce002Result.idYear= :idYear AND tce002Result.idCreatedBy = :idCreatBy")
	List<TCE002Result> findBys(Long idStudent, Long idYear, Long idCreatBy);


	@Query("""
			    SELECT 
			        NEW com.pajiniweb.oriachad_backend.actions.domains.scripts.TCE002.projections.SubjectAverageDto(
			            s.title,
			            r.value,
			            CASE
			                WHEN r.value >= 18.01 THEN 'category_18_20'
			                WHEN r.value >= 16.01 THEN 'category_16_18'
			                WHEN r.value >= 14.01 THEN 'category_14_16'
			                WHEN r.value >= 12.01 THEN 'category_12_14'
			                WHEN r.value >= 10.01 THEN 'category_10_12'
			                WHEN r.value >= 10 THEN 'category_greater_10'
			                ELSE 'category_less_10'
			            END
			        )
			    FROM TCE002Result r
			    JOIN r.subject s
			    WHERE r.idClass = :idClass AND r.idYear = :idYear AND r.idCreatedBy = :idCreatedBy
			    
			""")
	List<SubjectAverageDto> findSubjectAveragesClassWithCategories(
			@Param("idClass") Long idClass,
			@Param("idYear") Long idYear,
			@Param("idCreatedBy") Long idCreatedBy
	);


	@Query("""
			    SELECT 
			        NEW com.pajiniweb.oriachad_backend.actions.domains.scripts.TCE002.projections.SubjectAverageDto(
			            s.title,
			            r.value,
			            CASE
			                WHEN r.value >= 18.01 THEN 'category_18_20'
			                WHEN r.value >= 16.01 THEN 'category_16_18'
			                WHEN r.value >= 14.01 THEN 'category_14_16'
			                WHEN r.value >= 12.01 THEN 'category_12_14'
			                WHEN r.value >= 10.01 THEN 'category_10_12'
			                WHEN r.value >= 10 THEN 'category_greater_10'
			                ELSE 'category_less_10'
			            END
			        )
			    FROM TCE002Result r
			    JOIN r.subject s
			    WHERE r.idLevel = :idLevel AND r.idYear = :idYear AND r.idCreatedBy = :idCreatedBy
			""")
	List<SubjectAverageDto> findSubjectAveragesLevelWithCategories(
			@Param("idLevel") Long idLevel,
			@Param("idYear") Long idYear,
			@Param("idCreatedBy") Long idCreatedBy
	);
	@Query("""
			    SELECT 
			        NEW com.pajiniweb.oriachad_backend.actions.domains.scripts.TCE002.projections.SubjectAverageDto(
			            s.title,
			            r.value,
			            CASE
			                WHEN r.value >= 18.01 THEN 'category_18_20'
			                WHEN r.value >= 16.01 THEN 'category_16_18'
			                WHEN r.value >= 14.01 THEN 'category_14_16'
			                WHEN r.value >= 12.01 THEN 'category_12_14'
			                WHEN r.value >= 10.01 THEN 'category_10_12'
			                WHEN r.value >= 10 THEN 'category_greater_10'
			                ELSE 'category_less_10'
			            END
			        )
			    FROM TCE002Result r
			    JOIN r.subject s
			    WHERE r.idYear = :idYear AND r.idCreatedBy = :idCreatedBy
			""")
	List<SubjectAverageDto> findSubjectAveragesWithCategories(
			@Param("idYear") Long idYear,
			@Param("idCreatedBy") Long idCreatedBy
	);
	@Query("""
			    SELECT 
			        NEW com.pajiniweb.oriachad_backend.actions.domains.scripts.TCE002.projections.SubjectAverageDto(
			            s.title,
			            r.value,
			            CASE
			                WHEN r.value >= 18.01 THEN 'category_18_20'
			                WHEN r.value >= 16.01 THEN 'category_16_18'
			                WHEN r.value >= 14.01 THEN 'category_14_16'
			                WHEN r.value >= 12.01 THEN 'category_12_14'
			                WHEN r.value >= 10.01 THEN 'category_10_12'
			                WHEN r.value >= 10 THEN 'category_greater_10'
			                ELSE 'category_less_10'
			            END
			        )
			    FROM TCE002Result r
			    JOIN r.subject s
			    WHERE r.idLevel = :idLevel and r.classe.idSpeciality=:idSpeciality AND r.idYear = :idYear AND r.idCreatedBy = :idCreatedBy
			""")
	List<SubjectAverageDto> findSubjectAveragesSpecialityWithCategories(

			@Param("idSpeciality") Long idSpeciality,
			@Param("idLevel") Long idLevel,
			@Param("idYear") Long idYear,
			@Param("idCreatedBy") Long idCreatedBy
	);
	@Query("SELECT SUM(tce002Result.value) / COUNT(tce002Result) AS average "
			+ "FROM TCE002Result tce002Result "
			+ "WHERE tce002Result.idStudent = :idStudent "
			+ "AND tce002Result.idYear = :idYear "
			+ "AND tce002Result.idCreatedBy = :idCreatedBy "
			+ "AND tce002Result.idSubject IN (:idSubjects)")
	Double getAverageSubjectStudent(Long idStudent, Long idYear, Long idCreatedBy, List<Long> idSubjects);

	@Query("SELECT SUM(tce002Result.value) / COUNT(tce002Result) AS average "
			+ "FROM TCE002Result tce002Result "
			+ "WHERE tce002Result.idClass = :idClasse "
			+ "AND tce002Result.idYear = :idYear "
			+ "AND tce002Result.idCreatedBy = :idCreatedBy "
			+ "AND tce002Result.idSubject IN (:idSubjects)")
	Double getAverageSubjectClasse(Long idClasse, Long idYear, Long idCreatedBy, List<Long> idSubjects);

	@Query("SELECT SUM(tce002Result.value) / COUNT(tce002Result) AS average "
			+ "FROM TCE002Result tce002Result "
			+ "WHERE tce002Result.idLevel = :idLevel "
			+ "AND tce002Result.idYear = :idYear "
			+ "AND tce002Result.idCreatedBy = :idCreatedBy "
			+ "AND tce002Result.idSubject IN (:idSubjects)")
	Double getAverageSubjectLevel(Long idLevel, Long idYear, Long idCreatedBy, List<Long> idSubjects);

	@Query("SELECT SUM(tce002Result.value) / COUNT(tce002Result) AS average "
			+ "FROM TCE002Result tce002Result "
			+ "WHERE  tce002Result.idYear = :idYear "
			+ "AND tce002Result.idCreatedBy = :idCreatedBy "
			+ "AND tce002Result.idSubject =:idSubject")
	Double getAverageSubject(Long idYear, Long idCreatedBy, Long idSubject);
	@Query("SELECT SUM(tce002Result.value) / COUNT(tce002Result) AS average " + "FROM TCE002Result tce002Result " + "WHERE tce002Result.classe.idSpeciality = :idSpeciality " + "AND tce002Result.idYear = :idYear " + "AND tce002Result.idCreatedBy = :idCreatedBy " + "AND tce002Result.idSubject IN (:idSubjects)")

	Double getAverageSpeciality(Long idSpeciality, Long idYear, Long idCreatedBy, List<Long> idSubjects);


	@Query("""
    SELECT 
        NEW com.pajiniweb.oriachad_backend.actions.domains.scripts.TCE002.projections.CategoryCountDto(
            CASE
                WHEN AVG(r.value) >= 18.01 THEN 'category_18_20'
                WHEN AVG(r.value) >= 16.01 THEN 'category_16_18'
                WHEN AVG(r.value) >= 14.01 THEN 'category_14_16'
                WHEN AVG(r.value) >= 12.01 THEN 'category_12_14'
                WHEN AVG(r.value) >= 10.01 THEN 'category_10_12'
                WHEN AVG(r.value) >= 10 THEN 'category_greater_10'
                ELSE 'category_less_10'
            END,
            COUNT(s.title) 
        )
    FROM TCE002Result r
    JOIN r.subject s
    WHERE r.idSubject = :idSubject AND r.idYear = :idYear AND r.idCreatedBy = :idCreatedBy
    GROUP BY 
        CASE
            WHEN r.value >= 14 THEN 'category_greater_14'
            WHEN r.value >= 10.01 THEN 'category_10_14'
            WHEN r.value >= 10 THEN 'category_greater_10'
            ELSE 'category_less_10'
        END
""")
	List<CategoryCountDto> findSubjectCategoryCounts(
			@Param("idSubject") Long idSubject,
			@Param("idYear") Long idYear,
			@Param("idCreatedBy") Long idCreatedBy
	);

}