package com.pajiniweb.oriachad_backend.actions.services;

import com.pajiniweb.oriachad_backend.actions.domains.dtos.DirectionsShedDto;
import com.pajiniweb.oriachad_backend.actions.domains.entities.DirectionsShed;
import com.pajiniweb.oriachad_backend.actions.repositories.DirectionsShedRepository;
import com.pajiniweb.oriachad_backend.helps.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DirectionsShedService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DirectionsShedService.class);

    @Autowired
    private DirectionsShedRepository directionsShedRepository;

    // -------------------- CRUD Methods --------------------

    public List<DirectionsShedDto> findAll() {
        LOGGER.info(Messages.START_FUNCTION, "findAll DirectionsShed");
        return directionsShedRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Optional<DirectionsShedDto> findById(Long id) {
        LOGGER.info(Messages.START_FUNCTION, "findById DirectionsShed");
        return directionsShedRepository.findById(id)
                .map(this::toDto);
    }

    public DirectionsShedDto save(DirectionsShedDto dto) {
        LOGGER.info(Messages.START_FUNCTION, "save DirectionsShed");
        try {
            DirectionsShed directionsShed = toEntity(dto);
            directionsShed = directionsShedRepository.save(directionsShed);
            return toDto(directionsShed);
        } catch (Exception e) {
            LOGGER.error("Error saving DirectionsShed", e);
            throw e;
        }
    }

    public DirectionsShedDto update(Long id, DirectionsShedDto dto) {
        LOGGER.info(Messages.START_FUNCTION, "update DirectionsShed");
        if (!directionsShedRepository.existsById(id)) {
            LOGGER.error(Messages.ENTITY_IS_NOT_EXIST, "DirectionsShed");
            throw new IllegalArgumentException(Messages.ENTITY_IS_NOT_EXIST.replace("{}", "DirectionsShed"));
        }
        try {
            DirectionsShed directionsShed = toEntity(dto);
            directionsShed.setId(id);
            directionsShed = directionsShedRepository.save(directionsShed);
            return toDto(directionsShed);
        } catch (Exception e) {
            LOGGER.error("Error updating DirectionsShed", e);
            throw e;
        }
    }

    public boolean deleteById(Long id) {
        LOGGER.info(Messages.START_FUNCTION, "deleteById DirectionsShed");
        try {
            if (directionsShedRepository.existsById(id)) {
                directionsShedRepository.deleteById(id);
                LOGGER.info(Messages.DELETE_SUCCESSFULLY, "DirectionsShed");
                return true;
            } else {
                LOGGER.warn(Messages.ENTITY_IS_NOT_EXIST, "DirectionsShed");
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("Error deleting DirectionsShed", e);
            throw e;
        }
    }

    // -------------------- Mapping Methods --------------------

    public DirectionsShedDto toDto(DirectionsShed directionsShed) {
        return DirectionsShedDto.builder()
                .id(directionsShed.getId())
                .name(directionsShed.getName())
                .build();
    }

    public DirectionsShed toEntity(DirectionsShedDto dto) {
        return DirectionsShed.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }
}
