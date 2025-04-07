package com.pajiniweb.oriachad_backend.actions.services;

import com.pajiniweb.oriachad_backend.actions.domains.dtos.SupportStudentDto;
import com.pajiniweb.oriachad_backend.actions.domains.entities.SupportStudent;
import com.pajiniweb.oriachad_backend.actions.repositories.SupportStudentRepository;
import com.pajiniweb.oriachad_backend.helps.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SupportStudentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SupportStudentService.class);

    @Autowired
    private SupportStudentRepository supportStudentRepository;

    // ------------------ CRUD Methods ------------------

    public List<SupportStudentDto> findAll() {
        LOGGER.info(Messages.START_FUNCTION, "findAll SupportStudent");
        return supportStudentRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Optional<SupportStudentDto> findById(Long id) {
        LOGGER.info(Messages.START_FUNCTION, "findById SupportStudent");
        return supportStudentRepository.findById(id)
                .map(this::toDto);
    }

    public SupportStudentDto save(SupportStudentDto dto) {
        LOGGER.info(Messages.START_FUNCTION, "save SupportStudent");
        try {
            SupportStudent entity = toEntity(dto);
            entity = supportStudentRepository.save(entity);
            return toDto(entity);
        } catch (Exception e) {
            LOGGER.error("Error saving SupportStudent", e);
            throw e;
        }
    }

    public SupportStudentDto update(Long id, SupportStudentDto dto) {
        LOGGER.info(Messages.START_FUNCTION, "update SupportStudent");
        if (!supportStudentRepository.existsById(id)) {
            LOGGER.error(Messages.ENTITY_IS_NOT_EXIST, "SupportStudent");
            throw new IllegalArgumentException(Messages.ENTITY_IS_NOT_EXIST.replace("{}", "SupportStudent"));
        }
        try {
            SupportStudent entity = toEntity(dto);
            entity.setId(id);
            entity = supportStudentRepository.save(entity);
            return toDto(entity);
        } catch (Exception e) {
            LOGGER.error("Error updating SupportStudent", e);
            throw e;
        }
    }

    public boolean deleteById(Long id) {
        LOGGER.info(Messages.START_FUNCTION, "deleteById SupportStudent");
        try {
            if (supportStudentRepository.existsById(id)) {
                supportStudentRepository.deleteById(id);
                LOGGER.info(Messages.DELETE_SUCCESSFULLY, "SupportStudent");
                return true;
            } else {
                LOGGER.warn(Messages.ENTITY_IS_NOT_EXIST, "SupportStudent");
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("Error deleting SupportStudent", e);
            throw e;
        }
    }

    // ------------------ Mapping Methods ------------------

    public SupportStudentDto toDto(SupportStudent entity) {
        return SupportStudentDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .url(entity.getUrl())
                .build();
    }

    public SupportStudent toEntity(SupportStudentDto dto) {
        return SupportStudent.builder()
                .id(dto.getId())
                .name(dto.getName())
                .url(dto.getUrl())
                .build();
    }

    public List<SupportStudentDto> allByShedSetting(Long idShedSetting) {
        return supportStudentRepository.allByShedSetting(idShedSetting).stream().map(this::toDto).toList();
    }
}
