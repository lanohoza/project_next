package com.pajiniweb.oriachad_backend.repositories;

import com.pajiniweb.oriachad_backend.domains.entities.Speciality;
import com.pajiniweb.oriachad_backend.domains.enums.TypeEstablishment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpecialityRepository extends JpaRepository<Speciality, Long> {
    @Query("select s from Speciality as s " +
            "left join s.specialityLevels as sl " +
            "where sl.idLevel=:idLevel")
    List<Speciality> getAllByIdLevel(Long idLevel);
    @Query("select s from Speciality as s " +
            "left join s.specialityLevels as sl " +
            "where sl.level.type=:type and sl.level.number in :numbers")
    List<Speciality> getAllByEstabTypeAndNumbers(TypeEstablishment type, List<Integer> numbers);

    @Query("select distinct s from Speciality as s " +
            "left join s.specialityLevels as sl " +
            "where sl.idLevel=:idLevel and s.title=:title")
    Optional<Speciality> getByTitleAndIdLevel(String title, Long idLevel);

    @Query("select distinct s from Classe as cl " +
            "left join cl.speciality as s " +
            "where cl.idEstablishment=:idEstablishment ")
    List<Speciality> getBySpecialityOfEstablishment(Long idEstablishment);


}