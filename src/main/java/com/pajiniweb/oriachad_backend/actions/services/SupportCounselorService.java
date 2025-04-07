package com.pajiniweb.oriachad_backend.actions.services;

import com.pajiniweb.oriachad_backend.actions.domains.dtos.SupportCounselorDto;
import com.pajiniweb.oriachad_backend.actions.domains.entities.SupportCounselor;
import com.pajiniweb.oriachad_backend.actions.repositories.SupportCounselorRepository;
import com.pajiniweb.oriachad_backend.helps.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SupportCounselorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SupportCounselorService.class);

    @Autowired
    private SupportCounselorRepository supportCounselorRepository;

    // ------------------ CRUD Methods ------------------

    public List<SupportCounselorDto> findAll() {
        LOGGER.info(Messages.START_FUNCTION, "findAll SupportCounselor");
        return supportCounselorRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Optional<SupportCounselorDto> findById(Long id) {
        LOGGER.info(Messages.START_FUNCTION, "findById SupportCounselor");
        return supportCounselorRepository.findById(id)
                .map(this::toDto);
    }

    public SupportCounselorDto save(SupportCounselorDto dto) {
        LOGGER.info(Messages.START_FUNCTION, "save SupportCounselor");
        try {
            SupportCounselor entity = toEntity(dto);
            entity = supportCounselorRepository.save(entity);
            return toDto(entity);
        } catch (Exception e) {
            LOGGER.error("Error saving SupportCounselor", e);
            throw e;
        }
    }

    public SupportCounselorDto update(Long id, SupportCounselorDto dto) {
        LOGGER.info(Messages.START_FUNCTION, "update SupportCounselor");
        if (!supportCounselorRepository.existsById(id)) {
            LOGGER.error(Messages.ENTITY_IS_NOT_EXIST, "SupportCounselor");
            throw new IllegalArgumentException(Messages.ENTITY_IS_NOT_EXIST.replace("{}", "SupportCounselor"));
        }
        try {
            SupportCounselor entity = toEntity(dto);
            entity.setId(id);
            entity = supportCounselorRepository.save(entity);
            return toDto(entity);
        } catch (Exception e) {
            LOGGER.error("Error updating SupportCounselor", e);
            throw e;
        }
    }

    public boolean deleteById(Long id) {
        LOGGER.info(Messages.START_FUNCTION, "deleteById SupportCounselor");
        try {
            if (supportCounselorRepository.existsById(id)) {
                supportCounselorRepository.deleteById(id);
                LOGGER.info(Messages.DELETE_SUCCESSFULLY, "SupportCounselor");
                return true;
            } else {
                LOGGER.warn(Messages.ENTITY_IS_NOT_EXIST, "SupportCounselor");
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("Error deleting SupportCounselor", e);
            throw e;
        }
    }

    // ------------------ Mapping Methods ------------------

    public SupportCounselorDto toDto(SupportCounselor sc) {
        return SupportCounselorDto.builder()
                .id(sc.getId())
                .name(sc.getName())
                .url(sc.getUrl())
                .build();
    }

    public SupportCounselor toEntity(SupportCounselorDto dto) {
        return SupportCounselor.builder()
                .id(dto.getId())
                .name(dto.getName())
                .url(dto.getUrl())
                .build();
    }

    public List<SupportCounselorDto> allByShedSetting(Long idShedSetting) {
        return supportCounselorRepository.allByShedSetting(idShedSetting).stream().map(this::toDto).toList();
    }
}
