package com.pajiniweb.oriachad_backend.actions.repositories;

import com.pajiniweb.oriachad_backend.actions.domains.dtos.SupportStudentDto;
import com.pajiniweb.oriachad_backend.actions.domains.entities.SupportStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupportStudentRepository extends JpaRepository<SupportStudent, Long> {
    @Query("select sp from SupportStudent sp left join sp.shedSettings ss where ss.id=:idShedSetting")
    List<SupportStudent> allByShedSetting(Long idShedSetting);

}
