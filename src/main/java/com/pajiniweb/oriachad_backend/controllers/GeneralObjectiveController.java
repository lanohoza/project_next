package com.pajiniweb.oriachad_backend.controllers;

import com.pajiniweb.oriachad_backend.domains.dtos.GeneralObjectiveDto;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.services.GeneralObjectiveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1/general-objectives")
public class GeneralObjectiveController {
	private static final Logger LOGGER = LoggerFactory.getLogger(GeneralObjectiveController.class);

	@Autowired
	GeneralObjectiveService generalObjectiveService;


	// Create
	@PostMapping("/save")
	public Boolean create(@RequestBody GeneralObjectiveDto generalObjectiveDTO) {
		LOGGER.info(Messages.START_FUNCTION, "create");
		return generalObjectiveService.save(generalObjectiveDTO);
	}

	@PostMapping("/saveFromAdmin")
	public Boolean createFromAdministration(@RequestBody GeneralObjectiveDto generalObjectiveDTO) {
		LOGGER.info(Messages.START_FUNCTION, "create");
		return generalObjectiveService.saveFromAdmin(generalObjectiveDTO);
	}

	// Delete
	@DeleteMapping("/delete/{id}")
	public boolean deleteById(@PathVariable Long id) throws Exception {
		LOGGER.info(Messages.START_FUNCTION, "deleteById");
		return generalObjectiveService.deleteById(id);
	}

	// Find by createdBy
	@GetMapping("/findByCreatedBy")
	public Page<GeneralObjectiveDto> findByCreatedBy(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		LOGGER.info(Messages.START_FUNCTION, "findByCreatedBy");
		return generalObjectiveService.findByCreatedBy(PageRequest.of(page, size));
	}

	@GetMapping("/allCreatedBy")
	public List<GeneralObjectiveDto> findByCreatedBy() {
		LOGGER.info(Messages.START_FUNCTION, "findByCreatedBy");
		return generalObjectiveService.findByCreatedBy();
	}

	@GetMapping("/allCreatedByAdmin")
	public List<GeneralObjectiveDto> findCreatedByAdmin() {
		LOGGER.info(Messages.START_FUNCTION, "findCreatedByAdmin");
		return generalObjectiveService.findCreatedByAdmin();
	}
}
