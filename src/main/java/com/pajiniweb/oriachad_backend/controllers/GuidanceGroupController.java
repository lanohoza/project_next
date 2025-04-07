package com.pajiniweb.oriachad_backend.controllers;

import com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos.AddEditGuidanceGroupDto;
import com.pajiniweb.oriachad_backend.domains.dtos.GuidanceGroupDto;
import com.pajiniweb.oriachad_backend.domains.dtos.StudentDto;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.services.GuidanceGroupService;
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
@RequestMapping("/api/v1/guidance_groups")
public class GuidanceGroupController {
	private static final Logger LOGGER = LoggerFactory.getLogger(GuidanceGroupController.class);

	@Autowired
	GuidanceGroupService guidanceGroupService;

	@GetMapping("/search")
	public ResponseEntity<Page<GuidanceGroupDto>> searchGuidanceGroups(@RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "-1") Long idYear, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") int size) {
		LOGGER.info(Messages.START_FUNCTION, "findAll");
		Pageable pageable = PageRequest.of(page < 0 ? 0 : page, size);
		return new ResponseEntity<>(guidanceGroupService.searchGuidanceGroups(search, idYear, pageable), HttpStatus.OK);
	}

	@GetMapping("/findById/{id}")
	public GuidanceGroupDto findById(@PathVariable Long id) {
		return guidanceGroupService.findById(id);
	}

	@GetMapping("/findByIdToEdit/{id}")
	public GuidanceGroupDto findByIdToEdit(@PathVariable Long id) {
		return guidanceGroupService.findByIdToEdit(id);
	}

	@PostMapping("/save")
	public boolean create(@RequestBody @Valid AddEditGuidanceGroupDto addEditGuidanceGroupDto) throws Exception {
		LOGGER.info(Messages.START_FUNCTION, "create");
		return guidanceGroupService.create(addEditGuidanceGroupDto);
	}

	@PutMapping("/update")
	public boolean update(@RequestBody @Valid AddEditGuidanceGroupDto addEditGuidanceGroupDto) {
		LOGGER.info(Messages.START_FUNCTION, "update");
		return guidanceGroupService.update(addEditGuidanceGroupDto);

	}

	@DeleteMapping("/delete/{id}")
	public boolean deleteById(@PathVariable Long id) {
		LOGGER.info(Messages.START_FUNCTION, "deleteById");
		return guidanceGroupService.deleteById(id);

	}

	@GetMapping("/current-year")
	public ResponseEntity<List<GuidanceGroupDto>> getGuidanceGroupByCurrentYear() {
		LOGGER.info(Messages.START_FUNCTION, "getStudentByCurrentYear");
		List<GuidanceGroupDto> result = guidanceGroupService.getGuidanceGroupByCurrentYear();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}


}
