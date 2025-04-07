package com.pajiniweb.oriachad_backend.services.documents;

import com.pajiniweb.oriachad_backend.actions.repositories.ActivityRepository;
import com.pajiniweb.oriachad_backend.domains.dtos.documents.*;
import com.pajiniweb.oriachad_backend.domains.entities.*;
import com.pajiniweb.oriachad_backend.domains.enums.TypeEstablishment;
import com.pajiniweb.oriachad_backend.repositories.*;
import com.pajiniweb.oriachad_backend.services.HelperService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
public class ProfessorStatisticsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReportsService.class);

	final HelperService helperService;
	final DateTimeFormatter dateTimeFormatter;
	final ActivityRepository activityRepository;
	final TaskRepository taskRepository;
	private final ClasseRepository classeRepository;
	private final ProfessorRepository professorRepository;


	public ProfessorsBreaksDto getProfessorsBreaks() {
		OriachadUser user = helperService.getCurrentUser().getOriachadUser();
		Year year = helperService.getCurrentYear();
		Establishment establishment = helperService.getCurrentEstablishment();
		ProfessorsBreaksDto professorsBreaksDto = ProfessorsBreaksDto.builder().build();
		professorsBreaksDto.setYearTitle(year.getTitle());
		professorsBreaksDto.setEstablishmentName(establishment.getName());
		professorsBreaksDto.setWilayaName(establishment.getCommune().getWilaya().getName());
		professorsBreaksDto.setUserName(user.getFirstName() + " " + user.getLastName());
		professorsBreaksDto.setBreakDays(professorRepository.findAllByIdEstablishmentAndIdCreatedBy(establishment.getId(), user.getId()).stream().map(professor -> getProfessorsBreakDays(professor)).toList());
		return professorsBreaksDto;

	}

	@Transactional
	public ProfessorsCoordinatorsDto getProfessorsCoordinators() {

		OriachadUser user = helperService.getCurrentUser().getOriachadUser();
		Year year = helperService.getCurrentYear();
		Establishment establishment = helperService.getCurrentEstablishment();
		ProfessorsCoordinatorsDto professorsCoordinatorsDto = ProfessorsCoordinatorsDto.builder().build();
		professorsCoordinatorsDto.setYearTitle(year.getTitle());
		professorsCoordinatorsDto.setEstablishmentName(establishment.getName());
		professorsCoordinatorsDto.setWilayaName(establishment.getCommune().getWilaya().getName());
		professorsCoordinatorsDto.setUserName(user.getFirstName() + " " + user.getLastName());
		professorsCoordinatorsDto.setProfessors(professorRepository.findAllByCoordinatorAndIdEstablishmentAndIdCreatedBy(true, establishment.getId(), user.getId()).stream().map(professor -> ProfessorsCoordinatorsDto.ProfessorCoordinator.builder().fullName(String.format("%s %s", professor.getFirstName(), professor.getLastName())).subjectTitle(professor.getSubject().getTitle()).build()).toList());

		return professorsCoordinatorsDto;
	}

	@Transactional
	public ProfessorsMainsDto getProfessorsMains() {

		OriachadUser user = helperService.getCurrentUser().getOriachadUser();
		Year year = helperService.getCurrentYear();
		Establishment establishment = helperService.getCurrentEstablishment();
		ProfessorsMainsDto professorsMainsDto = ProfessorsMainsDto.builder().build();
		professorsMainsDto.setYearTitle(year.getTitle());
		professorsMainsDto.setEstablishmentName(establishment.getName());
		professorsMainsDto.setWilayaName(establishment.getCommune().getWilaya().getName());
		professorsMainsDto.setUserName(user.getFirstName() + " " + user.getLastName());
		professorsMainsDto.setProfessors(classeRepository.findByIdYearAndIdEstablishmentAndIdProfessorIsNotNull(year.getId(), establishment.getId()).stream().map(classe -> ProfessorsMainsDto.ProfessorMain.builder().classeTitle(classe.getTitle()).fullName(String.format("%s %s", classe.getProfessor().getFirstName(), classe.getProfessor().getLastName())).subjectTitle(classe.getProfessor().getSubject().getTitle()).build()).toList());

		return professorsMainsDto;
	}

	/**
	 * Support Technique
	 */

	private ProfessorsBreaksDto.BreakDay getProfessorsBreakDays(Professor professor) {
		var breakDay = ProfessorsBreaksDto.BreakDay.builder().title(String.format("%s %s", professor.getFirstName(), professor.getLastName())).build();
		professor.getBreaks().forEach(professorBreak -> {

			if (professorBreak.getBreakDay() == 1) {
				breakDay.setSunday(String.format("%s - %s", professorBreak.getEndHour(), professorBreak.getStartHour()));

			}
			if (professorBreak.getBreakDay() == 2) {
				breakDay.setMonday(String.format("%s - %s", professorBreak.getEndHour(), professorBreak.getStartHour()));
			}
			if (professorBreak.getBreakDay() == 3) {
				breakDay.setTuesday(String.format("%s - %s", professorBreak.getEndHour(), professorBreak.getStartHour()));
			}
			if (professorBreak.getBreakDay() == 4) {
				breakDay.setWednesday(String.format("%s - %s", professorBreak.getEndHour(), professorBreak.getStartHour()));
			}
			if (professorBreak.getBreakDay() == 5) {
				breakDay.setThursday(String.format("%s - %s", professorBreak.getEndHour(), professorBreak.getStartHour()));
			}
		});
		return breakDay;
	}

}
