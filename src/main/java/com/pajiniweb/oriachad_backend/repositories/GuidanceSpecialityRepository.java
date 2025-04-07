package com.pajiniweb.oriachad_backend.repositories;

import com.pajiniweb.oriachad_backend.domains.dtos.GuidanceGroupDto;
import com.pajiniweb.oriachad_backend.domains.entities.GuidanceGroup;
import com.pajiniweb.oriachad_backend.domains.entities.GuidanceSpeciality;
import com.pajiniweb.oriachad_backend.domains.enums.TypeEstablishment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface GuidanceSpecialityRepository extends JpaRepository<GuidanceSpeciality, Long> {

	List<GuidanceSpeciality> findAllByEstablishmentType(TypeEstablishment typeEstablishment);

	@Query("select gs from GuidanceSpeciality  gs inner join Classe c on gs.idSpecialityFor=c.idSpeciality where c.id=:idCalsse")
	List<GuidanceSpeciality>  findAllByClasse(Long idCalsse);
	@Query("SELECT gs " +
			"FROM GuidanceSpeciality gs " +
			"inner JOIN StudentClasse sc " +
			"on gs.idSpecialityFor = sc.classe.idSpeciality " +
			"WHERE sc.idStudent = :idStudent " )
	List<GuidanceSpeciality> findAllByStudent(@Param("idStudent") Long idStudent);
}