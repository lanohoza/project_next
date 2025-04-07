package com.pajiniweb.oriachad_backend.repositories;

import com.pajiniweb.oriachad_backend.domains.entities.Level;
import com.pajiniweb.oriachad_backend.domains.enums.TypeEstablishment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface LevelRepository extends JpaRepository<Level, Long> {
    List<Level> findAllByType(TypeEstablishment type);
    Optional<Level> findByTitleAndType(String title, TypeEstablishment type);
    List<Level> findAllByTypeAndNumberIn(TypeEstablishment type,int[] numbers);

}