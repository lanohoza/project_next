package com.pajiniweb.oriachad_backend.controllers;

import com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos.AddEditFollowUpDto;
import com.pajiniweb.oriachad_backend.domains.dtos.FollowUpDto;
import com.pajiniweb.oriachad_backend.domains.enums.FollowupStatus;
import com.pajiniweb.oriachad_backend.domains.enums.FollowUpType;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.services.FollowUpService;
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
@RequestMapping("/api/v1/follow-ups")
public class FollowUpController {
	private static final Logger LOGGER = LoggerFactory.getLogger(FollowUpController.class);

	@Autowired
	FollowUpService followUpService;

	@GetMapping("/search")
	public ResponseEntity<Page<FollowUpDto>> searchFlowUps(@RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "") String type, @RequestParam(defaultValue = "") String status, @RequestParam(defaultValue = "-1") Long idYear, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") int size) {
		LOGGER.info(Messages.START_FUNCTION, "findAll");
		Pageable pageable = PageRequest.of(page < 0 ? 0 : page, size);

		return new ResponseEntity<>(followUpService.searchFlowUps(search,!status.isBlank() ? FollowUpType.valueOf(type) : null,!status.isBlank() ? FollowupStatus.valueOf(status) : null, idYear, pageable), HttpStatus.OK);
	}

	@GetMapping("/findById/{id}")
	public FollowUpDto findById(@PathVariable Long id) {
		return followUpService.findById(id);
	}

	@PostMapping("/save")
	public boolean create(@RequestBody @Valid AddEditFollowUpDto addEditFollowUpDto) throws Exception {
		LOGGER.info(Messages.START_FUNCTION, "create");
		followUpService.createFlowUp(addEditFollowUpDto);
		return true;
	}

	@PutMapping("/update")
	public boolean update(@RequestBody @Valid AddEditFollowUpDto addEditFollowUpDto) throws Exception {
		LOGGER.info(Messages.START_FUNCTION, "update");
		return followUpService.update(addEditFollowUpDto);

	}

	@DeleteMapping("/delete/{id}")
	public boolean deleteById(@PathVariable Long id) {
		LOGGER.info(Messages.START_FUNCTION, "deleteById");
		    return followUpService.deleteById(id);

	}

	@GetMapping("/current-year/{type}")
	public ResponseEntity<List<FollowUpDto>> getFlowUpByCurrentYear(@PathVariable FollowUpType type) {
		LOGGER.info(Messages.START_FUNCTION, "getFlowUpByCurrentYear");
		List<FollowUpDto> result = followUpService.getFlowUpByCurrentYear(type);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/end/{id}")
	public boolean endFlowUp(@PathVariable Long id) throws CloneNotSupportedException {
		LOGGER.info(Messages.START_FUNCTION, "doInterview");
		return followUpService.endFlowUp(id);
	}


}
