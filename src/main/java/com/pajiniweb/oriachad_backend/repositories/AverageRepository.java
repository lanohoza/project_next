package com.pajiniweb.oriachad_backend.repositories;

import com.pajiniweb.oriachad_backend.domains.entities.Average;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AverageRepository extends JpaRepository<Average, Long> {
    Optional<Average> findByIdStudentAndIdTrimestre(Long idStudent, Long idTrimestre);

    void deleteByStudentIdAndTrimestreId(Long idStudent, Long idTrimestre);

    @Query("select sum(av.value) / COUNT(av) from Average av " +
            "where av.student.id = :idStudent " +
            "and av.trimestre.idYear = :idYear " +
            "and av.trimestre.number in (1, 2, 3)")
    Optional<Double> findByAverageOfYear(Long idStudent, Long idYear);

}