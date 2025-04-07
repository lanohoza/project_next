package com.pajiniweb.oriachad_backend.services;

import com.pajiniweb.oriachad_backend.domains.dtos.DifficultyDto;
import com.pajiniweb.oriachad_backend.domains.entities.Difficulty;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.repositories.DifficultyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class DifficultyService {

    private static final Logger log = LoggerFactory.getLogger(DifficultyService.class);

    @Autowired
    DifficultyRepository difficultyRepository;

    public DifficultyDto createDifficulty(DifficultyDto difficultyDTO) {
        log.info(Messages.START_FUNCTION, "createDifficulty");
        try {
            Difficulty difficulty = new Difficulty();
            difficulty.setTitle(difficultyDTO.getTitle());
            Difficulty savedDifficulty = difficultyRepository.save(difficulty);
            log.info(Messages.PROCESS_SUCCESSFULLY, "createDifficulty");
            return convertToDTO(savedDifficulty);
        } catch (Exception e) {
            log.error(Messages.PROCESS_FAILED, "createDifficulty", e.getMessage());
            throw e;
        }
    }

    public DifficultyDto updateDifficulty(Long id, DifficultyDto difficultyDTO) {
        log.info(Messages.START_FUNCTION, "updateDifficulty");
        try {
            Optional<Difficulty> optionalDifficulty = difficultyRepository.findById(id);
            if (optionalDifficulty.isPresent()) {
                Difficulty existingDifficulty = optionalDifficulty.get();
                existingDifficulty.setTitle(difficultyDTO.getTitle());
                Difficulty updatedDifficulty = difficultyRepository.save(existingDifficulty);
                log.info(Messages.PROCESS_SUCCESSFULLY, "updateDifficulty");
                return convertToDTO(updatedDifficulty);
            } else {
                log.warn(Messages.ENTITY_IS_NOT_FOUND, "Difficulty");
                return null;
            }
        } catch (Exception e) {
            log.error(Messages.PROCESS_FAILED, "updateDifficulty", e.getMessage());
            throw e;
        }
    }

    public DifficultyDto getDifficulty(Long id) {
        log.info(Messages.START_FUNCTION, "getDifficulty");
        try {
            Optional<Difficulty> optionalDifficulty = difficultyRepository.findById(id);
            if (optionalDifficulty.isPresent()) {
                log.info(Messages.ENTITY_IS_FOUND, "Difficulty");
                return convertToDTO(optionalDifficulty.get());
            } else {
                log.warn(Messages.ENTITY_IS_NOT_FOUND, "Difficulty");
                return null;
            }
        } catch (Exception e) {
            log.error(Messages.PROCESS_FAILED, "getDifficulty", e.getMessage());
            throw e;
        }
    }

    public List<DifficultyDto> getAllDifficulties() {
        log.info(Messages.START_FUNCTION, "getAllDifficulties");
        try {
            List<Difficulty> difficulties = difficultyRepository.findAll();
            if (difficulties.isEmpty()) {
                log.warn(Messages.LIST_IS_EMPTY, "Difficulties");
            }
            log.info(Messages.PROCESS_SUCCESSFULLY, "getAllDifficulties");
            return difficulties.stream().map(this::convertToDTO).collect(Collectors.toList());
        } catch (Exception e) {
            log.error(Messages.PROCESS_FAILED, "getAllDifficulties", e.getMessage());
            throw e;
        }
    }

    public boolean deleteDifficulty(Long id) {
        log.info(Messages.START_FUNCTION, "deleteDifficulty");
        try {
            Optional<Difficulty> optionalDifficulty = difficultyRepository.findById(id);
            if (optionalDifficulty.isPresent()) {
                difficultyRepository.deleteById(id);
                log.info(Messages.DELETE_SUCCESSFULLY, "Difficulty");
                return true;
            } else {
                log.warn(Messages.ENTITY_IS_NOT_FOUND, "Difficulty");
                return false;
            }
        } catch (Exception e) {
            log.error(Messages.DELETE_FAILED, "Difficulty", e.getMessage());
            throw e;
        }
    }

    public DifficultyDto convertToDTO(Difficulty difficulty) {
        DifficultyDto difficultyDTO = new DifficultyDto();
        difficultyDTO.setId(difficulty.getId());
        difficultyDTO.setTitle(difficulty.getTitle());
        return difficultyDTO;
    }

    public Difficulty convertToObject(DifficultyDto difficultyDto) {
        Difficulty difficulty = new Difficulty();
        difficulty.setId(difficultyDto.getId());
        difficulty.setTitle(difficultyDto.getTitle());
        return difficulty;
    }
}
