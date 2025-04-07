package com.pajiniweb.oriachad_backend.services;

import com.pajiniweb.oriachad_backend.domains.dtos.AudienceDto;
import com.pajiniweb.oriachad_backend.domains.entities.Audience;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.repositories.AudienceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AudienceService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AudienceService.class);

	@Autowired
	AudienceRepository audienceRepository;

	public List<AudienceDto> findAll() {
		LOGGER.info(Messages.START_FUNCTION, "findAll Audience");
		return audienceRepository.findAll().stream().map((audienceDto) -> AudienceDto.builder().id(audienceDto.getId()).name(audienceDto.getName()).build()).toList();

	}

	public Optional<Audience> findById(Long id) {
		LOGGER.info(Messages.START_FUNCTION, "findByIdAudience");
		try {
			return audienceRepository.findById(id);
		} catch (Exception e) {
			LOGGER.error("Error fetching audience by id", e);
			throw e;
		}
	}

	public Audience save(Audience audience) {
		LOGGER.info(Messages.START_FUNCTION, "save Audience");
		try {
			return audienceRepository.save(audience);
		} catch (Exception e) {
			LOGGER.error("Error saving audience", e);
			throw e;
		}
	}

	public Audience update(Long id, Audience audience) {
		LOGGER.info(Messages.START_FUNCTION, "update Audience");
		try {
			if (!audienceRepository.existsById(id)) {
				LOGGER.error(Messages.ENTITY_IS_NOT_EXIST, "Audience");
				throw new IllegalArgumentException(Messages.ENTITY_IS_NOT_EXIST.replace("{}", "Audience"));
			}
			audience.setId(id);
			return audienceRepository.save(audience);
		} catch (Exception e) {
			LOGGER.error("Error updating audience", e);
			throw e;
		}
	}

	public boolean deleteById(Long id) {
		LOGGER.info(Messages.START_FUNCTION, "deleteByIdAudience");
		try {
			if (audienceRepository.existsById(id)) {
				audienceRepository.deleteById(id);
				LOGGER.info(Messages.DELETE_SUCCESSFULLY, "Audience");
				return true; // Return true if deletion is successful
			} else {
				LOGGER.warn(Messages.ENTITY_IS_NOT_EXIST, "Audience");
				return false; // Return false if audience with given ID does not exist
			}
		} catch (Exception e) {
			LOGGER.error("Error deleting audience", e);
			throw e;
		}
	}
}
