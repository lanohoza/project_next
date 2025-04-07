package com.pajiniweb.oriachad_backend.actions.services;

import com.pajiniweb.oriachad_backend.actions.domains.dtos.ActivityDto;
import com.pajiniweb.oriachad_backend.actions.domains.entities.Activity;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.actions.repositories.ActivityRepository;
import com.pajiniweb.oriachad_backend.services.HelperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class ActivitiesService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ActivitiesService.class);

	final ActivityRepository activityRepository;
	final HelperService helperService;
	final DateTimeFormatter dateTimeFormatter;

	@Autowired
	public ActivitiesService(ActivityRepository activityRepository, HelperService helperService, DateTimeFormatter dateTimeFormatter) {

		this.activityRepository = activityRepository;
		this.helperService = helperService;
		this.dateTimeFormatter = dateTimeFormatter;
	}


	public Page<ActivityDto> searchActivities(String search, String sStart, String sEnd, Pageable pageable) {
		LOGGER.info(Messages.START_FUNCTION, "findAll classe");

		LocalDate start = (sStart != null && !sStart.isEmpty()) ? LocalDate.parse(sStart, dateTimeFormatter) : null;
		LocalDate end = (sEnd != null && !sEnd.isEmpty()) ? LocalDate.parse(sEnd, dateTimeFormatter) : null;

		Page<Activity> activities = activityRepository.searchActivities(search, helperService.getCurrentUser().getIdUser(), start, end, pageable);
		return activities.map(activity -> ActivityDto.builder()
				.idUser(activity.getIdUser())
				.createdDate(activity.getCreatedDate())
				.content(activity.getContent())
				.type(activity.getType())
				.id(activity.getId())
				.build());
	}

	public void actionActivitySave(Long id) {
		//activityRepository.save(Activity.builder().action(Action.builder().id(id).build()).build());
	}


}
