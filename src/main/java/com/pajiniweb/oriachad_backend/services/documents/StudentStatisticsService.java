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
public class StudentStatisticsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReportsService.class);

	final HelperService helperService;
	final DateTimeFormatter dateTimeFormatter;
	final ActivityRepository activityRepository;
	final TaskRepository taskRepository;
	private final LevelRepository levelRepository;
	private final ClasseRepository classeRepository;
	private final StudentRepository studentRepository;
	private final SpecialityRepository specialityRepository;
	private final ProfessorRepository professorRepository;

	@Transactional
	public GeneralStatisticsDto getGeneralStatistics() {


		OriachadUser user = helperService.getCurrentUser().getOriachadUser();
		Year year = helperService.getCurrentYear();
		Establishment establishment = helperService.getCurrentEstablishment();
		GeneralStatisticsDto generalStatisticsDto = GeneralStatisticsDto.builder().build();
		generalStatisticsDto.setYearTitle(year.getTitle());
		generalStatisticsDto.setEstablishmentName(establishment.getName());
		generalStatisticsDto.setWilayaName(establishment.getCommune().getWilaya().getName());
		generalStatisticsDto.setUserName(user.getFirstName() + " " + user.getLastName());
		generalStatisticsDto.setType(establishment.getType());
		generalStatisticsDto.setGlobalData(GeneralStatisticsDto.GlobalData.builder().build());
		levelRepository.findAllByType(establishment.getType()).forEach(level -> {
			int numberOfClass = classeRepository.countByIdLevelAndIdYearAndIdEstablishment(level.getId(), year.getId(), establishment.getId());
			int numberOfStudent = studentRepository.countByLevelAndYearAndEstablishment(level.getId(), year.getId(), establishment.getId());
			if (level.getNumber() == 1) {
				generalStatisticsDto.getGlobalData().setFirstNumberOfClass(numberOfClass);
				generalStatisticsDto.getGlobalData().setFirstNumberOfStudent(numberOfStudent);
			}
			if (level.getNumber() == 2) {
				generalStatisticsDto.getGlobalData().setSecondNumberOfClass(numberOfClass);
				generalStatisticsDto.getGlobalData().setSecondNumberOfStudent(numberOfStudent);
			}
			if (level.getNumber() == 3) {
				generalStatisticsDto.getGlobalData().setThirdNumberOfClass(numberOfClass);
				generalStatisticsDto.getGlobalData().setThirdNumberOfStudent(numberOfStudent);
			}
			if (level.getNumber() == 4) {
				generalStatisticsDto.getGlobalData().setFourthNumberOfClass(numberOfClass);
				generalStatisticsDto.getGlobalData().setFourthNumberOfStudent(numberOfStudent);
			}
		});
		if (establishment.getType() == TypeEstablishment.secondary)
			generalStatisticsDto.setSpecialties(specialityRepository.getBySpecialityOfEstablishment(establishment.getId()).stream().map(speciality -> {
				int numberOfClass = classeRepository.countByIdSpecialityAndIdYearAndIdEstablishment(speciality.getId(), year.getId(), establishment.getId());
				int numberOfStudent = studentRepository.countBySpecialityAndYearAndEstablishment(speciality.getId(), year.getId(), establishment.getId());
				return GeneralStatisticsDto.SpecialtyData.builder().title(speciality.getTitle()).numberOfStudents(numberOfStudent).numberOfClass(numberOfClass).build();

			}).toList());
		generalStatisticsDto.setClassesData(classeRepository.getAllByIdYearAndIdEstablishment(year.getId(),establishment.getId()).stream().map(classe -> {
			int numberOfStudent = studentRepository.countByClasses(classe.getId());
			return GeneralStatisticsDto.ClassesData.builder().numberOfStudents(numberOfStudent).title(classe.getTitle()).build();
		}).toList());

		return generalStatisticsDto;
	}

	@Transactional

	public StudentsBreaksDto getStudentsBreaks() {

		OriachadUser user = helperService.getCurrentUser().getOriachadUser();
		Year year = helperService.getCurrentYear();
		Establishment establishment = helperService.getCurrentEstablishment();
		StudentsBreaksDto studentsBreaksDto = StudentsBreaksDto.builder().build();
		studentsBreaksDto.setYearTitle(year.getTitle());
		studentsBreaksDto.setEstablishmentName(establishment.getName());
		studentsBreaksDto.setWilayaName(establishment.getCommune().getWilaya().getName());
		studentsBreaksDto.setUserName(user.getFirstName() + " " + user.getLastName());
		studentsBreaksDto.setClassBreakDays(classeRepository.getAllByIdYearAndIdEstablishment(year.getId(), establishment.getId()).stream().map(classe -> getStudentsBreakDays(classe)).toList());
		return studentsBreaksDto;
	}

	@Transactional

	public StudentsDiseasesDto getStudentsDiseases() {
		OriachadUser user = helperService.getCurrentUser().getOriachadUser();
		Year year = helperService.getCurrentYear();
		Establishment establishment = helperService.getCurrentEstablishment();
		StudentsDiseasesDto studentsBreaksDto = StudentsDiseasesDto.builder().build();
		studentsBreaksDto.setYearTitle(year.getTitle());
		studentsBreaksDto.setEstablishmentName(establishment.getName());
		studentsBreaksDto.setWilayaName(establishment.getCommune().getWilaya().getName());
		studentsBreaksDto.setUserName(user.getFirstName() + " " + user.getLastName());
		studentsBreaksDto.setDiseasesStudents(studentRepository.getAllByDiseases(year.getId(),establishment.getId()).stream().map(student -> {
			Classe classe = classeRepository.findByIdYearAndStudent(year.getId(), student.getId());
			return StudentsDiseasesDto.DiseasesStudent.builder().classTitle(classe.getTitle()).fullName(String.format("%s %s", student.getFirstName(), student.getLastName())).build();
		}).toList());
		return studentsBreaksDto;
	}

	@Transactional

	public StudentsNeedsDto getStudentsNeeds() {
		OriachadUser user = helperService.getCurrentUser().getOriachadUser();
		Year year = helperService.getCurrentYear();
		Establishment establishment = helperService.getCurrentEstablishment();
		StudentsNeedsDto studentsBreaksDto = StudentsNeedsDto.builder().build();
		studentsBreaksDto.setYearTitle(year.getTitle());
		studentsBreaksDto.setEstablishmentName(establishment.getName());
		studentsBreaksDto.setWilayaName(establishment.getCommune().getWilaya().getName());
		studentsBreaksDto.setUserName(user.getFirstName() + " " + user.getLastName());
		studentsBreaksDto.setStudents(studentRepository.getAllByNeeds(year.getId(),establishment.getId()).stream().map(student -> {
			Classe classe = classeRepository.findByIdYearAndStudent(year.getId(), student.getId());
			return StudentsNeedsDto.Student.builder().classTitle(classe.getTitle()).fullName(String.format("%s %s", student.getFirstName(), student.getLastName())).build();
		}).toList());
		return studentsBreaksDto;
	}

	@Transactional

	public StudentsNeedsDto getStudentsOrphans() {
		OriachadUser user = helperService.getCurrentUser().getOriachadUser();
		Year year = helperService.getCurrentYear();
		Establishment establishment = helperService.getCurrentEstablishment();
		StudentsNeedsDto studentsBreaksDto = StudentsNeedsDto.builder().build();
		studentsBreaksDto.setYearTitle(year.getTitle());
		studentsBreaksDto.setEstablishmentName(establishment.getName());
		studentsBreaksDto.setWilayaName(establishment.getCommune().getWilaya().getName());
		studentsBreaksDto.setUserName(user.getFirstName() + " " + user.getLastName());
		studentsBreaksDto.setStudents(studentRepository.getAllByOrphans(year.getId(),establishment.getId()).stream().map(student -> {
			Classe classe = classeRepository.findByIdYearAndStudent(year.getId(), student.getId());
			return StudentsNeedsDto.Student.builder().classTitle(classe.getTitle()).fullName(String.format("%s %s", student.getFirstName(), student.getLastName())).build();
		}).toList());
		return studentsBreaksDto;
	}


	/**
	 * support method
	 */
	private StudentsBreaksDto.ClassBreakDay getStudentsBreakDays(Classe classe) {
		var breakDay = StudentsBreaksDto.ClassBreakDay.builder().title(classe.getTitle()).build();
		classe.getBreaks().forEach(classeBreak -> {

			if (classeBreak.getBreakDay() == 1) {
				breakDay.setSunday(String.format("%s - %s", classeBreak.getStartHour(), classeBreak.getEndHour()));

			}
			if (classeBreak.getBreakDay() == 2) {
				breakDay.setMonday(String.format("%s - %s", classeBreak.getEndHour(), classeBreak.getStartHour()));
			}
			if (classeBreak.getBreakDay() == 3) {
				breakDay.setTuesday(String.format("%s - %s", classeBreak.getEndHour(), classeBreak.getStartHour()));
			}
			if (classeBreak.getBreakDay() == 4) {
				breakDay.setWednesday(String.format("%s - %s", classeBreak.getEndHour(), classeBreak.getStartHour()));
			}
			if (classeBreak.getBreakDay() == 5) {
				breakDay.setThursday(String.format("%s - %s", classeBreak.getEndHour(), classeBreak.getStartHour()));
			}
		});
		return breakDay;
	}


	public StudentsMainsDto getStudentsMains() {
		OriachadUser user = helperService.getCurrentUser().getOriachadUser();
		Year year = helperService.getCurrentYear();
		Establishment establishment = helperService.getCurrentEstablishment();
		StudentsMainsDto studentsMainsDto = StudentsMainsDto.builder().build();
		studentsMainsDto.setYearTitle(year.getTitle());
		studentsMainsDto.setEstablishmentName(establishment.getName());
		studentsMainsDto.setWilayaName(establishment.getCommune().getWilaya().getName());
		studentsMainsDto.setUserName(user.getFirstName() + " " + user.getLastName());
		studentsMainsDto.setStudents(studentRepository.getAllByMains(year.getId(),establishment.getId()).stream().map(student -> {
			Classe classe = classeRepository.findByIdYearAndStudent(year.getId(), student.getId());
			return StudentsMainsDto.Student.builder().classTitle(classe.getTitle()).fullName(String.format("%s %s", student.getFirstName(), student.getLastName())).build();
		}).toList());
		return studentsMainsDto;
	}
}
