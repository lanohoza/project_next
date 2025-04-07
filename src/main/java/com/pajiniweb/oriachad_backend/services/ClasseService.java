package com.pajiniweb.oriachad_backend.services;

import com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos.AddEditClasseDto;
import com.pajiniweb.oriachad_backend.domains.dtos.ClasseBreakDto;
import com.pajiniweb.oriachad_backend.domains.dtos.ClasseDto;
import com.pajiniweb.oriachad_backend.domains.entities.*;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClasseService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ClasseService.class);

	@Autowired
	ClasseRepository classeRepository;
	@Autowired
	LevelRepository levelRepository;
	@Autowired
	SpecialityRepository specialityRepository;
	@Autowired
	HelperService helperService;
	@Autowired
	private YearRepository yearRepository;
	@Autowired
	private ClasseBreakRepository classeBreakRepository;

	public List<ClasseDto> allByYear(Long idYear) {
		LOGGER.info(Messages.START_FUNCTION, "findAll classe");
		try {
			Establishment establishment = helperService.getCurrentEstablishment();
			List<Classe> classes = classeRepository.findByIdYearAndIdEstablishment(idYear, establishment.getId());
			var classeDtos = classes.stream().map(classe -> ClasseDto.builder().title(classe.getTitle()).number(classe.getNumber()).idLevel(classe.getIdLevel()).idSpeciality(classe.getIdSpeciality()).id(classe.getId()).build()).toList();
			return classeDtos;
		} catch (Exception e) {
			LOGGER.error(Messages.ENTITY_IS_NOT_FOUND, "classe", e.getMessage());
			throw new RuntimeException(Messages.PROCESS_IS_NOT_DONE);
		}
	}

	public List<Classe> getAllByIdYearAndIdEstablishment(Long idYear, Long IdEstablishment, Long idLevel, Long idS) {
		return  null;//classeRepository.getAllByIdYearAndIdEstablishment(idYear, )
	}

	public ClasseDto findById(Long id) {
		LOGGER.info(Messages.START_FUNCTION, "findByIdclasse");
		Classe classe = classeRepository.findById(id).orElseThrow(() -> new RuntimeException("Classe not found with id: " + id));
		return ClasseDto.builder().title(classe.getTitle()).levelTitle(classe.getLevel().getTitle()).specialityTitle(classe.getSpeciality() != null ? classe.getSpeciality().getTitle() : null).number(classe.getNumber()).idSpeciality(classe.getIdSpeciality()).idYear(classe.getIdYear()).idLevel(classe.getIdLevel()).id(classe.getId()).idProfessor(classe.getIdProfessor()).breaks(classe.getBreaks() == null ? null : classe.getBreaks().stream().map(classeBreak -> ClasseBreakDto.builder().id(classeBreak.getId()).breakDay(classeBreak.getBreakDay()).endHour(classeBreak.getEndHour()).startHour(classeBreak.getStartHour()).build()).toList())

				.build();
	}

	public Classe save(AddEditClasseDto addEditClasseDto) {
		LOGGER.info(Messages.START_FUNCTION, "save classe");
		try {
			Level level = levelRepository.findById(addEditClasseDto.getIdLevel()).orElseThrow();
			Speciality speciality = addEditClasseDto.getIdSpeciality() != null ? specialityRepository.findById(addEditClasseDto.getIdSpeciality()).orElseThrow() : null;
			Establishment establishment = helperService.getCurrentEstablishment();
			Year year = yearRepository.findById(addEditClasseDto.getIdYear()).orElseThrow();
			String title = speciality != null ? String.format("%s %s %s %s", level.getTitle(), helperService.getCurrentEstablishmentTypeName(establishment), speciality.getTitle(), addEditClasseDto.getNumber()) : String.format("%s %s %s", level.getTitle(), helperService.getCurrentEstablishmentTypeName(establishment), addEditClasseDto.getNumber());
			Classe classe = Classe.builder().year(year).professor(addEditClasseDto.getIdProfessor() != null ? Professor.builder().id(addEditClasseDto.getIdProfessor()).build() : null).number(addEditClasseDto.getNumber()).level(level).speciality(speciality).establishment(establishment).title(title).build();
			classe.setBreaks(addEditClasseDto.getBreaks() == null ? null : addEditClasseDto.getBreaks().stream().map(classeBreakDto -> ClasseBreak.builder().classe(classe).breakDay(classeBreakDto.getBreakDay()).endHour(classeBreakDto.getEndHour()).startHour(classeBreakDto.getStartHour()).build()).toList());
			classeRepository.save(classe);
			return classe;
		} catch (Exception e) {
			LOGGER.error("Error saving classe", e);
			throw e;
		}
	}

	public boolean update(AddEditClasseDto addEditClasseDto) {
		LOGGER.info(Messages.START_FUNCTION, "update classe");
		try {
			Classe classe = classeRepository.findById(addEditClasseDto.getId()).orElseThrow(() -> {
				LOGGER.error(Messages.ENTITY_IS_NOT_EXIST, "classe");
				throw new IllegalArgumentException(Messages.ENTITY_IS_NOT_EXIST.replace("{}", "classe"));
			});
			Level level = levelRepository.findById(addEditClasseDto.getIdLevel()).orElseThrow();
			Year year = yearRepository.findById(addEditClasseDto.getIdYear()).orElseThrow();
			Speciality speciality = addEditClasseDto.getIdSpeciality() != null ? specialityRepository.findById(addEditClasseDto.getIdSpeciality()).orElseThrow() : null;
			String title = speciality != null ? String.format("%s %s %s", level.getTitle(), speciality.getTitle(), addEditClasseDto.getNumber()) : String.format("%s %s", level.getTitle(), addEditClasseDto.getNumber());
			classe.setNumber(addEditClasseDto.getNumber());
			classe.setTitle(title);
			classe.setLevel(level);
			classe.setProfessor(Professor.builder().id(addEditClasseDto.getIdProfessor()).build());
			classe.setYear(year);
			classe.getBreaks().clear();
			classe.getBreaks().addAll(addEditClasseDto.getBreaks() == null ? null : addEditClasseDto.getBreaks().stream().map(classeBreakDto -> ClasseBreak.builder().classe(classe).breakDay(classeBreakDto.getBreakDay()).endHour(classeBreakDto.getEndHour()).startHour(classeBreakDto.getStartHour()).build()).toList());
			classe.setSpeciality(speciality);

			classeRepository.save(classe);
			return true;

		} catch (Exception e) {
			LOGGER.error("Error updating classe", e);
			throw e;
		}
	}

	public boolean deleteById(Long id) {
		LOGGER.info(Messages.START_FUNCTION, "deleteByIdclasse");
		try {
			if (classeRepository.existsById(id)) {
				classeRepository.deleteById(id);
				LOGGER.info(Messages.DELETE_SUCCESSFULLY, "classe");
				return true; // Return true if deletion is successful
			} else {
				LOGGER.warn(Messages.ENTITY_IS_NOT_EXIST, "classe");
				return false; // Return false if classe with given ID does not exist
			}
		} catch (Exception e) {
			LOGGER.error("Error deleting classe", e);
			throw e;
		}
	}

	public Page<ClasseDto> searchClasses(String search, Long idYear, Pageable pageable) {
		LOGGER.info(Messages.START_FUNCTION, "searchClasses");
		try {
			Establishment establishment = helperService.getCurrentEstablishment();
			return classeRepository.findByIdYearAndIdEstablishmentAndTitleContainingOrNumber(idYear, establishment.getId(), search, Integer.getInteger(search), pageable).map(classe -> ClasseDto.builder().title(classe.getTitle()).levelTitle(classe.getLevel().getTitle()).specialityTitle(classe.getSpeciality() != null ? classe.getSpeciality().getTitle() : null).number(classe.getNumber()).idSpeciality(classe.getIdSpeciality()).idYear(classe.getIdYear()).idLevel(classe.getIdLevel()).id(classe.getId()).idProfessor(classe.getIdProfessor()).build());
		} catch (Exception e) {
			LOGGER.error("Error search Classes", e);
			throw e;
		}
	}

	public List<Classe> getAllByIdYearAndIdEstablishmentAndLevel(Long idYear, Long idEstablishment, Long idLevel) {
		return classeRepository.getAllByIdYearAndIdEstablishmentAndIdLevel(idYear, idEstablishment, idLevel);
	}

	public List<ClasseDto> allByCurrentYear() {
		LOGGER.info(Messages.START_FUNCTION, "findAll classe");
		try {
			Establishment establishment = helperService.getCurrentEstablishment();
			List<Classe> classes = classeRepository.findByIdEstablishment(establishment.getId());
			var classeDtos = classes.stream().map(classe -> ClasseDto.builder().title(classe.getTitle()).number(classe.getNumber()).idLevel(classe.getIdLevel()).idSpeciality(classe.getIdSpeciality()).id(classe.getId()).build()).toList();
			return classeDtos;
		} catch (Exception e) {
			LOGGER.error(Messages.ENTITY_IS_NOT_FOUND, "classe", e.getMessage());
			throw new RuntimeException(Messages.PROCESS_IS_NOT_DONE);
		}
	}
}
