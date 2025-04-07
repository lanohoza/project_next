package com.pajiniweb.oriachad_backend.services;

import com.pajiniweb.oriachad_backend.domains.dtos.WilayaDto;
import com.pajiniweb.oriachad_backend.domains.entities.Wilaya;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.repositories.WilayaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WilayaService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WilayaService.class);

    @Autowired
    private WilayaRepository wilayaRepository;

    public List<WilayaDto> findAll() {
        LOGGER.info(Messages.START_FUNCTION, "findAll Wilaya");
        try {
            List<Wilaya> wilayas = wilayaRepository.findAll();
            return wilayas.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LOGGER.error(Messages.ENTITY_IS_NOT_FOUND, "Wilaya", e.getMessage());
            throw new RuntimeException(Messages.PROCESS_IS_NOT_DONE);
        }
    }

    public WilayaDto findById(Long id) {
        LOGGER.info(Messages.START_FUNCTION, "findById Wilaya");
        try {
            Optional<Wilaya> wilayaOptional = wilayaRepository.findById(id);
            Wilaya wilaya = wilayaOptional.orElseThrow(() -> new RuntimeException("Wilaya not found with id: " + id));
            return convertToDTO(wilaya);
        } catch (Exception e) {
            LOGGER.error("Error fetching wilaya by id", e);
            throw e;
        }
    }

    public WilayaDto save(WilayaDto wilayaDTO) {
        LOGGER.info(Messages.START_FUNCTION, "save Wilaya");
        try {
            Wilaya wilaya = convertToEntity(wilayaDTO);
            Wilaya savedWilaya = wilayaRepository.save(wilaya);
            return convertToDTO(savedWilaya);
        } catch (Exception e) {
            LOGGER.error("Error saving wilaya", e);
            throw e;
        }
    }

    public WilayaDto update(Long id, WilayaDto wilayaDTO) {
        LOGGER.info(Messages.START_FUNCTION, "update Wilaya");
        try {
            if (!wilayaRepository.existsById(id)) {
                LOGGER.error(Messages.ENTITY_IS_NOT_EXIST, "Wilaya");
                throw new IllegalArgumentException(Messages.ENTITY_IS_NOT_EXIST.replace("{}", "Wilaya"));
            }
            Wilaya wilayaToUpdate = convertToEntity(wilayaDTO);
            wilayaToUpdate.setId(id);
            Wilaya updatedWilaya = wilayaRepository.save(wilayaToUpdate);
            return convertToDTO(updatedWilaya);
        } catch (Exception e) {
            LOGGER.error("Error updating wilaya", e);
            throw e;
        }
    }

    public boolean deleteById(Long id) {
        LOGGER.info(Messages.START_FUNCTION, "deleteById Wilaya");
        try {
            if (wilayaRepository.existsById(id)) {
                wilayaRepository.deleteById(id);
                LOGGER.info(Messages.DELETE_SUCCESSFULLY, "Wilaya");
                return true;
            } else {
                LOGGER.warn(Messages.ENTITY_IS_NOT_EXIST, "Wilaya");
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("Error deleting wilaya", e);
            throw e;
        }
    }

    private WilayaDto convertToDTO(Wilaya wilaya) {
        return WilayaDto.builder()
                .id(wilaya.getId())
                .name(wilaya.getName())
                .build();
    }

    public Wilaya convertToEntity(WilayaDto wilayaDTO) {
        return Wilaya.builder()
                .id(wilayaDTO.getId())
                .name(wilayaDTO.getName())
                .build();
    }
}
