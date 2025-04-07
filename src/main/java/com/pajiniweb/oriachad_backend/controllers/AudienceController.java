package com.pajiniweb.oriachad_backend.controllers;

import com.pajiniweb.oriachad_backend.domains.dtos.AudienceDto;
import com.pajiniweb.oriachad_backend.domains.entities.Audience;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.services.AudienceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/audiences")
public class AudienceController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AudienceController.class);

	@Autowired
	AudienceService audienceService;

	@GetMapping("/all")
	public List<AudienceDto> findAll() {
		LOGGER.info(Messages.START_FUNCTION, "findAll");
		return audienceService.findAll();
	}

/*	@GetMapping("/findById/{id}")
	public Audience findById(@PathVariable Long id) {
		LOGGER.info(Messages.START_FUNCTION, "findById");
		return audienceService.findById(id).orElseThrow(() -> new RuntimeException("Audience not found with id: " + id));
	}

	@PostMapping("/save")
	public Audience create(@RequestBody Audience audience) {
		LOGGER.info(Messages.START_FUNCTION, "create");
		return audienceService.save(audience);
	}

	@PutMapping("/update/{id}")
	public Audience update(@PathVariable Long id, @RequestBody Audience audience) {
		LOGGER.info(Messages.START_FUNCTION, "update");
		return audienceService.update(id, audience);
	}

	@DeleteMapping("/delete/{id}")
	public boolean deleteById(@PathVariable Long id) {
		LOGGER.info(Messages.START_FUNCTION, "deleteById");
		return audienceService.deleteById(id);
	}*/
}
