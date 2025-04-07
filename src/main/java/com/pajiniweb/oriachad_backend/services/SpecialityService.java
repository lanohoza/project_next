package com.pajiniweb.oriachad_backend.services;

import com.pajiniweb.oriachad_backend.domains.dtos.SpecialityDto;
import com.pajiniweb.oriachad_backend.domains.entities.Establishment;
import com.pajiniweb.oriachad_backend.domains.entities.Speciality;
import com.pajiniweb.oriachad_backend.domains.enums.TypeEstablishment;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.repositories.SpecialityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpecialityService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpecialityService.class);
    @Autowired
    SpecialityRepository specialityRepository;

    @Autowired

    HelperService helperService;
    @Autowired

    SpecialityRepository SpecialityRepository;

    public List<SpecialityDto> getAllByEstabTypeAndNumber(TypeEstablishment type, List<Integer> numbers) {
        LOGGER.info(Messages.START_FUNCTION, "findAll Speciality");
        try {
            return specialityRepository.getAllByEstabTypeAndNumbers(type, numbers).stream().map(speciality -> SpecialityDto.builder().id(speciality.getId()).title(speciality.getTitle()).build()).toList();

        } catch (Exception e) {
            LOGGER.error(Messages.ENTITY_IS_NOT_FOUND, "Speciality", e.getMessage());
            throw new RuntimeException(Messages.PROCESS_IS_NOT_DONE);
        }
    }
    public List<SpecialityDto> findAll() {
        LOGGER.info(Messages.START_FUNCTION, "findAll Speciality");
        try {
            return SpecialityRepository.findAll().stream().map(speciality -> SpecialityDto.builder()
                    .id(speciality.getId())
                    .title(speciality.getTitle())
                    .build()).toList();
        } catch (Exception e) {
            LOGGER.error(Messages.ENTITY_IS_NOT_FOUND, "Speciality", e.getMessage());
            throw new RuntimeException(Messages.PROCESS_IS_NOT_DONE);
        }
    }
    public List<SpecialityDto> getAllByIdLevel(Long idLevel) {
        LOGGER.info(Messages.START_FUNCTION, "findAll Speciality");
        try {
            return SpecialityRepository.getAllByIdLevel(idLevel).stream().map(speciality -> SpecialityDto.builder()
                    .id(speciality.getId())
                    .title(speciality.getTitle())
                    .build()).toList();

        } catch (Exception e) {
            LOGGER.error(Messages.ENTITY_IS_NOT_FOUND, "Speciality", e.getMessage());
            throw new RuntimeException(Messages.PROCESS_IS_NOT_DONE);
        }
    }
    public Optional<SpecialityDto> findById(Long id) {
        LOGGER.info(Messages.START_FUNCTION, "findByIdSpeciality");
        try {
            return SpecialityRepository.findById(id).map(speciality -> SpecialityDto.builder().id(speciality.getId()).title(speciality.getTitle()).build());
        } catch (Exception e) {
            LOGGER.error("Error fetching Speciality by id", e);
            throw e;
        }
    }

    public Speciality save(Speciality Speciality) {
        LOGGER.info(Messages.START_FUNCTION, "save Speciality");
        try {
            return SpecialityRepository.save(Speciality);
        } catch (Exception e) {
            LOGGER.error("Error saving Speciality", e);
            throw e;
        }
    }

    public Speciality update(Long id, Speciality Speciality) {
        LOGGER.info(Messages.START_FUNCTION, "update Speciality");
        try {
            if (!SpecialityRepository.existsById(id)) {
                LOGGER.error(Messages.ENTITY_IS_NOT_EXIST, "Speciality");
                throw new IllegalArgumentException(Messages.ENTITY_IS_NOT_EXIST.replace("{}", "Speciality"));
            }
        } catch (Exception e) {
            LOGGER.error("Error updating Speciality", e);
            throw e;
        }
        return Speciality;
    }

    public boolean deleteById(Long id) {
        LOGGER.info(Messages.START_FUNCTION, "deleteByIdSpeciality");
        try {
            if (SpecialityRepository.existsById(id)) {
                SpecialityRepository.deleteById(id);
                LOGGER.info(Messages.DELETE_SUCCESSFULLY, "Speciality");
                return true; // Return true if deletion is successful
            } else {
                LOGGER.warn(Messages.ENTITY_IS_NOT_EXIST, "Speciality");
                return false; // Return false if Speciality with given ID does not exist
            }
        } catch (Exception e) {
            LOGGER.error("Error deleting Speciality", e);
            throw e;
        }
    }

    public List<SpecialityDto> getSpecialtyForDesire() {
        Establishment establishment = helperService.getCurrentEstablishment();
        return specialityRepository.getAllByEstabTypeAndNumbers(TypeEstablishment.secondary, List.of(establishment.getType() == TypeEstablishment.middle ? 1 : 2)).stream().map(speciality -> SpecialityDto.builder().id(speciality.getId()).title(speciality.getTitle()).build()).toList();

    }
}
