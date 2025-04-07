package com.pajiniweb.oriachad_backend.actions.repositories.scripts.TCO001.data;

import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCO001.data.TCO001GuidanceSpecialityAverage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TCO001GuidanceSpecialityAverageRepository extends JpaRepository<TCO001GuidanceSpecialityAverage, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM TCO001GuidanceSpecialityAverage average WHERE average.idDesireSpecialty = :idDesireSpecialty")
    void deleteByDesireSpecialtyId(Long idDesireSpecialty);


    @Modifying
    @Transactional
    @Query("DELETE FROM TCO001GuidanceSpecialityAverage sa WHERE sa.idYear= :idYear AND sa.idCreatedBy = :idCreatBy")
    void deleteBys(Long idYear, Long idCreatBy);
    @Query("select sav FROM TCO001GuidanceSpecialityAverage sav WHERE sav.idYear=:idYear " +
            "and sav.idGuidanceSpeciality = :idGuidanceSpeciality " +
            "and sav.idStudent=:idStudent " +
            "and sav.idCreatedBy=:idCreatedBy")
    TCO001GuidanceSpecialityAverage find(Long idYear, Long idCreatedBy, Long idStudent, Long idGuidanceSpeciality);
    @Query("SELECT sav FROM TCO001GuidanceSpecialityAverage sav " +
            "WHERE sav.idYear = :idYear " +
            "AND sav.idStudent = :idStudent " +
            "AND sav.idCreatedBy = :idCreatedBy " +
            "ORDER BY sav.value desc ")
    List<TCO001GuidanceSpecialityAverage> findAllByStudent(Long idYear, Long idCreatedBy, Long idStudent);
    @Query("SELECT (COUNT(sav) + 1) FROM TCO001GuidanceSpecialityAverage sav " +
            "WHERE sav.idYear = :idYear " +
            "AND sav.idStudent = :idStudent " +
            "AND sav.idCreatedBy = :idCreatedBy " +
            "AND sav.id <> :idGuidanceSpecialityAverage " +
            "AND sav.value > (SELECT sav2.value FROM TCO001GuidanceSpecialityAverage sav2 " +
            "WHERE sav2.id = :idGuidanceSpecialityAverage)")
    Integer testRank(Long idYear, Long idCreatedBy, Long idStudent, Long idGuidanceSpecialityAverage);




    @Query("SELECT sav FROM TCO001GuidanceSpecialityAverage sav " +
            "WHERE sav.idYear = :idYear " +
            "AND sav.idClass = :idClass " +
            "AND sav.idCreatedBy = :idCreatedBy " +
            "and sav.order=:order" )
    List<TCO001GuidanceSpecialityAverage> findAllByClasse(Long idClass, Long idYear,Long idCreatedBy,Integer order);
    @Query("SELECT sav FROM TCO001GuidanceSpecialityAverage sav " +
            "WHERE sav.idYear = :idYear " +
            "AND sav.idLevel = :idLevel " +
            "AND sav.idCreatedBy = :idCreatedBy " +
            "and sav.order=:order" )
    List<TCO001GuidanceSpecialityAverage> findAllByLevel(Long idLevel, Long idYear,Long idCreatedBy,Integer order);

    @Query("SELECT sav FROM TCO001GuidanceSpecialityAverage sav " +
            "WHERE sav.idYear = :idYear " +
            "AND sav.idCreatedBy = :idCreatedBy " +
            "and sav.order=:order" )
    List<TCO001GuidanceSpecialityAverage> findAllByEstablishment(Long idYear,Long idCreatedBy,Integer order);
    @Query("SELECT sav FROM TCO001GuidanceSpecialityAverage sav " +
            "WHERE sav.idYear = :idYear " +
            "AND sav.idCreatedBy = :idCreatedBy " +
            "AND sav.idLevel = :idLevel " +
            "and sav.idSpeciality=:idSpeciality "+
            "and sav.order=:order" )
    List<TCO001GuidanceSpecialityAverage> findAllBySpecialty(Long idSpeciality,Long idLevel,Long idYear,Long idCreatedBy,Integer order);
    @Query("SELECT sav FROM TCO001GuidanceSpecialityAverage sav " +
            "left join sav.subjectAverages sa " +
            "WHERE sav.idYear = :idYear " +
            "AND sav.idCreatedBy = :idCreatedBy " +
            "and sav.order=:order " +
            "and sa.idSubject=:idSubject" )
    List<TCO001GuidanceSpecialityAverage> findAllBySubject(Long idSubject, Long idYear,Long idCreatedBy,Integer order);
}