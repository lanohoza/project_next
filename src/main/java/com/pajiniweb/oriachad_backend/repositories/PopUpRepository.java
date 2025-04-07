package com.pajiniweb.oriachad_backend.repositories;

import com.pajiniweb.oriachad_backend.domains.entities.OriachadUser;
import com.pajiniweb.oriachad_backend.domains.entities.PopUp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PopUpRepository extends JpaRepository<PopUp, Long> {

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM PopUp p WHERE p.publish = true")
    boolean existsByPublishTrue();

    PopUp findByPublishTrue();

    @Query("SELECT p FROM  PopUp p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<PopUp> search(Pageable pageable, @Param("search") String search);
}
