package com.pajiniweb.oriachad_backend.actions.repositories.scripts.TCE002;

import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCE002.TCE002Diagnostic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TCE002DiagnosticRepository extends JpaRepository<TCE002Diagnostic, Long> {


    @Modifying
    @Transactional
    @Query("delete from TCE002Diagnostic tCE002Diagnostic where tCE002Diagnostic.idStudent = :idStudent and tCE002Diagnostic.idYear= :idYear and tCE002Diagnostic.idCreatedBy=:idCreatBy")
    void deleteBys(Long idStudent, Long idYear, Long idCreatBy);

    @Modifying
    @Transactional
    @Query("delete from TCE002Diagnostic tCE002Diagnostic where tCE002Diagnostic.idYear= :idYear and tCE002Diagnostic.idCreatedBy=:idCreatBy")
    void deleteBys(Long idYear, Long idCreatBy);

    @Query("select tCE002Diagnostic.shedSetting.syndromeDiagnostic from TCE002Diagnostic tCE002Diagnostic where tCE002Diagnostic.idStudent = :idStudent and tCE002Diagnostic.idYear= :idYear and tCE002Diagnostic.idCreatedBy=:idCreatBy")
    List<String> findByStudent(Long idStudent, Long idYear, Long idCreatBy);

    @Query("select tCE002Diagnostic.shedSetting.syndromeDiagnostic from TCE002Diagnostic tCE002Diagnostic where " +
            "tCE002Diagnostic.idClasse = :idClasse " +
            "and tCE002Diagnostic.idYear= :idYear " +
            "and tCE002Diagnostic.idCreatedBy= :idCreatBy")
    List<String> findByClasse(@Param("idClasse") Long idClasse,
                              @Param("idYear") Long idYear,
                              @Param("idCreatBy") Long idCreatBy);

    @Query("select tCE002Diagnostic.shedSetting.syndromeDiagnostic from TCE002Diagnostic tCE002Diagnostic where " +
            "tCE002Diagnostic.idLevel = :idLevel " +
            "and tCE002Diagnostic.idYear= :idYear " +
            "and tCE002Diagnostic.idCreatedBy= :idCreatBy")
    List<String> findByLevel(@Param("idLevel") Long idLevel,
                             @Param("idYear") Long idYear,
                             @Param("idCreatBy") Long idCreatBy);

    @Query("select tCE002Diagnostic.shedSetting.syndromeDiagnostic from TCE002Diagnostic tCE002Diagnostic where " +
            "tCE002Diagnostic.idSpeciality = :idSpeciality " +
            "and tCE002Diagnostic.idYear= :idYear " +
            "and tCE002Diagnostic.idLevel=:idLevel " +
            "and tCE002Diagnostic.idCreatedBy= :idCreatBy")
    List<String> findBySpeciality(
            @Param("idSpeciality") Long idSpeciality,
            @Param("idLevel") Long idLevel,
                             @Param("idYear") Long idYear,
                             @Param("idCreatBy") Long idCreatBy);
    @Query("select tCE002Diagnostic.shedSetting.syndromeDiagnostic from TCE002Diagnostic tCE002Diagnostic where " +
            "tCE002Diagnostic.idSubject = :idSubject " +
            "and tCE002Diagnostic.idYear= :idYear " +
            "and tCE002Diagnostic.idCreatedBy= :idCreatBy")
    List<String> findBySubject(@Param("idSubject") Long idSubject,
                               @Param("idYear") Long idYear,
                               @Param("idCreatBy") Long idCreatBy);
}