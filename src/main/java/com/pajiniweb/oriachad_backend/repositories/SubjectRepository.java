package com.pajiniweb.oriachad_backend.repositories;

import com.pajiniweb.oriachad_backend.domains.entities.Subject;
import com.pajiniweb.oriachad_backend.domains.enums.TypeEstablishment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    @Query("select s from Subject as s " +
        "left join s.subjectLevels as sl " +
        "where  sl.idSpeciality=:idSpeciality and sl.idLevel=:idLevel")
    List<Subject> findAllByIdSpecialityAndIdLevel(Long idSpeciality, Long idLevel);

    List<Subject> findAllByType(TypeEstablishment type);


}