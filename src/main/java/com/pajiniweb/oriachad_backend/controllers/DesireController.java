package com.pajiniweb.oriachad_backend.controllers;

import com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos.AddEditDesireDto;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.services.DesireService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/desires")
@AllArgsConstructor
public class DesireController {
	private static final Logger LOGGER = LoggerFactory.getLogger(DesireController.class);

	private final DesireService desireService;


	@PostMapping("save")
	public ResponseEntity<Boolean> saveDesire(@RequestBody @Valid AddEditDesireDto addEditDesireDto) {
		LOGGER.info("Start createDesire");
		boolean result = desireService.saveDesire(addEditDesireDto);
		return new ResponseEntity<>(result, HttpStatus.CREATED);

	}

	@DeleteMapping("/delete/{studentId}/{idGuidanceSpeciality}")
	public ResponseEntity<Boolean> deleteById(@PathVariable Long studentId, @PathVariable Long idGuidanceSpeciality) {
		LOGGER.info(Messages.START_FUNCTION, "deleteById");
		boolean result = desireService.deleteById(studentId, idGuidanceSpeciality);
		return new ResponseEntity<>(result, HttpStatus.OK);

	}

}
