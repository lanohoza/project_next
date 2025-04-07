package com.pajiniweb.oriachad_backend.services;

import com.pajiniweb.oriachad_backend.domains.dtos.GuidanceSpecialityDto;
import com.pajiniweb.oriachad_backend.domains.entities.Establishment;
import com.pajiniweb.oriachad_backend.domains.entities.Speciality;
import com.pajiniweb.oriachad_backend.domains.enums.TypeEstablishment;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.repositories.GuidanceSpecialityRepository;
import com.pajiniweb.oriachad_backend.repositories.SpecialityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GuidanceSpecialityService {
	private static final Logger LOGGER = LoggerFactory.getLogger(GuidanceSpecialityService.class);
	@Autowired
	HelperService helperService;
	@Autowired

	GuidanceSpecialityRepository guidanceSpecialityRepository;

	public List<GuidanceSpecialityDto> getSpecialtyForDesire() {
		Establishment establishment = helperService.getCurrentEstablishment();
		return guidanceSpecialityRepository.findAllByEstablishmentType(establishment.getType())
				.stream()
                .map(guidanceSpeciality -> GuidanceSpecialityDto.builder()
                        .id(guidanceSpeciality.getId())
                        .title(guidanceSpeciality.getTitle())
						.idSpeciality(guidanceSpeciality.getIdSpeciality())
						.build())
                .toList();



	}

	public List<GuidanceSpecialityDto> getAdminSpecialtyForDesire(TypeEstablishment type) {
		 		return guidanceSpecialityRepository.findAllByEstablishmentType(type)
				.stream()
				.map(guidanceSpeciality -> GuidanceSpecialityDto.builder()
						.id(guidanceSpeciality.getId())
						.title(guidanceSpeciality.getTitle())
						.idSpeciality(guidanceSpeciality.getIdSpeciality())
						.build())
				.toList();
	}

	public List<GuidanceSpecialityDto> getSpecialtyForClasseDesire(Long idCalsse) {
		return guidanceSpecialityRepository.findAllByClasse(idCalsse)
				.stream()
				.map(guidanceSpeciality -> GuidanceSpecialityDto.builder()
						.id(guidanceSpeciality.getId())
						.title(guidanceSpeciality.getTitle())
						.idSpeciality(guidanceSpeciality.getIdSpeciality())
						.build())
				.toList();

	}


}
