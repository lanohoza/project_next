package com.pajiniweb.oriachad_backend.actions.repositories;

import com.pajiniweb.oriachad_backend.actions.domains.entities.SupportCounselor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface SupportCounselorRepository extends JpaRepository<SupportCounselor, Long> {
    @Query("select sp from SupportCounselor sp left join sp.shedSettings ss where ss.id=:idShedSetting")
    List<SupportCounselor> allByShedSetting(Long idShedSetting);
}
