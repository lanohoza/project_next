package com.pajiniweb.oriachad_backend.services.documents;

import com.pajiniweb.oriachad_backend.actions.domains.dtos.ActivityDto;
import com.pajiniweb.oriachad_backend.domains.dtos.documents.DailyNotebookDto;
import com.pajiniweb.oriachad_backend.actions.domains.entities.Activity;
import com.pajiniweb.oriachad_backend.domains.entities.Establishment;
import com.pajiniweb.oriachad_backend.domains.entities.OriachadUser;
import com.pajiniweb.oriachad_backend.domains.entities.Year;
import com.pajiniweb.oriachad_backend.actions.repositories.ActivityRepository;
import com.pajiniweb.oriachad_backend.services.HelperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class NoteBookService {
	private static final Logger LOGGER = LoggerFactory.getLogger(NoteBookService.class);

	final HelperService helperService;
	final DateTimeFormatter dateTimeFormatter;
	final ActivityRepository activityRepository;

	@Autowired
	public NoteBookService(HelperService helperService, DateTimeFormatter dateTimeFormatter, ActivityRepository activityRepository) {
		this.helperService = helperService;
		this.dateTimeFormatter = dateTimeFormatter;
		this.activityRepository = activityRepository;
	}


	public DailyNotebookDto getDailyNotebookData(String sDay) {
		LocalDate day = (sDay != null && !sDay.isEmpty()) ? LocalDate.parse(sDay, dateTimeFormatter) : null;
		OriachadUser user = helperService.getCurrentUser().getOriachadUser();
		Year year = helperService.getCurrentYear();
		Establishment establishment = helperService.getCurrentEstablishment();
		List<Activity> activities = activityRepository.getActivitiesByDay(user.getId(), day);
		DailyNotebookDto dailyNotebookDto = DailyNotebookDto.builder().build();
		dailyNotebookDto.setActivities(activities.stream().map(activity -> ActivityDto.builder()
				.idUser(activity.getIdUser())
				.createdDate(activity.getCreatedDate())
				.content(activity.getContent())
				.id(activity.getId())
				.build()).toList());
		dailyNotebookDto.setYearTitle(year.getTitle());
		dailyNotebookDto.setYearTitle(year.getTitle());
		dailyNotebookDto.setEstablishmentName(establishment.getName());
		dailyNotebookDto.setWilayaName(establishment.getCommune().getWilaya().getName());
		dailyNotebookDto.setUserName(user.getFirstName() + " " + user.getLastName());
		dailyNotebookDto.setDay(sDay);
		dailyNotebookDto.setReportNumber(LocalDate.now().getYear() + "/" + day.getDayOfYear());
		return dailyNotebookDto;
	}
}
