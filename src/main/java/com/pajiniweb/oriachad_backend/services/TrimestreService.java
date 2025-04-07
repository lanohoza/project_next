package com.pajiniweb.oriachad_backend.services;

import com.pajiniweb.oriachad_backend.domains.dtos.TrimestreDto;
import com.pajiniweb.oriachad_backend.domains.entities.Trimestre;
import com.pajiniweb.oriachad_backend.exceptions.ResourceNotFoundException;
import com.pajiniweb.oriachad_backend.repositories.TrimestreRepository;
import com.pajiniweb.oriachad_backend.repositories.YearRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrimestreService {
	private static final Logger LOGGER = LoggerFactory.getLogger(TrimestreService.class);

	@Autowired
	private TrimestreRepository trimestreRepository;

	@Autowired
	private YearRepository yearRepository;

	public List<TrimestreDto> findAll() {
		LOGGER.info("Start findAll Trimestres");

		return trimestreRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
	}

	public TrimestreDto findById(Long id) {
		LOGGER.info("Start findById Trimestre");

		return trimestreRepository.findById(id).map(this::convertToDto).orElseThrow(() -> new ResourceNotFoundException("Trimestre not found"));
	}


	private TrimestreDto convertToDto(Trimestre trimestre) {
		return TrimestreDto.builder().id(trimestre.getId()).title(trimestre.getTitle()).number(trimestre.getNumber()).type(trimestre.getType()).idYear(trimestre.getIdYear()).start(trimestre.getStart()).end(trimestre.getEnd()).build();
	}


	public List<TrimestreDto> findAllByYear(Long idYear) {
		return trimestreRepository.findAllByIdYear(idYear).stream().map(this::convertToDto).collect(Collectors.toList());
	}
}
