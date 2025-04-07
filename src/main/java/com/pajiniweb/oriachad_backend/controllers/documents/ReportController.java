package com.pajiniweb.oriachad_backend.controllers.documents;

import com.pajiniweb.oriachad_backend.domains.dtos.documents.*;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.services.documents.ReportsService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/repoerts")
public class ReportController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReportController.class);

	private final ReportsService reportsService;


	@GetMapping("/activities")
	public ResponseEntity<ActivitiesReportDto> getActivitiesReportData() {
		LOGGER.info(Messages.START_FUNCTION, "getActivitiesReportData");
		ActivitiesReportDto activitiesReportDto = reportsService.getActivitiesReportData();
		return new ResponseEntity<>(activitiesReportDto, HttpStatus.OK);
	}

	@GetMapping("/interviews")
	public ResponseEntity<InterviewsReportDto> geInterviewsReportData() {
		LOGGER.info(Messages.START_FUNCTION, "getActivitiesReportData");
		InterviewsReportDto interviewsReportDto = reportsService.geInterviewsReportData();
		return new ResponseEntity<>(interviewsReportDto, HttpStatus.OK);
	}

	@GetMapping("/followups")
	public ResponseEntity<FollowupReportDto> getFollowupsReportData() {
		LOGGER.info(Messages.START_FUNCTION, "getActivitiesReportData");
		FollowupReportDto followupReportDto = reportsService.getFollowupsReportData();
		return new ResponseEntity<>(followupReportDto, HttpStatus.OK);
	}

	@GetMapping("/tms/{idTrimestre}")
	public ResponseEntity<TmsReportDto> getTmsReportData(@PathVariable Long idTrimestre) {
		LOGGER.info(Messages.START_FUNCTION, "getActivitiesReportData");
		TmsReportDto tmsReportData = reportsService.getTmsReportData(idTrimestre);
		return new ResponseEntity<>(tmsReportData, HttpStatus.OK);
	}

	@GetMapping("/task/{idTask}")
	public ResponseEntity<TaskReportDto> getTaskReportData(@PathVariable Long idTask) {
		LOGGER.info(Messages.START_FUNCTION, "getTaskReportData");
		TaskReportDto taskReportDto = reportsService.getTaskReportData(idTask);
		return new ResponseEntity<>(taskReportDto, HttpStatus.OK);
	}


}
