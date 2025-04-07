package com.pajiniweb.oriachad_backend.services.documents;

import com.pajiniweb.oriachad_backend.actions.repositories.ActivityRepository;
import com.pajiniweb.oriachad_backend.domains.dtos.documents.*;
import com.pajiniweb.oriachad_backend.domains.entities.*;
import com.pajiniweb.oriachad_backend.helps.Functions;
import com.pajiniweb.oriachad_backend.repositories.TaskRepository;
import com.pajiniweb.oriachad_backend.repositories.TechnicalCardRepository;
import com.pajiniweb.oriachad_backend.services.HelperService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
public class YearProgramDocumentService {
	private static final Logger LOGGER = LoggerFactory.getLogger(YearProgramDocumentService.class);

	final HelperService helperService;
	final DateTimeFormatter dateTimeFormatter;
	final ActivityRepository activityRepository;
	private final TaskRepository taskRepository;
	private final TechnicalCardRepository technicalCardRepository;

	@Transactional
	public CurrentYearProgramDto getCurrentYearProgramData() {
		OriachadUser user = helperService.getCurrentUser().getOriachadUser();
		Year year = helperService.getCurrentYear();
		Establishment establishment = helperService.getCurrentEstablishment();
		CurrentYearProgramDto currentYearProgramDto = CurrentYearProgramDto.builder().build();
		currentYearProgramDto.setYearTitle(year.getTitle());
		currentYearProgramDto.setEstablishmentName(establishment.getName());
		currentYearProgramDto.setWilayaName(establishment.getCommune().getWilaya().getName());
		currentYearProgramDto.setUserName(user.getFirstName() + " " + user.getLastName());
		currentYearProgramDto.setTasks(taskRepository.findByIdYear(year.getId(), user.getId()).stream().map(task -> CurrentYearProgramDto.CurrentYearProgramTaskDto.builder().id(task.getId()).taskTitle(task.getTechnicalCard().getTitle()).month(Functions.getMonthTitle(task.getTechnicalCard().getRunMonth())).week(Functions.getWeekTitle(task.getTechnicalCard().getRunWeek())).generalObjectsTitles(task.getTechnicalCard().getGeneralObjectives().stream().map(generalObject -> IdValueDto.builder().id(generalObject.getId()).value(generalObject.getContent()).build()).toList()).build()).toList());

		return currentYearProgramDto;
	}

	public TechnicalCardDocumentDto getTechnicalCardData(Long idTechnicalCard) {
		OriachadUser user = helperService.getCurrentUser().getOriachadUser();
		Year year = helperService.getCurrentYear();
		Establishment establishment = helperService.getCurrentEstablishment();
		TechnicalCard technicalCard = technicalCardRepository.findById(idTechnicalCard).orElseThrow();

		TechnicalCardDocumentDto technicalCardDocumentDto = TechnicalCardDocumentDto.builder().build();
		technicalCardDocumentDto.setYearTitle(year.getTitle());
		technicalCardDocumentDto.setCode(technicalCard.getCode());
		technicalCardDocumentDto.setType(technicalCard.getType().name());
		technicalCardDocumentDto.setCategory(technicalCard.getCategory().getName());
		technicalCardDocumentDto.setAudiences(technicalCard.getAudiences().stream().map(Audience::getName).toList());
		technicalCardDocumentDto.setOperateObjectsTitles(technicalCard.getGeneralObjectives().stream().map(GeneralObjective::getOperateObjectives).flatMap(Collection::stream).map(OperateObjective::getContent).toList());
		technicalCardDocumentDto.setMonthTitle(Functions.getMonthTitle(technicalCard.getRunMonth()));
		technicalCardDocumentDto.setWeekTitle(Functions.getMonthTitle(technicalCard.getRunWeek()));
		technicalCardDocumentDto.setEstablishmentName(establishment.getName());
		technicalCardDocumentDto.setWilayaName(establishment.getCommune().getWilaya().getName());
		technicalCardDocumentDto.setUserName(user.getFirstName() + " " + user.getLastName());
		technicalCardDocumentDto.setFeedback(technicalCard.getFeedback());

		technicalCardDocumentDto.setTitle(technicalCard.getTitle());
		technicalCardDocumentDto.setOfficialTxts(technicalCard.getOfficialTxts().stream().map(officialTxt -> TechnicalCardDocumentDto.OfficialTxtDto.builder().id(officialTxt.getId()).title(officialTxt.getTitle()).number(officialTxt.getNumber()).date(officialTxt.getDate()).build()).toList());

		technicalCardDocumentDto.setGeneralObjectsTitles(technicalCard.getGeneralObjectives().stream().map(GeneralObjective::getContent).toList());
		technicalCardDocumentDto.setHumanTools(technicalCard.getHumanTools().stream().map(humanTool -> String.format("%s %s", humanTool.getFirstName(), humanTool.getLastName())).toList());
		technicalCardDocumentDto.setMaterielToots(technicalCard.getMaterielToots());
		technicalCardDocumentDto.setDifficulties(technicalCard.getDifficulties().stream().map(Difficulty::getTitle).toList());


		return technicalCardDocumentDto;
	}

	public List<TechnicalCardDocumentDto> getAllTechnicalCardData() {

		OriachadUser user = helperService.getCurrentUser().getOriachadUser();
		Year year = helperService.getCurrentYear();
		Establishment establishment = helperService.getCurrentEstablishment();
		return technicalCardRepository.findByIdCreatedBy(user.getId()).stream().map(technicalCard -> {
			TechnicalCardDocumentDto technicalCardDocumentDto = TechnicalCardDocumentDto.builder().build();
			technicalCardDocumentDto.setYearTitle(year.getTitle());
			technicalCardDocumentDto.setCode(technicalCard.getCode());
			technicalCardDocumentDto.setType(technicalCard.getType().name());
			technicalCardDocumentDto.setFeedback(technicalCard.getFeedback());
			technicalCardDocumentDto.setCategory(technicalCard.getCategory().getName());
			technicalCardDocumentDto.setAudiences(technicalCard.getAudiences().stream().map(Audience::getName).toList());
			technicalCardDocumentDto.setOperateObjectsTitles(technicalCard.getGeneralObjectives().stream().map(GeneralObjective::getOperateObjectives).flatMap(Collection::stream).map(OperateObjective::getContent).toList());
			technicalCardDocumentDto.setMonthTitle(Functions.getMonthTitle(technicalCard.getRunMonth()));
			technicalCardDocumentDto.setWeekTitle(Functions.getMonthTitle(technicalCard.getRunWeek()));
			technicalCardDocumentDto.setEstablishmentName(establishment.getName());
			technicalCardDocumentDto.setWilayaName(establishment.getCommune().getWilaya().getName());
			technicalCardDocumentDto.setUserName(user.getFirstName() + " " + user.getLastName());

			technicalCardDocumentDto.setTitle(technicalCard.getTitle());
			technicalCardDocumentDto.setOfficialTxts(technicalCard.getOfficialTxts().stream().map(officialTxt -> TechnicalCardDocumentDto.OfficialTxtDto.builder().id(officialTxt.getId()).title(officialTxt.getTitle()).number(officialTxt.getNumber()).date(officialTxt.getDate()).build()).toList());

			technicalCardDocumentDto.setGeneralObjectsTitles(technicalCard.getGeneralObjectives().stream().map(GeneralObjective::getContent).toList());
			technicalCardDocumentDto.setHumanTools(technicalCard.getHumanTools().stream().map(humanTool -> String.format("%s %s", humanTool.getFirstName(), humanTool.getLastName())).toList());
			technicalCardDocumentDto.setMaterielToots(technicalCard.getMaterielToots());
			technicalCardDocumentDto.setDifficulties(technicalCard.getDifficulties().stream().map(Difficulty::getTitle).toList());

			return technicalCardDocumentDto;

		}).toList();


	}
}
