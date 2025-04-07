package com.pajiniweb.oriachad_backend.actions.services;

import com.pajiniweb.oriachad_backend.actions.domains.dtos.ShedCategoryDto;
import com.pajiniweb.oriachad_backend.actions.domains.entities.ShedCategory;
import com.pajiniweb.oriachad_backend.actions.repositories.ShedCategoryRepository;
import com.pajiniweb.oriachad_backend.helps.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShedCategoryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShedCategoryService.class);

    @Autowired
    private ShedCategoryRepository shedCategoryRepository;

    public List<ShedCategoryDto> findAll() {
        LOGGER.info(Messages.START_FUNCTION, "findAll ShedCategory");
        return shedCategoryRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Optional<ShedCategoryDto> findById(Long id) {
        LOGGER.info(Messages.START_FUNCTION, "findById ShedCategory");
        return shedCategoryRepository.findById(id)
                .map(this::toDto);
    }

    public ShedCategoryDto save(ShedCategoryDto dto) {
        LOGGER.info(Messages.START_FUNCTION, "save ShedCategory");
        try {
            ShedCategory shedCategory = toEntity(dto);
            shedCategory = shedCategoryRepository.save(shedCategory);
            return toDto(shedCategory);
        } catch (Exception e) {
            LOGGER.error("Error saving ShedCategory", e);
            throw e;
        }
    }

    public ShedCategoryDto update(Long id, ShedCategoryDto dto) {
        LOGGER.info(Messages.START_FUNCTION, "update ShedCategory");
        if (!shedCategoryRepository.existsById(id)) {
            LOGGER.error(Messages.ENTITY_IS_NOT_EXIST, "ShedCategory");
            throw new IllegalArgumentException(Messages.ENTITY_IS_NOT_EXIST.replace("{}", "ShedCategory"));
        }
        try {
            ShedCategory shedCategory = toEntity(dto);
            shedCategory.setId(id);
            shedCategory = shedCategoryRepository.save(shedCategory);
            return toDto(shedCategory);
        } catch (Exception e) {
            LOGGER.error("Error updating ShedCategory", e);
            throw e;
        }
    }

    public boolean deleteById(Long id) {
        LOGGER.info(Messages.START_FUNCTION, "deleteById ShedCategory");
        try {
            if (shedCategoryRepository.existsById(id)) {
                shedCategoryRepository.deleteById(id);
                LOGGER.info(Messages.DELETE_SUCCESSFULLY, "ShedCategory");
                return true;
            } else {
                LOGGER.warn(Messages.ENTITY_IS_NOT_EXIST, "ShedCategory");
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("Error deleting ShedCategory", e);
            throw e;
        }
    }

    // Mapping: Entity to DTO
    public ShedCategoryDto toDto(ShedCategory shedCategory) {
        return ShedCategoryDto.builder()
                .id(shedCategory.getId())
                .name(shedCategory.getName())
                .code(shedCategory.getCode())
                .build();
    }

    // Mapping: DTO to Entity
    public ShedCategory toEntity(ShedCategoryDto dto) {
        return ShedCategory.builder()
                .id(dto.getId())
                .name(dto.getName())
                .code(dto.getCode())
                .build();
    }
}
