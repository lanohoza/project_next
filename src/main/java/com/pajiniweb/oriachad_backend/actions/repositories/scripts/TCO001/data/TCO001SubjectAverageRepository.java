package com.pajiniweb.oriachad_backend.actions.repositories.scripts.TCO001.data;

import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCO001.data.TCO001SubjectAverage;
import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCO001.projections.GroupSubjectAverage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TCO001SubjectAverageRepository extends JpaRepository<TCO001SubjectAverage, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM TCO001SubjectAverage average WHERE average.guidanceSpecialityAverage.idDesireSpecialty = :idDesireSpecialty")
    void deleteByDesireSpecialtyId(Long idDesireSpecialty);
    @Modifying
    @Transactional
    @Query("DELETE FROM TCO001SubjectAverage sa WHERE sa.idYear= :idYear AND sa.idCreatedBy = :idCreatBy")
    void deleteBys(Long idYear, Long idCreatBy);

    @Query("SELECT sav  FROM TCO001SubjectAverage sav " +
            "WHERE sav.idYear = :idYear " +
            "AND sav.idStudent = :idStudent " +
            "AND sav.idSubject = :idSubject " +
            "AND sav.guidanceSpecialityAverage.idGuidanceSpeciality = :idGuidanceSpeciality " +
            "AND sav.idCreatedBy = :idUser")
    TCO001SubjectAverage find(Long idYear, Long idUser, Long idStudent, Long idGuidanceSpeciality, Long idSubject);
    @Query("SELECT   avg(sav.average) " +
            "FROM TCO001SubjectAverage sav " +
            "WHERE sav.idYear = :idYear " +
            "  AND sav.idClass = :idClass " +
            "  AND sav.idCreatedBy = :idUser " +
            "  AND sav.guidanceSpecialityAverage.idGuidanceSpeciality = :idGuidanceSpeciality " +
            "GROUP BY sav.idSubject " +
            "order by sav.coefficient limit 2")
    List<Double> getMaxCoefficientAverageClass(Long idClass, Long idYear, Long idUser, Long idGuidanceSpeciality);
    @Query("SELECT  avg(sav.average) FROM TCO001SubjectAverage sav " +
            "WHERE sav.idYear = :idYear " +
            "  AND sav.idLevel = :idLevel " +
            "  AND sav.idCreatedBy = :idUser " +
            "  AND sav.guidanceSpecialityAverage.idGuidanceSpeciality = :idGuidanceSpeciality " +
            "GROUP BY sav.idSubject " +
            "order by sav.coefficient limit 2")
    List<Double> getMaxCoefficientAverageLevel(Long idLevel, Long idYear, Long idUser, Long idGuidanceSpeciality);

    @Query("SELECT avg(sav.average) " +
            "FROM TCO001SubjectAverage sav " +
            "WHERE sav.idYear = :idYear " +
            "AND sav.idCreatedBy = :idUser " +
            "AND sav.guidanceSpecialityAverage.idGuidanceSpeciality = :idGuidanceSpeciality " +
            "GROUP BY sav.idSubject " +
            "order by sav.coefficient limit 2")
    List<Double> getMaxCoefficientAverageEstablishment(Long idYear, Long idUser, Long idGuidanceSpeciality);
    @Query("SELECT avg(sav.average) " +
            "FROM TCO001SubjectAverage sav " +
            "WHERE sav.idYear = :idYear " +
            "AND sav.idCreatedBy = :idUser " +
            "AND sav.idSpeciality = :idSpeciality " +
            "AND sav.idLevel = :idLevel " +
            "AND sav.guidanceSpecialityAverage.idGuidanceSpeciality = :idGuidanceSpeciality " +
            "GROUP BY sav.idSubject " +
            "order by sav.coefficient limit 2")
    List<Double> getMaxCoefficientAverageSpecialty(Long idSpeciality,Long idLevel,Long idYear, Long idUser, Long idGuidanceSpeciality);

    @Query("SELECT  avg(sav.average) FROM TCO001SubjectAverage sav " +
            "WHERE sav.idYear = :idYear " +
            "  AND sav.idSubject = :idSubject " +
            "  AND sav.idCreatedBy = :idUser " +
            "  AND sav.guidanceSpecialityAverage.idGuidanceSpeciality = :idGuidanceSpeciality ")
    Double getMaxCoefficientAverageSubject(Long idSubject, Long idYear, Long idUser, Long idGuidanceSpeciality);
}