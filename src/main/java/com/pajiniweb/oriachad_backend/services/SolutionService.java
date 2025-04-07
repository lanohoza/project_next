package com.pajiniweb.oriachad_backend.services;


import com.pajiniweb.oriachad_backend.domains.dtos.SolutionDto;
import com.pajiniweb.oriachad_backend.domains.entities.Solution;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.repositories.SolutionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SolutionService {

    private static final Logger log = LoggerFactory.getLogger(DifficultyService.class);

    @Autowired
    SolutionRepository solutionRepository;

    public SolutionDto createSolution(SolutionDto solutionDTO) {
        log.info(Messages.START_FUNCTION, "createSolution");
        try {
            Solution solution = new Solution();
            solution.setTitle(solutionDTO.getTitle());
            Solution savedSolution = solutionRepository.save(solution);
            log.info(Messages.PROCESS_SUCCESSFULLY, "createSolution");
            return convertToDTO(savedSolution);
        } catch (Exception e) {
            log.error(Messages.PROCESS_FAILED, "createSolution", e.getMessage());
            throw e;
        }
    }

    public SolutionDto updateSolution(Long id, SolutionDto solutionDTO) {
        log.info(Messages.START_FUNCTION, "updateSolution");
        try {
            Optional<Solution> optionalSolution = solutionRepository.findById(id);
            if (optionalSolution.isPresent()) {
                Solution existingSolution = optionalSolution.get();
                existingSolution.setTitle(solutionDTO.getTitle());
                Solution updatedSolution = solutionRepository.save(existingSolution);
                log.info(Messages.PROCESS_SUCCESSFULLY, "updateSolution");
                return convertToDTO(updatedSolution);
            } else {
                log.warn(Messages.ENTITY_IS_NOT_FOUND, "Solution");
                return null;
            }
        } catch (Exception e) {
            log.error(Messages.PROCESS_FAILED, "updateSolution", e.getMessage());
            throw e;
        }
    }

    public SolutionDto getSolution(Long id) {
        log.info(Messages.START_FUNCTION, "getSolution");
        try {
            Optional<Solution> optionalSolution = solutionRepository.findById(id);
            if (optionalSolution.isPresent()) {
                log.info(Messages.ENTITY_IS_FOUND, "Solution");
                return convertToDTO(optionalSolution.get());
            } else {
                log.warn(Messages.ENTITY_IS_NOT_FOUND, "Solution");
                return null;
            }
        } catch (Exception e) {
            log.error(Messages.PROCESS_FAILED, "getSolution", e.getMessage());
            throw e;
        }
    }

    public List<SolutionDto> getAllSolutions() {
        log.info(Messages.START_FUNCTION, "getAllSolutions");
        try {
            List<Solution> solutions = solutionRepository.findAll();
            if (solutions.isEmpty()) {
                log.warn(Messages.LIST_IS_EMPTY, "Solutions");
            }
            log.info(Messages.PROCESS_SUCCESSFULLY, "getAllSolutions");
            return solutions.stream().map(this::convertToDTO).collect(Collectors.toList());
        } catch (Exception e) {
            log.error(Messages.PROCESS_FAILED, "getAllSolutions", e.getMessage());
            throw e;
        }
    }

    public boolean deleteSolution(Long id) {
        log.info(Messages.START_FUNCTION, "deleteSolution");
        try {
            Optional<Solution> optionalSolution = solutionRepository.findById(id);
            if (optionalSolution.isPresent()) {
                solutionRepository.deleteById(id);
                log.info(Messages.DELETE_SUCCESSFULLY, "Solution");
                return true;
            } else {
                log.warn(Messages.ENTITY_IS_NOT_FOUND, "Solution");
                return false;
            }
        } catch (Exception e) {
            log.error(Messages.DELETE_FAILED, "Solution", e.getMessage());
            throw e;
        }
    }

    private SolutionDto convertToDTO(Solution solution) {
        SolutionDto solutionDTO = new SolutionDto();
        solutionDTO.setId(solution.getId());
        solutionDTO.setTitle(solution.getTitle());
        return solutionDTO;
    }
}
