package com.pajiniweb.oriachad_backend.controllers;

import com.pajiniweb.oriachad_backend.domains.dtos.CommuneDto;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.services.CommuneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/communes")
public class CommuneController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CommuneController.class);

	@Autowired
	private CommuneService communeService;

	@GetMapping("/all")
	public List<CommuneDto> findAll() {
		LOGGER.info(Messages.START_FUNCTION, "findAll Commune");
		return communeService.findAll();
	}

	@GetMapping("/findById/{id}")
	public CommuneDto findById(@PathVariable Long id) {
		LOGGER.info(Messages.START_FUNCTION, "findById Commune");
		return communeService.findById(id);
	}

	@GetMapping("/findByIdWilaya/{idWilaya}")
	public List<CommuneDto> findByIdWilaya(@PathVariable Long idWilaya) {
		LOGGER.info(Messages.START_FUNCTION, "findByIdDaira Commune");
		return communeService.findByIdWilaya(idWilaya);
	}

	/*@PostMapping("/save")
	public CommuneDto create(@RequestBody CommuneDto communeDTO) {
		LOGGER.info(Messages.START_FUNCTION, "create Commune");
		return communeService.save(communeDTO);
	}

	@PutMapping("/update/{id}")
	public CommuneDto update(@PathVariable Long id, @RequestBody CommuneDto communeDTO) {
		LOGGER.info(Messages.START_FUNCTION, "update Commune");
		return communeService.update(id, communeDTO);
	}

	@DeleteMapping("/delete/{id}")
	public boolean deleteById(@PathVariable Long id) {
		LOGGER.info(Messages.START_FUNCTION, "deleteById Commune");
		return communeService.deleteById(id);
	}*/
}
