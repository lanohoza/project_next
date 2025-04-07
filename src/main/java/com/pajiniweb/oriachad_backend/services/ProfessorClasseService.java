package com.pajiniweb.oriachad_backend.services;

import com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos.AddEditProfessorClasseDto;
import com.pajiniweb.oriachad_backend.domains.entities.ProfessorClasse;
import com.pajiniweb.oriachad_backend.domains.entities.OriachadUser;
import com.pajiniweb.oriachad_backend.domains.entities.Year;
import com.pajiniweb.oriachad_backend.repositories.ProfessorClasseRepository;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.security.domain.entities.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfessorClasseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfessorClasseService.class);

    @Autowired
    private ProfessorClasseRepository professorClasseRepository;

    @Autowired
    private HelperService helperService;

    public List<AddEditProfessorClasseDto> findAll() {
        LOGGER.info(Messages.START_FUNCTION, "findAll ProfessorClasse");
        return professorClasseRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Optional<AddEditProfessorClasseDto> findById(Long id) {
        LOGGER.info(Messages.START_FUNCTION, "findById ProfessorClasse");
        try {
            return professorClasseRepository.findById(id).map(this::toDto);
        } catch (Exception e) {
            LOGGER.error("Error fetching ProfessorClasse by ID", e);
            throw e;
        }
    }

    public AddEditProfessorClasseDto save(AddEditProfessorClasseDto dto) {
        LOGGER.info(Messages.START_FUNCTION, "save ProfessorClasse");
        try {
            ProfessorClasse professorClasse = toEntity(dto);
            professorClasse.setCreatedBy(getAuthenticatedUser());
            return toDto(professorClasseRepository.save(professorClasse));
        } catch (Exception e) {
            LOGGER.error("Error saving ProfessorClasse", e);
            throw e;
        }
    }

    public List<AddEditProfessorClasseDto> saveAll(List<AddEditProfessorClasseDto> dtos) {
        LOGGER.info(Messages.START_FUNCTION, "saveAll ProfessorClasse");
        try {
            OriachadUser user = getAuthenticatedUser();
            Year year = helperService.getCurrentYear();
            List<ProfessorClasse> professorClasses = dtos.stream()
                    .map(dto -> {
                        ProfessorClasse professorClasse = toEntity(dto);
                        professorClasse.setCreatedBy(user);
                        professorClasse.setYear(year);
                        return professorClasse;
                    })
                    .collect(Collectors.toList());
            return professorClasseRepository.saveAll(professorClasses).stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LOGGER.error("Error saving list of ProfessorClasse", e);
            throw e;
        }
    }

    public AddEditProfessorClasseDto update(Long id, AddEditProfessorClasseDto dto) {
        LOGGER.info(Messages.START_FUNCTION, "update ProfessorClasse");
        try {
            if (!professorClasseRepository.existsById(id)) {
                LOGGER.error(Messages.ENTITY_IS_NOT_EXIST, "ProfessorClasse");
                throw new IllegalArgumentException(Messages.ENTITY_IS_NOT_EXIST.replace("{}", "ProfessorClasse"));
            }
            ProfessorClasse professorClasse = toEntity(dto);
            professorClasse.setId(id);
            professorClasse.setCreatedBy(getAuthenticatedUser());
            return toDto(professorClasseRepository.save(professorClasse));
        } catch (Exception e) {
            LOGGER.error("Error updating ProfessorClasse", e);
            throw e;
        }
    }

    public boolean deleteById(Long id) {
        LOGGER.info(Messages.START_FUNCTION, "deleteById ProfessorClasse");
        try {
            if (professorClasseRepository.existsById(id)) {
                professorClasseRepository.deleteById(id);
                LOGGER.info(Messages.DELETE_SUCCESSFULLY, "ProfessorClasse");
                return true;
            } else {
                LOGGER.warn(Messages.ENTITY_IS_NOT_EXIST, "ProfessorClasse");
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("Error deleting ProfessorClasse", e);
            throw e;
        }
    }

    private AddEditProfessorClasseDto toDto(ProfessorClasse professorClasse) {
        return AddEditProfessorClasseDto.builder()
                .id(professorClasse.getId())
                .idClasse(professorClasse.getIdClasse())
                .idProfessor(professorClasse.getIdProfessor())
                .idYear(professorClasse.getIdYear())
                .idCreatedBy(professorClasse.getIdCreatedBy())
                .build();
    }

    private ProfessorClasse toEntity(AddEditProfessorClasseDto dto) {
        ProfessorClasse professorClasse = new ProfessorClasse();
        professorClasse.setId(dto.getId());
        professorClasse.setIdClasse(dto.getIdClasse());
        professorClasse.setIdProfessor(dto.getIdProfessor());
        //professorClasse.setIdYear(dto.getIdYear());
        return professorClasse;
    }

    private OriachadUser getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        return userDetails.getOriachadUser();
    }
}
