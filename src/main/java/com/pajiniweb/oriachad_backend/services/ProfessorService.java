package com.pajiniweb.oriachad_backend.services;

import com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos.AddEditProfessorDto;
import com.pajiniweb.oriachad_backend.domains.dtos.ProfessorBreakDto;
import com.pajiniweb.oriachad_backend.domains.dtos.ProfessorDto;
import com.pajiniweb.oriachad_backend.domains.entities.*;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.repositories.*;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProfessorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProfessorService.class);

    private final ProfessorRepository professorRepository;
    private final HelperService helperService;

    private final ClasseRepository classeRepository;


    public List<ProfessorDto> findAll() {
        LOGGER.info(Messages.START_FUNCTION, "findAll Professor");
        Establishment establishment = helperService.getCurrentEstablishment();
        OriachadUser user = helperService.getCurrentUser().getOriachadUser();
        List<ProfessorDto> professors = professorRepository.findAllByIdEstablishmentAndIdCreatedBy(establishment.getId(), user.getId()).stream().map(professor -> ProfessorDto.builder().id(professor.getId()).firstName(professor.getFirstName()).lastName(professor.getLastName()).phoneNumber(professor.getPhoneNumber()).email(professor.getEmail()).idSubject(professor.getIdSubject()).build()).toList();
        return professors;

    }

    public ProfessorDto findById(Long id) {
        LOGGER.info(Messages.START_FUNCTION, "findById Professor");
        Professor professor = professorRepository.findById(id).orElseThrow();

        return ProfessorDto.builder()
                .id(professor.getId())
                .firstName(professor.getFirstName())
                .lastName(professor.getLastName())
                .phoneNumber(professor.getPhoneNumber())
                .email(professor.getEmail())
                .idSubject(professor.getIdSubject())
                .coordinator(professor.getCoordinator())
                .breaks(
                        professor.getBreaks() == null ? null :
                                professor.getBreaks().stream()
                                        .map(breakEntity -> ProfessorBreakDto.builder()
                                                .id(breakEntity.getId())
                                                .breakDay(breakEntity.getBreakDay())
                                                .endHour(breakEntity.getEndHour())
                                                .startHour(breakEntity.getStartHour())
                                                .build())
                                        .toList()
                )
                .classes(
                        professor.getClasses() == null ? null :
                                professor.getClasses().stream()
                                        .map(professorClasse -> professorClasse.getClasse().getId()) // Extract Classe ID
                                        .toList()
                )
                .build();
    }


    public boolean save(AddEditProfessorDto addEditProfessorDto) {
        LOGGER.info(Messages.START_FUNCTION, "save addEditTeacherDto");
        Establishment establishment = helperService.getCurrentEstablishment();
        OriachadUser user = helperService.getCurrentUser().getOriachadUser();
        Year year = helperService.getCurrentYear();
        Professor professor = Professor.builder().createdBy(user).establishment(establishment).email(addEditProfessorDto.getEmail()).phoneNumber(addEditProfessorDto.getPhoneNumber()).firstName(addEditProfessorDto.getFirstName()).lastName(addEditProfessorDto.getLastName()).subject(Subject.builder().id(addEditProfessorDto.getIdSubject()).build()).coordinator(addEditProfessorDto.getCoordinator()).build();
        professor.setBreaks(addEditProfessorDto.getBreaks() == null ? null : addEditProfessorDto.getBreaks().stream().map(professorBreakDto -> ProfessorBreak.builder().professor(professor).breakDay(professorBreakDto.getBreakDay()).endHour(professorBreakDto.getEndHour()).startHour(professorBreakDto.getStartHour()).build()).toList());
        professor.setClasses(
                addEditProfessorDto.getClasses() == null ? null :
                        addEditProfessorDto.getClasses().stream()
                                .map(idProfessorClasseDto -> ProfessorClasse.builder()
                                        .classe(Classe.builder().id(idProfessorClasseDto).build())
                                        .professor(professor)
                                        .year(year)
                                        .createdBy(user)
                                        .build())
                                .toList()
        );
        professorRepository.save(professor);
        return true;
    }

    @Transactional
    public boolean update(AddEditProfessorDto addEditProfessorDto, Long idProfessor) {
        LOGGER.info(Messages.START_FUNCTION, "update Professor");
        Optional<Professor> optionalProfessor = professorRepository.findById(idProfessor);
        OriachadUser user = helperService.getCurrentUser().getOriachadUser();
        Year year = helperService.getCurrentYear();
        if (optionalProfessor.isPresent()) {
            Professor professor = optionalProfessor.get();
            professor.setFirstName(addEditProfessorDto.getFirstName());
            professor.setLastName(addEditProfessorDto.getLastName());
            professor.setSubject(Subject.builder().id(addEditProfessorDto.getIdSubject()).build());
            professor.setCoordinator(addEditProfessorDto.getCoordinator());
            professor.setEmail(addEditProfessorDto.getEmail());
            professor.setPhoneNumber(addEditProfessorDto.getPhoneNumber());
            professor.getBreaks().clear();
            professor.getBreaks().addAll(addEditProfessorDto.getBreaks() == null ? null : addEditProfessorDto.getBreaks().stream().map(professorBreakDto -> ProfessorBreak.builder().professor(professor).breakDay(professorBreakDto.getBreakDay()).endHour(professorBreakDto.getEndHour()).startHour(professorBreakDto.getStartHour()).build()).toList());
            professor.getClasses().clear();
            if (addEditProfessorDto.getClasses() != null) {
                professor.getClasses().addAll(
                        addEditProfessorDto.getClasses().stream()
                                .map(idProfessorClasseDto -> ProfessorClasse.builder()
                                        .classe(Classe.builder().id(idProfessorClasseDto).build())
                                        .professor(professor)
                                        .year(year)
                                        .createdBy(user)
                                        .build())
                                .toList()
                );
            }
            professorRepository.save(professor);
        }
        return true;

    }

    @Transactional
    public boolean deleteById(Long id) throws Exception {
        LOGGER.info(Messages.START_FUNCTION, "deleteById Professor");
        try {
            if (professorRepository.existsById(id)) {
                List<Classe> classes = classeRepository.findByIdProfessor(id);
                if (classes != null && !classes.isEmpty()) {
                    throw new Exception("لا يمكنك حذف هذا الأستاذ لأنه مربوط بقسم");
                }
                professorRepository.deleteById(id);
                LOGGER.info(Messages.DELETE_SUCCESSFULLY, "Professor");
                return true;
            } else {
                LOGGER.warn(Messages.ENTITY_IS_NOT_EXIST, "Professor");
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("Error deleting Professor", e);
            throw e;
        }
    }

    public Page<ProfessorDto> searchProfessors(String search, Pageable pageable) {
        LOGGER.info(Messages.START_FUNCTION, "searchProfessors");
        Establishment establishment = helperService.getCurrentEstablishment();
        OriachadUser user = helperService.getCurrentUser().getOriachadUser();
        return professorRepository.search(search, establishment.getId(), user.getId(), pageable).map(professor -> ProfessorDto.builder().id(professor.getId()).firstName(professor.getFirstName()).lastName(professor.getLastName()).phoneNumber(professor.getPhoneNumber()).email(professor.getEmail()).idSubject(professor.getIdSubject()).coordinator(professor.getCoordinator()).subjectTitle(professor.getSubject().getTitle()).build());

    }


}
