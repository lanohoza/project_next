package com.pajiniweb.oriachad_backend.services;

import com.pajiniweb.oriachad_backend.domains.dtos.DifficultySolutionDto;
import com.pajiniweb.oriachad_backend.domains.dtos.UserDto;
import com.pajiniweb.oriachad_backend.domains.entities.DifficultySolution;
import com.pajiniweb.oriachad_backend.domains.entities.OriachadUser;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.repositories.DifficultySolutionRepository;
import com.pajiniweb.oriachad_backend.security.domain.entities.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DifficultySolutionService {

    private static final Logger log = LoggerFactory.getLogger(DifficultySolutionService.class);

    @Autowired
    private DifficultySolutionRepository difficultySolutionRepository;

    @Autowired
    UserService userService;

    public DifficultySolutionDto createDifficultySolution(DifficultySolutionDto difficultySolutionDTO) {
        log.info(Messages.START_FUNCTION, "createDifficultySolution");
        try {
            DifficultySolution difficultySolution = new DifficultySolution();
            difficultySolution.setIdDifficulty(difficultySolutionDTO.getDifficultyId());
            difficultySolution.setIdSolution(difficultySolutionDTO.getSolutionId());

            UserDto createdBy = userService.getUserById(difficultySolutionDTO.getCreatedBy());
            OriachadUser user = userService.mapUserDtoToEntity(createdBy);
            difficultySolution.setCreatedBy(user);

            DifficultySolution savedDifficultySolution = difficultySolutionRepository.save(difficultySolution);
            log.info(Messages.PROCESS_SUCCESSFULLY, "createDifficultySolution");
            return convertToDTO(savedDifficultySolution);
        } catch (Exception e) {
            log.error(Messages.PROCESS_FAILED, "createDifficultySolution", e.getMessage());
            throw e;
        }
    }

    public DifficultySolutionDto updateDifficultySolution(Long id, DifficultySolutionDto difficultySolutionDTO) {
        log.info(Messages.START_FUNCTION, "updateDifficultySolution");
        try {
            Optional<DifficultySolution> optionalDifficultySolution = difficultySolutionRepository.findById(id);
            if (optionalDifficultySolution.isPresent()) {
                DifficultySolution existingDifficultySolution = optionalDifficultySolution.get();
                existingDifficultySolution.setIdDifficulty(difficultySolutionDTO.getDifficultyId());
                existingDifficultySolution.setIdSolution(difficultySolutionDTO.getSolutionId());

                UserDto createdBy = userService.getUserById(difficultySolutionDTO.getCreatedBy());
                OriachadUser user = userService.mapUserDtoToEntity(createdBy);
                existingDifficultySolution.setCreatedBy(user);

                DifficultySolution updatedDifficultySolution = difficultySolutionRepository.save(existingDifficultySolution);
                log.info(Messages.PROCESS_SUCCESSFULLY, "updateDifficultySolution");
                return convertToDTO(updatedDifficultySolution);
            } else {
                log.warn(Messages.ENTITY_IS_NOT_FOUND, "DifficultySolution");
                return null;
            }
        } catch (Exception e) {
            log.error(Messages.PROCESS_FAILED, "updateDifficultySolution", e.getMessage());
            throw e;
        }
    }

    public DifficultySolutionDto getDifficultySolution(Long id) {
        log.info(Messages.START_FUNCTION, "getDifficultySolution");
        try {
            Optional<DifficultySolution> optionalDifficultySolution = difficultySolutionRepository.findById(id);
            if (optionalDifficultySolution.isPresent()) {
                log.info(Messages.ENTITY_IS_FOUND, "DifficultySolution");
                return convertToDTO(optionalDifficultySolution.get());
            } else {
                log.warn(Messages.ENTITY_IS_NOT_FOUND, "DifficultySolution");
                return null;
            }
        } catch (Exception e) {
            log.error(Messages.PROCESS_FAILED, "getDifficultySolution", e.getMessage());
            throw e;
        }
    }

    public List<DifficultySolutionDto> getAllDifficultySolutions() {
        log.info(Messages.START_FUNCTION, "getAllDifficultySolutions");
        try {
            List<DifficultySolution> difficultySolutions = difficultySolutionRepository.findAll();
            if (difficultySolutions.isEmpty()) {
                log.warn(Messages.LIST_IS_EMPTY, "DifficultySolutions");
            }
            log.info(Messages.PROCESS_SUCCESSFULLY, "getAllDifficultySolutions");
            return difficultySolutions.stream().map(this::convertToDTO).collect(Collectors.toList());
        } catch (Exception e) {
            log.error(Messages.PROCESS_FAILED, "getAllDifficultySolutions", e.getMessage());
            throw e;
        }
    }

    public boolean deleteDifficultySolution(Long id) {
        log.info(Messages.START_FUNCTION, "deleteDifficultySolution");
        try {
            Optional<DifficultySolution> optionalDifficultySolution = difficultySolutionRepository.findById(id);
            if (optionalDifficultySolution.isPresent()) {
                difficultySolutionRepository.deleteById(id);
                log.info(Messages.DELETE_SUCCESSFULLY, "DifficultySolution");
                return true;
            } else {
                log.warn(Messages.ENTITY_IS_NOT_FOUND, "DifficultySolution");
                return false;
            }
        } catch (Exception e) {
            log.error(Messages.DELETE_FAILED, "DifficultySolution", e.getMessage());
            throw e;
        }
    }

    public List<DifficultySolutionDto> saveAll(List<DifficultySolutionDto> difficultySolutionDTOs) {
        log.info(Messages.START_FUNCTION, "saveAllDifficultySolutions");
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails userDetails = ((CustomUserDetails) auth.getPrincipal());
            OriachadUser user = userDetails.getOriachadUser();

            List<DifficultySolution> difficultySolutions = difficultySolutionDTOs.stream()
                    .map(dto -> {
                        DifficultySolution difficultySolution = new DifficultySolution();
                        difficultySolution.setIdDifficulty(dto.getDifficultyId());
                        difficultySolution.setIdSolution(dto.getSolutionId());
                        difficultySolution.setCreatedBy(user);
                        difficultySolution.setIdCreatedBy(userDetails.getIdUser());
                        return difficultySolution;
                    })
                    .collect(Collectors.toList());

            List<DifficultySolution> savedDifficultySolutions = difficultySolutionRepository.saveAll(difficultySolutions);
            log.info(Messages.PROCESS_SUCCESSFULLY, "saveAllDifficultySolutions");
            return savedDifficultySolutions.stream().map(this::convertToDTO).collect(Collectors.toList());
        } catch (Exception e) {
            log.error(Messages.PROCESS_FAILED, "saveAllDifficultySolutions", e.getMessage());
            throw e;
        }
    }

    public List<DifficultySolutionDto> findByDifficultyId(Long difficultyId) {
        log.info(Messages.START_FUNCTION, "findByDifficultyId");
        try {
            List<DifficultySolution> difficultySolutions = difficultySolutionRepository.findByDifficultyId(difficultyId);
            if (difficultySolutions.isEmpty()) {
                log.warn(Messages.LIST_IS_EMPTY, "DifficultySolutions for difficultyId: " + difficultyId);
            }
            log.info(Messages.PROCESS_SUCCESSFULLY, "findByDifficultyId");
            return difficultySolutions.stream().map(this::convertToDTO).collect(Collectors.toList());
        } catch (Exception e) {
            log.error(Messages.PROCESS_FAILED, "findByDifficultyId", e.getMessage());
            throw e;
        }
    }

    public List<DifficultySolutionDto> findByDifficultyIdAndToken(Long difficultyId) {
        log.info(Messages.START_FUNCTION, "findByDifficultyIdAndUserId");
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails userDetails = ((CustomUserDetails) auth.getPrincipal());
            List<DifficultySolution> difficultySolutions = difficultySolutionRepository.findByDifficultyIdAndUserId(difficultyId, userDetails.getIdUser());
            if (difficultySolutions.isEmpty()) {
                log.warn(Messages.LIST_IS_EMPTY, "DifficultySolutions for difficultyId: " + difficultyId + " and userId: " + userDetails.getIdUser());
            }
            log.info(Messages.PROCESS_SUCCESSFULLY, "findByDifficultyIdAndUserId");
            return difficultySolutions.stream().map(this::convertToDTO).collect(Collectors.toList());
        } catch (Exception e) {
            log.error(Messages.PROCESS_FAILED, "findByDifficultyIdAndUserId", e.getMessage());
            throw e;
        }
    }

    private DifficultySolutionDto convertToDTO(DifficultySolution difficultySolution) {
        DifficultySolutionDto difficultySolutionDTO = new DifficultySolutionDto();
        difficultySolutionDTO.setId(difficultySolution.getId());
        difficultySolutionDTO.setDifficultyId(difficultySolution.getIdDifficulty());
        difficultySolutionDTO.setSolutionId(difficultySolution.getIdSolution());
        difficultySolutionDTO.setCreatedBy(difficultySolution.getIdCreatedBy());
        return difficultySolutionDTO;
    }
}
