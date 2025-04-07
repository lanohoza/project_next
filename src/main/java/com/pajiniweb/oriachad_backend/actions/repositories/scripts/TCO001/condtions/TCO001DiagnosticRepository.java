package com.pajiniweb.oriachad_backend.actions.repositories.scripts.TCO001.condtions;

import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCO001.conditions.TCO001Diagnostic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface   TCO001DiagnosticRepository extends JpaRepository<TCO001Diagnostic, Long> {

    @Modifying
    @Transactional
    @Query("delete from TCO001Diagnostic tCO001Diagnostic where tCO001Diagnostic.idStudent = :idStudent and tCO001Diagnostic.idYear= :idYear and tCO001Diagnostic.idCreatedBy=:idCreatBy")
    void deleteBys(Long idStudent, Long idYear, Long idCreatBy);

    @Modifying
    @Transactional
    @Query("delete from TCO001Diagnostic tCO001Diagnostic where tCO001Diagnostic.idYear= :idYear and tCO001Diagnostic.idCreatedBy=:idCreatBy")
    void deleteBys(Long idYear, Long idCreatBy);

    @Query("select tCO001Diagnostic.shedSetting.syndromeDiagnostic from TCO001Diagnostic tCO001Diagnostic where tCO001Diagnostic.idStudent = :idStudent and tCO001Diagnostic.idYear= :idYear and tCO001Diagnostic.idCreatedBy=:idCreatBy")
    List<String> findByStudent(Long idStudent, Long idYear, Long idCreatBy);

    @Query("select tCO001Diagnostic.shedSetting.syndromeDiagnostic from TCO001Diagnostic tCO001Diagnostic where " +
            "tCO001Diagnostic.idClasse = :idClasse " +
            "and tCO001Diagnostic.idYear= :idYear " +
            "and tCO001Diagnostic.idCreatedBy= :idCreatBy")
    List<String> findByClasse(@Param("idClasse") Long idClasse,
                              @Param("idYear") Long idYear,
                              @Param("idCreatBy") Long idCreatBy);

    @Query("select tCO001Diagnostic.shedSetting.syndromeDiagnostic from TCO001Diagnostic tCO001Diagnostic where " +
            "tCO001Diagnostic.idLevel = :idLevel " +
            "and tCO001Diagnostic.idYear= :idYear " +
            "and tCO001Diagnostic.idCreatedBy= :idCreatBy")
    List<String> findByLevel(@Param("idLevel") Long idLevel,
                             @Param("idYear") Long idYear,
                             @Param("idCreatBy") Long idCreatBy);
    @Query("select tCO001Diagnostic.shedSetting.syndromeDiagnostic from TCO001Diagnostic tCO001Diagnostic where " +
            "tCO001Diagnostic.idEstablishment = :Establishment " +
            "and tCO001Diagnostic.idYear= :idYear " +
            "and tCO001Diagnostic.idCreatedBy= :idCreatBy")
    List<String> findByEstablishment(@Param("Establishment") Long Establishment,
                             @Param("idYear") Long idYear,
                             @Param("idCreatBy") Long idCreatBy);

    @Query("select tCO001Diagnostic.shedSetting.syndromeDiagnostic from TCO001Diagnostic tCO001Diagnostic where " +
            "tCO001Diagnostic.idSpeciality = :idSpeciality " +
            "and tCO001Diagnostic.idYear= :idYear " +
            "and tCO001Diagnostic.idLevel=:idLevel " +
            "and tCO001Diagnostic.idCreatedBy= :idCreatBy")
    List<String> findBySpeciality(
            @Param("idSpeciality") Long idSpeciality,
            @Param("idLevel") Long idLevel,
            @Param("idYear") Long idYear,
            @Param("idCreatBy") Long idCreatBy);
    @Query("select tCO001Diagnostic.shedSetting.syndromeDiagnostic from TCO001Diagnostic tCO001Diagnostic where " +
            "tCO001Diagnostic.idSubject = :idSubject " +
            "and tCO001Diagnostic.idYear= :idYear " +
            "and tCO001Diagnostic.idCreatedBy= :idCreatBy")
    List<String> findBySubject(@Param("idSubject") Long idSubject,
                               @Param("idYear") Long idYear,
                               @Param("idCreatBy") Long idCreatBy);

}