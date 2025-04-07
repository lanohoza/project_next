package com.pajiniweb.oriachad_backend.actions.controllers;

import com.pajiniweb.oriachad_backend.actions.domains.dtos.ActivityDto;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.actions.services.ActivitiesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/activities")
public class ActivityController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ActivityController.class);

	private final ActivitiesService activitiesService;

	@Autowired
	public ActivityController(ActivitiesService activitiesService) {
		this.activitiesService = activitiesService;
	}


	@GetMapping("/search")
	public ResponseEntity<Page<ActivityDto>> searchActivities(@RequestParam(defaultValue = "") String search, @RequestParam(required = false) String start, @RequestParam(required = false) String end, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") int size) {
		LOGGER.info(Messages.START_FUNCTION, "searchActivities");
		Pageable pageable = PageRequest.of(page < 0 ? 0 : page, size);
		Page<ActivityDto> activitiesDtos = activitiesService.searchActivities(search, start, end, pageable);
		return new ResponseEntity<>(activitiesDtos, HttpStatus.OK);
	}

}
