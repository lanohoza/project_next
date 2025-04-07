package com.pajiniweb.oriachad_backend.actions.services.TCE002;

import com.pajiniweb.oriachad_backend.actions.domains.dtos.TCE002.TCE002ConditionToDisplayDto;
import com.pajiniweb.oriachad_backend.actions.domains.dtos.TCE002ConditionDto;
import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCE002.TCE002Condition;
import com.pajiniweb.oriachad_backend.actions.repositories.scripts.TCE002.TCE002ConditionRepository;
import com.pajiniweb.oriachad_backend.domains.entities.Level;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.actions.domains.entities.ShedSetting;
import com.pajiniweb.oriachad_backend.domains.entities.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TCE002ConditionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TCE002ConditionService.class);

    @Autowired
    private TCE002ConditionRepository tce002ConditionRepository;

    // --- CRUD Methods ---

    public List<TCE002ConditionDto> findAll() {
        LOGGER.info(Messages.START_FUNCTION, "findAll TCE002Condition");
        return tce002ConditionRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Optional<TCE002ConditionToDisplayDto> findById(Long id) {
        LOGGER.info(Messages.START_FUNCTION, "findById TCE002Condition");
        return tce002ConditionRepository.findById(id)
                .map(this::toDisplayDto);
    }

    public TCE002ConditionDto save(TCE002ConditionDto dto) {
        LOGGER.info(Messages.START_FUNCTION, "save TCE002Condition");
        try {
            TCE002Condition entity = toEntity(dto);
            entity = tce002ConditionRepository.save(entity);
            return toDto(entity);
        } catch (Exception e) {
            LOGGER.error("Error saving TCE002Condition", e);
            throw e;
        }
    }

    public TCE002ConditionDto update(Long id, TCE002ConditionDto dto) {
        LOGGER.info(Messages.START_FUNCTION, "update TCE002Condition");
        if (!tce002ConditionRepository.existsById(id)) {
            LOGGER.error(Messages.ENTITY_IS_NOT_EXIST, "TCE002Condition");
            throw new IllegalArgumentException(Messages.ENTITY_IS_NOT_EXIST.replace("{}", "TCE002Condition"));
        }
        try {
            TCE002Condition entity = toEntity(dto);
            entity.setId(id);
            entity = tce002ConditionRepository.save(entity);
            return toDto(entity);
        } catch (Exception e) {
            LOGGER.error("Error updating TCE002Condition", e);
            throw e;
        }
    }

    public boolean deleteById(Long id) {
        LOGGER.info(Messages.START_FUNCTION, "deleteById TCE002Condition");
        try {
            if (tce002ConditionRepository.existsById(id)) {
                tce002ConditionRepository.deleteById(id);
                LOGGER.info(Messages.DELETE_SUCCESSFULLY, "TCE002Condition");
                return true;
            } else {
                LOGGER.warn(Messages.ENTITY_IS_NOT_EXIST, "TCE002Condition");
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("Error deleting TCE002Condition", e);
            throw e;
        }
    }

    public Page<TCE002ConditionToDisplayDto> search(String search, Pageable pageable) {
        LOGGER.info(Messages.START_FUNCTION, "search TCE002Condition by syndromeDiagnostic");
        try {
            return tce002ConditionRepository.search(search, pageable)
                    .map(this::toDisplayDto);
        } catch (Exception e) {
            LOGGER.error("Error fetching paginated TCE002ConditionToDisplayDto by search query", e);
            throw e;
        }
    }


    // --- Mapping Methods ---

    public TCE002ConditionDto toDto(TCE002Condition entity) {
        return TCE002ConditionDto.builder()
                .id(entity.getId())
                .average(entity.getAverage())
                .averageMax(entity.getAverageMax())
                .idLevel(entity.getIdLevel())
                .operate(entity.getOperate())
                .idShedSetting(entity.getShedSetting() != null ? entity.getShedSetting().getId() : null)
                .target(entity.getTarget())
                .subjectIds(entity.getSubjects() != null
                        ? entity.getSubjects().stream()
                        .map(Subject::getId)
                        .collect(Collectors.toList())
                        : null)
                .build();
    }

    public TCE002Condition toEntity(TCE002ConditionDto dto) {
        TCE002Condition.TCE002ConditionBuilder builder = TCE002Condition.builder();
        builder.id(dto.getId())
                .average(dto.getAverage())
                .averageMax(dto.getAverageMax())
                .operate(dto.getOperate())
                .Level(Level.builder().id(dto.getIdLevel()).build())
                .target(dto.getTarget());
        if (dto.getIdShedSetting() != null) {
            ShedSetting shedSetting = new ShedSetting();
            shedSetting.setId(dto.getIdShedSetting());
            builder.shedSetting(shedSetting);
        }
        if (dto.getSubjectIds() != null) {
            builder.subjects(dto.getSubjectIds().stream()
                    .map(id -> {
                        Subject subject = new Subject();
                        subject.setId(id);
                        return subject;
                    })
                    .collect(Collectors.toList()));
        }
        return builder.build();
    }

    public TCE002ConditionToDisplayDto toDisplayDto(TCE002Condition condition) {
        return TCE002ConditionToDisplayDto.builder()
                .id(condition.getId())
                .average(condition.getAverage())
                .averageMax(condition.getAverageMax())
                .operate(condition.getOperate())
                .idLevel(condition.getIdLevel())
                .idShedSetting(condition.getIdShedSetting())
                .idShedSettingCategory(condition.getShedSetting().getIdShedCategory())
                .shedSetting(condition.getShedSetting() != null ? condition.getShedSetting().getSyndromeDiagnostic() : null)
                .target(condition.getTarget())
                .subjectIds(condition.getSubjects() != null
                        ? condition.getSubjects().stream().map(Subject::getId).collect(Collectors.toList())
                        : null)

                .subjectTitle(condition.getSubjects() != null
                        ? condition.getSubjects().stream().map(s -> s.getTitle()).collect(Collectors.toList())
                        : null)
                .build();
    }

}
