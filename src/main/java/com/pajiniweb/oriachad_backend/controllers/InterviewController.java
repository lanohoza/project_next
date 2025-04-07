package com.pajiniweb.oriachad_backend.controllers;

import com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos.AddEditInterviewDto;
import com.pajiniweb.oriachad_backend.domains.dtos.DoInterviewDto;
import com.pajiniweb.oriachad_backend.domains.dtos.EndInterviewDto;
import com.pajiniweb.oriachad_backend.domains.dtos.InterviewDetailDto;
import com.pajiniweb.oriachad_backend.domains.dtos.InterviewDto;
import com.pajiniweb.oriachad_backend.domains.enums.FollowupStatus;
import com.pajiniweb.oriachad_backend.domains.enums.InterviewStatus;
import com.pajiniweb.oriachad_backend.domains.enums.InterviewType;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.services.InterviewService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/interviews")
public class InterviewController {
	private static final Logger LOGGER = LoggerFactory.getLogger(InterviewController.class);

	@Autowired
	InterviewService interviewService;

	@GetMapping("/search")
	public ResponseEntity<Page<InterviewDto>> searchInterviews(@RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "") String type, @RequestParam(defaultValue = "") String status, @RequestParam(defaultValue = "-1") Long idYear, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") int size) {
		LOGGER.info(Messages.START_FUNCTION, "findAll");
		Pageable pageable = PageRequest.of(page < 0 ? 0 : page, size);
		InterviewType typeInterview = type.isEmpty() ? null : InterviewType.valueOf(type);
		return new ResponseEntity<>(interviewService.searchInterviews(search, typeInterview, !status.isBlank() ? InterviewStatus.valueOf(status) : null, idYear, pageable), HttpStatus.OK);
	}

	@GetMapping("/findById/{id}")
	public InterviewDto findById(@PathVariable Long id) {
		return interviewService.findById(id);
	}
	@GetMapping("/getDetailsById/{id}")
	public InterviewDetailDto getDetailsById(@PathVariable Long id) {
		return interviewService.getDetailsById(id);
	}

	@PostMapping("/save")
	public Long create(@RequestBody @Valid AddEditInterviewDto addEditInterviewDto) throws Exception {
		LOGGER.info(Messages.START_FUNCTION, "create");

		return interviewService.createInterview(addEditInterviewDto);
	}

	@PutMapping("/update")
	public boolean update(@RequestBody @Valid AddEditInterviewDto addEditInterviewDto) throws Exception {
		LOGGER.info(Messages.START_FUNCTION, "update");
		return interviewService.update(addEditInterviewDto);

	}

	@PostMapping("/doInterview")
	public boolean doInterview(@RequestBody DoInterviewDto doInterviewDto) {
		LOGGER.info(Messages.START_FUNCTION, "doInterview");
		return interviewService.doInterview(doInterviewDto);
	}

	@PostMapping("/endInterview")
	public boolean endInterview(@RequestBody EndInterviewDto endInterviewDto) throws CloneNotSupportedException {
		LOGGER.info(Messages.START_FUNCTION, "doInterview");
		return interviewService.endInterview(endInterviewDto);
	}

	@DeleteMapping("/delete/{id}")
	public boolean deleteById(@PathVariable Long id) {
		LOGGER.info(Messages.START_FUNCTION, "deleteById");
		return interviewService.deleteById(id);

	}


}
