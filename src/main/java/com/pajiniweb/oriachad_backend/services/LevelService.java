package com.pajiniweb.oriachad_backend.services;

import com.pajiniweb.oriachad_backend.domains.dtos.LevelDto;
import com.pajiniweb.oriachad_backend.domains.entities.Establishment;
import com.pajiniweb.oriachad_backend.domains.entities.Level;
import com.pajiniweb.oriachad_backend.domains.enums.TypeEstablishment;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.repositories.LevelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LevelService {
	private static final Logger LOGGER = LoggerFactory.getLogger(LevelService.class);

	@Autowired
	LevelRepository levelRepository;
	@Autowired
	HelperService helperService;

	public List<LevelDto> findAll() {
		LOGGER.info(Messages.START_FUNCTION, "findAll level");
		try {
			Establishment establishment = helperService.getCurrentEstablishment();
			return levelRepository.findAllByType(establishment.getType()).stream().map(level -> LevelDto.builder().type(level.getType()).number(level.getNumber()).title(level.getTitle()).id(level.getId()).build()).toList();
		} catch (Exception e) {
			LOGGER.error(Messages.ENTITY_IS_NOT_FOUND, "level", e.getMessage());
			throw new RuntimeException(Messages.PROCESS_IS_NOT_DONE);
		}
	}

	public Optional<Level> findById(Long id) {
		LOGGER.info(Messages.START_FUNCTION, "findByIdlevel");
		try {
			return levelRepository.findById(id);
		} catch (Exception e) {
			LOGGER.error("Error fetching level by id", e);
			throw e;
		}
	}

	public Level save(Level level) {
		LOGGER.info(Messages.START_FUNCTION, "save level");
		try {
			return levelRepository.save(level);
		} catch (Exception e) {
			LOGGER.error("Error saving level", e);
			throw e;
		}
	}

	public Level update(Long id, Level level) {
		LOGGER.info(Messages.START_FUNCTION, "update level");
		try {
			if (!levelRepository.existsById(id)) {
				LOGGER.error(Messages.ENTITY_IS_NOT_EXIST, "level");
				throw new IllegalArgumentException(Messages.ENTITY_IS_NOT_EXIST.replace("{}", "level"));
			}
		} catch (Exception e) {
			LOGGER.error("Error updating level", e);
			throw e;
		}
		return level;
	}

	public boolean deleteById(Long id) {
		LOGGER.info(Messages.START_FUNCTION, "deleteByIdlevel");
		try {
			if (levelRepository.existsById(id)) {
				levelRepository.deleteById(id);
				LOGGER.info(Messages.DELETE_SUCCESSFULLY, "level");
				return true; // Return true if deletion is successful
			} else {
				LOGGER.warn(Messages.ENTITY_IS_NOT_EXIST, "level");
				return false; // Return false if level with given ID does not exist
			}
		} catch (Exception e) {
			LOGGER.error("Error deleting level", e);
			throw e;
		}
	}

	public List<Level> getByTypeEstablishment(TypeEstablishment type) {
		return levelRepository.findAllByType(type);
	}
	public List<Level> getByTypeEstablishmentAndNumbers(TypeEstablishment type,int[] numbers) {
		return levelRepository.findAllByTypeAndNumberIn(type,numbers);
	}
	public List<LevelDto> findAdminAll() {
		LOGGER.info(Messages.START_FUNCTION, "findAll level");

			return levelRepository.findAll().stream()
					.map(level -> LevelDto.builder()
							.type(level.getType())
							.id(level.getId())
							.title(level.getTitle())
							.build()).toList();
	}
}
