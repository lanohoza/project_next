package com.pajiniweb.oriachad_backend.services;

import com.pajiniweb.oriachad_backend.actions.repositories.scripts.TCO001.data.TCO001GuidanceSpecialityAverageRepository;
import com.pajiniweb.oriachad_backend.actions.repositories.scripts.TCO001.data.TCO001SubjectAverageRepository;
import com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos.AddEditDesireDto;
import com.pajiniweb.oriachad_backend.domains.entities.*;
import com.pajiniweb.oriachad_backend.repositories.DesireRepository;
import com.pajiniweb.oriachad_backend.repositories.DesireSpecialtyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DesireService {
	private static final Logger LOGGER = LoggerFactory.getLogger(DesireService.class);

	@Autowired
	DesireRepository desireRepository;
	@Autowired
	DesireSpecialtyRepository desireSpecialtyRepository;
	@Autowired
	SpecialityService specialityService;
	@Autowired
	HelperService helperService;
	@Autowired
	TCO001GuidanceSpecialityAverageRepository tco001GuidanceSpecialityAverageRepository;
	@Autowired
	TCO001SubjectAverageRepository subjectAverageRepository;

	@Transactional
	public boolean saveDesire(AddEditDesireDto addEditDesireDto) {
		Year currentYear = helperService.getCurrentYear();
		OriachadUser user = helperService.getCurrentUser().getOriachadUser();
		Desire desire = desireRepository.getByIdUserAndIdYearAndIdStudent(user.getId(), currentYear.getId(), addEditDesireDto.getIdStudent()).orElseGet(() -> desireRepository.save(Desire.builder().user(user).year(currentYear).student(Student.builder().id(addEditDesireDto.getIdStudent()).build()).build()));
		desireSpecialtyRepository.deleteByOrderOrGuidanceSpeciality(desire.getId(), addEditDesireDto.getOrder(), addEditDesireDto.getIdGuidanceSpeciality());
		desireSpecialtyRepository.save(DesireSpecialty.builder().year(currentYear).student(Student.builder().id(addEditDesireDto.getIdStudent()).build()).desire(desire).user(user).order(addEditDesireDto.getOrder())

				.guidanceSpeciality(GuidanceSpeciality.builder().id(addEditDesireDto.getIdGuidanceSpeciality()).build()).build());

		return true;
	}
	@Transactional

	public Boolean deleteById(Long studentId, Long idGuidanceSpeciality) {
		Year currentYear = helperService.getCurrentYear();
		OriachadUser user = helperService.getCurrentUser().getOriachadUser();
		Long idDesire = desireSpecialtyRepository.findByIdDesire(currentYear.getId(), user.getId(), studentId, idGuidanceSpeciality).orElseThrow();
		subjectAverageRepository.deleteByDesireSpecialtyId(idDesire);
		tco001GuidanceSpecialityAverageRepository.deleteByDesireSpecialtyId(idDesire);
		desireSpecialtyRepository.deleteByGuidanceSpeciality(currentYear.getId(), user.getId(), studentId, idGuidanceSpeciality);
		desireRepository.deleteByCountDesireSpecialty(idDesire);
		return true;

	}
}
