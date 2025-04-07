package com.pajiniweb.oriachad_backend.actions.services;

import com.pajiniweb.oriachad_backend.actions.domains.dtos.RequiredProceduresDto;
import com.pajiniweb.oriachad_backend.actions.domains.entities.RequiredProcedures;
import com.pajiniweb.oriachad_backend.actions.repositories.RequiredProceduresRepository;
import com.pajiniweb.oriachad_backend.helps.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RequiredProceduresService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequiredProceduresService.class);

    @Autowired
    private RequiredProceduresRepository requiredProceduresRepository;

    // ------------------ CRUD Methods ------------------

    public List<RequiredProceduresDto> findAll() {
        LOGGER.info(Messages.START_FUNCTION, "findAll RequiredProcedures");
        return requiredProceduresRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Optional<RequiredProceduresDto> findById(Long id) {
        LOGGER.info(Messages.START_FUNCTION, "findById RequiredProcedures");
        return requiredProceduresRepository.findById(id)
                .map(this::toDto);
    }

    public RequiredProceduresDto save(RequiredProceduresDto dto) {
        LOGGER.info(Messages.START_FUNCTION, "save RequiredProcedures");
        try {
            RequiredProcedures entity = toEntity(dto);
            entity = requiredProceduresRepository.save(entity);
            return toDto(entity);
        } catch (Exception e) {
            LOGGER.error("Error saving RequiredProcedures", e);
            throw e;
        }
    }

    public RequiredProceduresDto update(Long id, RequiredProceduresDto dto) {
        LOGGER.info(Messages.START_FUNCTION, "update RequiredProcedures");
        if (!requiredProceduresRepository.existsById(id)) {
            LOGGER.error(Messages.ENTITY_IS_NOT_EXIST, "RequiredProcedures");
            throw new IllegalArgumentException(Messages.ENTITY_IS_NOT_EXIST.replace("{}", "RequiredProcedures"));
        }
        try {
            RequiredProcedures entity = toEntity(dto);
            entity.setId(id);
            entity = requiredProceduresRepository.save(entity);
            return toDto(entity);
        } catch (Exception e) {
            LOGGER.error("Error updating RequiredProcedures", e);
            throw e;
        }
    }

    public boolean deleteById(Long id) {
        LOGGER.info(Messages.START_FUNCTION, "deleteById RequiredProcedures");
        try {
            if (requiredProceduresRepository.existsById(id)) {
                requiredProceduresRepository.deleteById(id);
                LOGGER.info(Messages.DELETE_SUCCESSFULLY, "RequiredProcedures");
                return true;
            } else {
                LOGGER.warn(Messages.ENTITY_IS_NOT_EXIST, "RequiredProcedures");
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("Error deleting RequiredProcedures", e);
            throw e;
        }
    }

    // ------------------ Mapping Methods ------------------

    public RequiredProceduresDto toDto(RequiredProcedures entity) {
        return RequiredProceduresDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public RequiredProcedures toEntity(RequiredProceduresDto dto) {
        return RequiredProcedures.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }
}
