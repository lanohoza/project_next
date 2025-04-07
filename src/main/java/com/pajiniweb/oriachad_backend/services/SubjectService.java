package com.pajiniweb.oriachad_backend.services;

import com.pajiniweb.oriachad_backend.domains.dtos.SubjectDto;
import com.pajiniweb.oriachad_backend.domains.dtos.SubjectShedSettingConditionDto;
import com.pajiniweb.oriachad_backend.domains.entities.*;
import com.pajiniweb.oriachad_backend.domains.enums.TypeEstablishment;
import com.pajiniweb.oriachad_backend.exceptions.ResourceNotFoundException;
import com.pajiniweb.oriachad_backend.repositories.ClasseRepository;
import com.pajiniweb.oriachad_backend.repositories.SubjectLevelRepository;
import com.pajiniweb.oriachad_backend.repositories.SubjectRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SubjectService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SubjectService.class);

    final private SubjectRepository subjectRepository;
    final private ClasseRepository classeRepository;
    final private SubjectLevelRepository subjectLevelRepository;
    final private HelperService helperService;


    public List<SubjectDto> findAllByCurrentEstablishmentType() {
        LOGGER.info("Start findAllByCurrentEstablishmentType function");
        return subjectRepository.findAllByType(helperService.getCurrentEstablishment().getType()).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public SubjectDto findById(Long id) {
        LOGGER.info("Start findById Subject");
        return subjectRepository.findById(id).map(this::convertToDto).orElseThrow(() -> new ResourceNotFoundException("Subject not found"));
    }

    public boolean save(SubjectDto subjectDto) {
        LOGGER.info("Start save Subject");

        try {
            Subject subject = convertToEntity(subjectDto);
            subjectRepository.save(subject);
            return true;
        } catch (Exception e) {
            LOGGER.error("Error saving Subject", e);
            throw new RuntimeException("Error saving Subject", e);
        }
    }

    public boolean update(Long id, SubjectDto subjectDto) {
        LOGGER.info("Start update Subject");

        try {
            Subject subject = subjectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Subject not found"));

            subject.setTitle(subjectDto.getTitle());

            subjectRepository.save(subject);
            return true;
        } catch (Exception e) {
            LOGGER.error("Error updating Subject", e);
            throw new RuntimeException("Error updating Subject", e);
        }
    }

    public boolean deleteById(Long id) {
        LOGGER.info("Start deleteById Subject");

        try {
            Subject subject = subjectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Subject not found"));
            subjectRepository.delete(subject);
            return true;
        } catch (Exception e) {
            LOGGER.error("Error deleting Subject", e);
            throw new RuntimeException("Error deleting Subject", e);
        }
    }

    public List<SubjectDto> findAllByClasse(Long idClasse) {
        LOGGER.info("Start findAllByClasse  Subjects");
        Classe classe = classeRepository.findById(idClasse).orElseThrow();
        return subjectLevelRepository.findAllByIdSpecialityAndIdLevel(classe.getIdSpeciality(), classe.getIdLevel()).stream().map(subjectLevel -> SubjectDto.builder().idSpeciality(subjectLevel.getIdSpeciality()).idLevel(subjectLevel.getIdLevel()).id(subjectLevel.getSubject().getId()).idSubjectLevel(subjectLevel.getId()).title(subjectLevel.getSubject().getTitle()).build()).toList();
    }

    public List<SubjectShedSettingConditionDto> findAllByTypeForShedSettingCondition() {

        LOGGER.info("Finding subjects by type");
        Establishment establishment= helperService.getCurrentEstablishment();
        return subjectRepository.findAllByType(establishment.getType())
                .stream()
                .map(this::toSubjectShedSettingConditionDto)
                .collect(Collectors.toList());
    }

    private SubjectDto convertToDto(Subject subject) {
        return SubjectDto.builder().id(subject.getId()).title(subject.getTitle()).build();
    }

    private Subject convertToEntity(SubjectDto subjectDto) {
        return Subject.builder().title(subjectDto.getTitle()).id(subjectDto.getId()).build();
    }

    private SubjectShedSettingConditionDto toSubjectShedSettingConditionDto(Subject subject) {
        return SubjectShedSettingConditionDto.builder()
                .id(subject.getId())
                .title(subject.getTitle())
                .build();
    }

    public List<SubjectDto> getAdminSubjectsByType(TypeEstablishment type) {
        return subjectRepository.findAllByType(type)
                .stream()
                .map(subject -> SubjectDto.builder()
                        .id(subject.getId())
                        .title(subject.getTitle())
                        .build())
                .toList();
    }
}
