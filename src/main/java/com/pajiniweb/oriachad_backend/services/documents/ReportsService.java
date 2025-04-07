package com.pajiniweb.oriachad_backend.services.documents;

import com.pajiniweb.oriachad_backend.actions.domains.dtos.ActivityDto;
import com.pajiniweb.oriachad_backend.actions.domains.entities.Activity;
import com.pajiniweb.oriachad_backend.actions.domains.entities.ShedSetting;
import com.pajiniweb.oriachad_backend.actions.repositories.ActivityRepository;
import com.pajiniweb.oriachad_backend.domains.dtos.FollowUpDto;
import com.pajiniweb.oriachad_backend.domains.dtos.InterviewDto;
import com.pajiniweb.oriachad_backend.domains.dtos.documents.ActivityByDateDto;
import com.pajiniweb.oriachad_backend.domains.dtos.documents.*;
import com.pajiniweb.oriachad_backend.domains.entities.*;
import com.pajiniweb.oriachad_backend.domains.enums.FollowUpType;
import com.pajiniweb.oriachad_backend.domains.enums.InterviewType;
import com.pajiniweb.oriachad_backend.domains.enums.TypeEstablishment;
import com.pajiniweb.oriachad_backend.helps.Functions;
import com.pajiniweb.oriachad_backend.repositories.*;
import com.pajiniweb.oriachad_backend.services.HelperService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReportsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReportsService.class);

	final HelperService helperService;
	final DateTimeFormatter dateTimeFormatter;
	final ActivityRepository activityRepository;
	final TaskRepository taskRepository;
	final LevelRepository levelRepository;
	private final ClasseRepository classeRepository;
	private final StudentRepository studentRepository;
	private final SpecialityRepository specialityRepository;
	private final InterviewRepository interviewRepository;
	private final FollowUpRepository followUpRepository;

	@Transactional
	public ActivitiesReportDto getActivitiesReportData() {

		OriachadUser user = helperService.getCurrentUser().getOriachadUser();
		Year year = helperService.getCurrentYear();
		Establishment establishment = helperService.getCurrentEstablishment();
		ActivitiesReportDto activitiesReportDto = ActivitiesReportDto.builder().build();
		List<Object[]> activities = activityRepository.getActivitiesByYear(user.getId(), year.getStart(), year.getEnd());
		Map<LocalDate, List<ActivityDto>> activitiesByDate = new HashMap<>();

		for (Object[] result : activities) {
			LocalDate date = (LocalDate) result[0];
			ActivityDto activityDto = ActivityDto.builder().idUser(((Activity) result[1]).getIdUser()).createdDate(((Activity) result[1]).getCreatedDate()).content(((Activity) result[1]).getContent()).id(((Activity) result[1]).getId()).build();
			//					.fullName(String.format("%s : %s",((Activity)result[1]).getActionTask().getTask().getTechnicalCard().getTitle(),((Activity)result[1]).getActionTask().getAction().getTitle()))
			activitiesByDate.computeIfAbsent(date, k -> new ArrayList<>()).add(activityDto);
		}
		List<ActivityByDateDto> activityByDateDtos = new ArrayList<>();
		for (Map.Entry<LocalDate, List<ActivityDto>> entry : activitiesByDate.entrySet()) {
			ActivityByDateDto dto = new ActivityByDateDto(entry.getKey(), entry.getValue());
			activityByDateDtos.add(dto);
		}

		//List<Task> tasks = taskRepository.getFinishedTaskByDates(user.getId(), year.getStart(), year.getEnd());
		//activitiesReportDto.setTasks(tasks.stream().map(task -> ActivitiesReportDto.TaskReportDto.builder().title(task.getTechnicalCard().getTitle()).date(task.getCreatedDate()).build()).toList());
		activitiesReportDto.setActivities(activityByDateDtos);
		activitiesReportDto.setYearTitle(year.getTitle());
		activitiesReportDto.setYearTitle(year.getTitle());
		activitiesReportDto.setEstablishmentName(establishment.getName());
		activitiesReportDto.setWilayaName(establishment.getCommune().getWilaya().getName());
		activitiesReportDto.setUserName(user.getFirstName() + " " + user.getLastName());
		return activitiesReportDto;
	}

	@Transactional

	public TmsReportDto getTmsReportData(Long idTrimestre) {
		OriachadUser user = helperService.getCurrentUser().getOriachadUser();
		Year year = helperService.getCurrentYear();
		Establishment establishment = helperService.getCurrentEstablishment();
		TmsReportDto tmsReportDto = TmsReportDto.builder().build();
		tmsReportDto.setYearTitle(year.getTitle());
		tmsReportDto.setEstablishmentName(establishment.getName());
		tmsReportDto.setWilayaName(establishment.getCommune().getWilaya().getName());
		tmsReportDto.setUserName(user.getFirstName() + " " + user.getLastName());
		tmsReportDto.setType(establishment.getType());
		tmsReportDto.setGlobalData(TmsReportDto.GlobalData.builder().build());
		levelRepository.findAllByType(establishment.getType()).forEach(level -> {
			int numberOfClass = classeRepository.countByIdLevelAndIdYearAndIdEstablishment(level.getId(), year.getId(), establishment.getId());
			int numberOfStudent = studentRepository.countByLevelAndYearAndEstablishment(level.getId(), year.getId(), establishment.getId());
			if (level.getNumber() == 1) {
				tmsReportDto.getGlobalData().setFirstNumberOfClass(numberOfClass);
				tmsReportDto.getGlobalData().setFirstNumberOfStudent(numberOfStudent);
			}
			if (level.getNumber() == 2) {
				tmsReportDto.getGlobalData().setSecondNumberOfClass(numberOfClass);
				tmsReportDto.getGlobalData().setSecondNumberOfStudent(numberOfStudent);
			}
			if (level.getNumber() == 3) {
				tmsReportDto.getGlobalData().setThirdNumberOfClass(numberOfClass);
				tmsReportDto.getGlobalData().setThirdNumberOfStudent(numberOfStudent);
			}
			if (level.getNumber() == 4) {
				tmsReportDto.getGlobalData().setFourthNumberOfClass(numberOfClass);
				tmsReportDto.getGlobalData().setFourthNumberOfStudent(numberOfStudent);
			}
		});
		if (establishment.getType() == TypeEstablishment.secondary)
			tmsReportDto.setSpecialties(specialityRepository.getBySpecialityOfEstablishment(establishment.getId()).stream().map(speciality -> {
				int numberOfClass = classeRepository.countByIdSpecialityAndIdYearAndIdEstablishment(speciality.getId(), year.getId(), establishment.getId());
				int numberOfStudent = studentRepository.countBySpecialityAndYearAndEstablishment(speciality.getId(), year.getId(), establishment.getId());
				return TmsReportDto.SpecialtyData.builder().title(speciality.getTitle()).numberOfStudents(numberOfStudent).numberOfClass(numberOfClass).build();

			}).toList());
		tmsReportDto.setClassesData(classeRepository.getAllByIdYearAndIdEstablishment(year.getId(), establishment.getId()).stream().map(classe -> {
			int numberOfStudent = studentRepository.countByClasses(classe.getId());
			return TmsReportDto.ClassesData.builder().numberOfStudents(numberOfStudent).title(classe.getTitle()).build();
		}).toList());
		List<Task> tasks = taskRepository.getFinishedTaskByDates(user.getId(), year.getStart(), year.getEnd());
		tmsReportDto.setTasks(tasks.stream().map(task -> ActivitiesReportDto.TaskReportDto.builder().title(task.getTechnicalCard().getTitle()).date(task.getCreatedDate()).build()).toList());
		return tmsReportDto;
	}

	@Transactional
	public TaskReportDto getTaskReportData(Long idTask) {

		OriachadUser user = helperService.getCurrentUser().getOriachadUser();
		Year year = helperService.getCurrentYear();
		Establishment establishment = helperService.getCurrentEstablishment();
		Task task = taskRepository.findById(idTask).orElseThrow();

		TaskReportDto taskReportDto = TaskReportDto.builder().build();
		taskReportDto.setYearTitle(year.getTitle());
		taskReportDto.setTrimestreTitle("الاول");
		taskReportDto.setMonthTitle(Functions.getMonthTitle(task.getTechnicalCard().getRunMonth()));
		taskReportDto.setWeekTitle(Functions.getWeekTitle(task.getTechnicalCard().getRunWeek()));
		taskReportDto.setEstablishmentName(establishment.getName());
		taskReportDto.setWilayaName(establishment.getCommune().getWilaya().getName());
		taskReportDto.setUserName(user.getFirstName() + " " + user.getLastName());

		taskReportDto.setTaskTitle(task.getTechnicalCard().getTitle());
		taskReportDto.setOfficialTxts(task.getTechnicalCard().getOfficialTxts().stream().map(officialTxt -> TaskReportDto.OfficialTxtDto.builder().id(officialTxt.getId()).title(officialTxt.getTitle()).number(officialTxt.getNumber()).date(officialTxt.getDate()).build()).toList());
		taskReportDto.setActionTitles(task.getActions().stream().map(actionTask -> actionTask.getAction().getTitle()).toList());
		taskReportDto.setGeneralObjectsTitles(task.getTechnicalCard().getGeneralObjectives().stream().map(GeneralObjective::getContent).toList());
		taskReportDto.setHumanTools(task.getTechnicalCard().getHumanTools().stream().map(humanTool -> String.format("%s %s", humanTool.getFirstName(), humanTool.getLastName())).toList());
		taskReportDto.setMaterielToots(task.getTechnicalCard().getMaterielToots());
		taskReportDto.setDifficulties(task.getTechnicalCard().getDifficulties().stream().map(Difficulty::getTitle).toList());
		return taskReportDto;
	}

	public InterviewsReportDto geInterviewsReportData() {

		OriachadUser user = helperService.getCurrentUser().getOriachadUser();
		Year year = helperService.getCurrentYear();
		Establishment establishment = helperService.getCurrentEstablishment();
		InterviewsReportDto interviewsReportDto = InterviewsReportDto.builder().build();
		List<Object[]> interviews = interviewRepository.getInterviewsByYear(user.getId(), year.getId());
		List<InterviewByDateDto> interviewsByDateDtos = new ArrayList<>();
		Map<LocalDateTime, List<InterviewDto>> interviewsByDate = new HashMap<>();

		for (Object[] result : interviews) {
			LocalDateTime date = (LocalDateTime) result[0];
			Interview interview = (Interview) result[1];
			InterviewDto interviewDto = InterviewDto.builder().id(interview.getId()).number(interview.getNumber())
					.createdDate(interview.getCreatedDate())
					.status(interview.getStatus())
					.type(interview.getType())
					.target(interview.getType() == InterviewType.group ? interview.getGuidanceGroup().getTitle() : String.format("%s %s", interview.getStudent().getFirstName(), interview.getStudent().getLastName()))
					.shedCategory(interview.getShedCategory().getName())
					.shedSettings(interview.getShedSettings().stream().map(ShedSetting::getSyndromeDiagnostic).toList())
					.description(interview.getDescription()).createType(interview.getCreateType()).build();
			interviewsByDate.computeIfAbsent(date, k -> new ArrayList<>()).add(interviewDto);
		}
		for (Map.Entry<LocalDateTime, List<InterviewDto>> entry : interviewsByDate.entrySet()) {
			InterviewByDateDto dto = new InterviewByDateDto(entry.getKey(), entry.getValue());
			interviewsByDateDtos.add(dto);
		}
		interviewsReportDto.setInterviewByDateDtos(interviewsByDateDtos);
		interviewsReportDto.setYearTitle(year.getTitle());
		interviewsReportDto.setYearTitle(year.getTitle());
		interviewsReportDto.setEstablishmentName(establishment.getName());
		interviewsReportDto.setWilayaName(establishment.getCommune().getWilaya().getName());
		interviewsReportDto.setUserName(user.getFirstName() + " " + user.getLastName());
		return interviewsReportDto;
	}

	public FollowupReportDto getFollowupsReportData() {
		OriachadUser user = helperService.getCurrentUser().getOriachadUser();
		Year year = helperService.getCurrentYear();
		Establishment establishment = helperService.getCurrentEstablishment();
		FollowupReportDto followupReportDto = FollowupReportDto.builder().build();
		List<Object[]> interviews = followUpRepository.getFollowUpByYear(user.getId(), year.getId());
		List<FollowupByDateDto> followupByDateDtos = new ArrayList<>();
		Map<LocalDateTime, List<FollowUpDto>> followupsByDate = new HashMap<>();

		for (Object[] result : interviews) {
			LocalDateTime date = (LocalDateTime) result[0];
			FollowUp followUp  = (FollowUp) result[1];
			FollowUpDto followUpDto = FollowUpDto.builder().id(followUp.getId()).number(followUp.getNumber())
					.createdDate(followUp.getCreatedDate())
					.status(followUp.getStatus())
					.type(followUp.getType())
					.target(followUp.getType() == FollowUpType.group ? followUp.getGuidanceGroup().getTitle() : String.format("%s %s", followUp.getStudent().getFirstName(), followUp.getStudent().getLastName()))
					.shedCategory(followUp.getShedCategory().getName())
					.shedSettings(followUp.getShedSettings().stream().map(ShedSetting::getSyndromeDiagnostic).toList())
					.description(followUp.getDescription()).createType(followUp.getCreateType()).build();
			followupsByDate.computeIfAbsent(date, k -> new ArrayList<>()).add(followUpDto);
		}
		for (Map.Entry<LocalDateTime, List<FollowUpDto>> entry : followupsByDate.entrySet()) {
			FollowupByDateDto dto = new FollowupByDateDto(entry.getKey(), entry.getValue());
			followupByDateDtos.add(dto);
		}

		followupReportDto.setFlowupByDateDtos(followupByDateDtos);
		followupReportDto.setYearTitle(year.getTitle());
		followupReportDto.setYearTitle(year.getTitle());
		followupReportDto.setEstablishmentName(establishment.getName());
		followupReportDto.setWilayaName(establishment.getCommune().getWilaya().getName());
		followupReportDto.setUserName(user.getFirstName() + " " + user.getLastName());
		return followupReportDto;	}
}
