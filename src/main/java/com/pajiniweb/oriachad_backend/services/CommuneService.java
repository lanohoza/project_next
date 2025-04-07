package com.pajiniweb.oriachad_backend.services;

import com.pajiniweb.oriachad_backend.domains.dtos.CommuneDto;
import com.pajiniweb.oriachad_backend.domains.entities.Commune;
import com.pajiniweb.oriachad_backend.domains.entities.Wilaya;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.repositories.CommuneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommuneService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommuneService.class);

    @Autowired
    private CommuneRepository communeRepository;


    public List<CommuneDto> findAll() {
        LOGGER.info(Messages.START_FUNCTION, "findAll Commune");
        try {
            List<Commune> communes = communeRepository.findAll();
            return communes.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LOGGER.error(Messages.ENTITY_IS_NOT_FOUND, "Commune", e.getMessage());
            throw new RuntimeException(Messages.PROCESS_IS_NOT_DONE);
        }
    }

    public CommuneDto findById(Long id) {
        LOGGER.info(Messages.START_FUNCTION, "findById Commune");
        try {
            Optional<Commune> communeOptional = communeRepository.findById(id);
            Commune commune = communeOptional.orElseThrow(() -> new RuntimeException("Commune not found with id: " + id));
            return convertToDTO(commune);
        } catch (Exception e) {
            LOGGER.error("Error fetching commune by id", e);
            throw e;
        }
    }

    public List<CommuneDto> findByIdWilaya(Long idWilaya) {
        LOGGER.info(Messages.START_FUNCTION, "findByIdDaira Commune");
        try {
            List<Commune> communes = communeRepository.findByIdWilaya(idWilaya);
            return communes.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LOGGER.error("Error fetching communes by idDaira", e);
            throw e;
        }
    }

    public CommuneDto save(CommuneDto communeDTO) {
        LOGGER.info(Messages.START_FUNCTION, "save Commune");
        try {
            Commune commune = convertToEntity(communeDTO);
            Commune savedCommune = communeRepository.save(commune);
            return convertToDTO(savedCommune);
        } catch (Exception e) {
            LOGGER.error("Error saving commune", e);
            throw e;
        }
    }

    public CommuneDto update(Long id, CommuneDto communeDTO) {
        LOGGER.info(Messages.START_FUNCTION, "update Commune");
        try {
            if (!communeRepository.existsById(id)) {
                LOGGER.error(Messages.ENTITY_IS_NOT_EXIST, "Commune");
                throw new IllegalArgumentException(Messages.ENTITY_IS_NOT_EXIST.replace("{}", "Commune"));
            }
            Commune communeToUpdate = convertToEntity(communeDTO);
            communeToUpdate.setId(id);
            Commune updatedCommune = communeRepository.save(communeToUpdate);
            return convertToDTO(updatedCommune);
        } catch (Exception e) {
            LOGGER.error("Error updating commune", e);
            throw e;
        }
    }

    public boolean deleteById(Long id) {
        LOGGER.info(Messages.START_FUNCTION, "deleteById Commune");
        try {
            if (communeRepository.existsById(id)) {
                communeRepository.deleteById(id);
                LOGGER.info(Messages.DELETE_SUCCESSFULLY, "Commune");
                return true;
            } else {
                LOGGER.warn(Messages.ENTITY_IS_NOT_EXIST, "Commune");
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("Error deleting commune", e);
            throw e;
        }
    }

    private CommuneDto convertToDTO(Commune commune) {
        return CommuneDto.builder()
                .id(commune.getId())
                .name(commune.getName())
                .idWilaya(commune.getIdWilaya())
                .build();
    }

    public Commune convertToEntity(CommuneDto communeDTO) {

        return Commune.builder()
                .id(communeDTO.getId())
                .name(communeDTO.getName())
                .wilaya(Wilaya.builder().id(communeDTO.getIdWilaya()).build())
                .build();
    }
}
