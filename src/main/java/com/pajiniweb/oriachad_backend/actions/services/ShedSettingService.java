package com.pajiniweb.oriachad_backend.actions.services;

import com.pajiniweb.oriachad_backend.actions.domains.dtos.*;
import com.pajiniweb.oriachad_backend.actions.domains.entities.DirectionsShed;
import com.pajiniweb.oriachad_backend.actions.domains.entities.RequiredProcedures;
import com.pajiniweb.oriachad_backend.actions.domains.entities.ShedCategory;
import com.pajiniweb.oriachad_backend.actions.domains.entities.ShedSetting;
import com.pajiniweb.oriachad_backend.actions.domains.entities.SupportCounselor;
import com.pajiniweb.oriachad_backend.actions.domains.entities.SupportStudent;
import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCE002.DiagnosticType;
import com.pajiniweb.oriachad_backend.actions.repositories.DirectionsShedRepository;
import com.pajiniweb.oriachad_backend.actions.repositories.RequiredProceduresRepository;
import com.pajiniweb.oriachad_backend.actions.repositories.ShedSettingRepository;
import com.pajiniweb.oriachad_backend.actions.repositories.SupportCounselorRepository;
import com.pajiniweb.oriachad_backend.actions.repositories.SupportStudentRepository;
import com.pajiniweb.oriachad_backend.domains.entities.Speciality;
import com.pajiniweb.oriachad_backend.helps.Messages;
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
public class ShedSettingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShedSettingService.class);

    private final ShedSettingRepository shedSettingRepository;
    private final SupportCounselorRepository supportCounselorRepository;
    private final SupportStudentRepository supportStudentRepository;
    private final RequiredProceduresRepository requiredProceduresRepository;
    private final DirectionsShedRepository directionsShedRepository;
    private final ShedCategoryService shedCategoryService;

    @Autowired
    public ShedSettingService(ShedSettingRepository shedSettingRepository,
                              SupportCounselorRepository supportCounselorRepository,
                              SupportStudentRepository supportStudentRepository,
                              RequiredProceduresRepository requiredProceduresRepository,
                              DirectionsShedRepository directionsShedRepository, ShedCategoryService shedCategoryService) {
        this.shedSettingRepository = shedSettingRepository;
        this.supportCounselorRepository = supportCounselorRepository;
        this.supportStudentRepository = supportStudentRepository;
        this.requiredProceduresRepository = requiredProceduresRepository;
        this.directionsShedRepository = directionsShedRepository;
        this.shedCategoryService = shedCategoryService;
    }

    // ------------------ CRUD Methods ------------------

    public Page<ShedSettingDto> findAll(Pageable pageable) {
        LOGGER.info(Messages.START_FUNCTION, "findAll ShedSetting");
        return shedSettingRepository.findAll(pageable)
                                    .map(this::toDto);
    }

    public Optional<ShedSettingDto> findById(Long id) {
        LOGGER.info(Messages.START_FUNCTION, "findById ShedSetting");
        return shedSettingRepository.findById(id)
                                    .map(this::toDto);
    }

    public ShedSettingDto save(ShedSettingDto dto) {
        LOGGER.info(Messages.START_FUNCTION, "save ShedSetting");
        try {
            Optional<Long> lastNumberOpt = shedSettingRepository.lastNumberOfShedSetting(dto.getIdShedCategory());
            Long nextSequence = lastNumberOpt.map(num -> num + 1)
                                             .orElse(1L);

            Optional<ShedCategoryDto> categoryOpt = shedCategoryService.findById(dto.getIdShedCategory());
            if (categoryOpt.isPresent()) {
                String categoryCode = categoryOpt.get()
                                                 .getCode();
                dto.setReference(categoryCode + "-" + nextSequence);
            } else {
                dto.setReference("UNKNOWN-" + nextSequence);
            }
            dto.setNumber(nextSequence);
            ShedSetting entity = toEntity(dto);
            entity = shedSettingRepository.save(entity);
            return toDto(entity);
        } catch (Exception e) {
            LOGGER.error("Error saving ShedSetting", e);
            throw e;
        }
    }

    public ShedSettingDto update(Long id, ShedSettingDto dto) {
        LOGGER.info(Messages.START_FUNCTION, "update ShedSetting");
        if (!shedSettingRepository.existsById(id)) {
            LOGGER.error(Messages.ENTITY_IS_NOT_EXIST, "ShedSetting");
            throw new IllegalArgumentException(Messages.ENTITY_IS_NOT_EXIST.replace("{}", "ShedSetting"));
        }
        try {
            ShedSetting entity = toEntity(dto);
            entity.setId(id);
            entity = shedSettingRepository.save(entity);
            return toDto(entity);
        } catch (Exception e) {
            LOGGER.error("Error updating ShedSetting", e);
            throw e;
        }
    }

    public boolean deleteById(Long id) {
        LOGGER.info(Messages.START_FUNCTION, "deleteById ShedSetting");
        try {
            if (shedSettingRepository.existsById(id)) {
                shedSettingRepository.deleteById(id);
                LOGGER.info(Messages.DELETE_SUCCESSFULLY, "ShedSetting");
                return true;
            } else {
                LOGGER.warn(Messages.ENTITY_IS_NOT_EXIST, "ShedSetting");
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("Error deleting ShedSetting", e);
            throw e;
        }
    }

    public Page<ShedSettingToDisplayDto> search(String search, Pageable pageable) {
        LOGGER.info(Messages.START_FUNCTION, "search ShedSetting by syndromeDiagnostic");
        try {
            return shedSettingRepository.search(search, pageable)
                                        .map(this::toDisplayDto);
        } catch (Exception e) {
            LOGGER.error("Error fetching paginated ShedSettingToDisplayDto by search query", e);
            throw e;
        }
    }

    public List<ShedSettingConditionDto> findByTargetAndCategory(DiagnosticType target, Long idShedCategory) {
        LOGGER.info(Messages.START_FUNCTION, "findByTargetAndCategory ShedSetting");

        List<ShedSettingConditionDto> settings = shedSettingRepository.findByTargetAndIdShedCategory(target, idShedCategory == -1 ? null : idShedCategory);
        return settings;
    }

    // ------------------ Mapping Methods ------------------

    public ShedSettingDto toDto(ShedSetting shedSetting) {
        return ShedSettingDto.builder()
                             .id(shedSetting.getId())
                             .idShedCategory(shedSetting.getShedCategory() != null ? shedSetting.getShedCategory()
                                                                                                .getId() : null)
                             .target(shedSetting.getTarget())
                             .reference(shedSetting.getReference())
                             .syndromeDiagnostic(shedSetting.getSyndromeDiagnostic())
                             .number(shedSetting.getNumber())
                             .hasGroup(shedSetting.getHasGroup())
                             .groupName(shedSetting.getGroupName())
                             .supportCounselors(shedSetting.getSupportCounselors() != null ?
                                     shedSetting.getSupportCounselors()
                                                .stream()
                                                .map(sc -> sc.getId())
                                                .collect(Collectors.toList())
                                     : null)
                             .supportStudents(shedSetting.getSupportStudents() != null ?
                                     shedSetting.getSupportStudents()
                                                .stream()
                                                .map(ss -> ss.getId())
                                                .collect(Collectors.toList())
                                     : null)
                             .requiredProcedures(shedSetting.getRequiredProcedures() != null ?
                                     shedSetting.getRequiredProcedures()
                                                .stream()
                                                .map(rp -> rp.getId())
                                                .collect(Collectors.toList())
                                     : null)
                             .directionSheds(shedSetting.getDirectionSheds() != null ?
                                     shedSetting.getDirectionSheds()
                                                .stream()
                                                .map(ds -> ds.getId())
                                                .collect(Collectors.toList())
                                     : null)
                             .build();
    }

    public ShedSetting toEntity(ShedSettingDto dto) {
        ShedSetting.ShedSettingBuilder builder = ShedSetting.builder();
        builder.id(dto.getId());

        // For ShedCategory, create a new object with the provided id.
        if (dto.getIdShedCategory() != null) {
            ShedCategory category = new ShedCategory();
            category.setId(dto.getIdShedCategory());
            builder.shedCategory(category);
        }
        builder.idShedCategory(dto.getIdShedCategory());
        builder.target(dto.getTarget());
        builder.reference(dto.getReference());
        builder.syndromeDiagnostic(dto.getSyndromeDiagnostic());
        builder.number(dto.getNumber());
        builder.hasGroup(dto.getHasGroup());
        if (dto.getHasGroup()) {
            builder.groupName(dto.getGroupName());
        }
        builder.idSpeciality(dto.getIdSpeciality());

        if (dto.getSupportCounselors() != null) {
            builder.supportCounselors(dto.getSupportCounselors()
                                         .stream()
                                         .map(id -> {
                                             SupportCounselor sc = new SupportCounselor();
                                             sc.setId(id);
                                             return sc;
                                         })
                                         .collect(Collectors.toSet()));
        }
        if (dto.getSupportStudents() != null) {
            builder.supportStudents(dto.getSupportStudents()
                                       .stream()
                                       .map(id -> {
                                           SupportStudent ss = new SupportStudent();
                                           ss.setId(id);
                                           return ss;
                                       })
                                       .collect(Collectors.toSet()));
        }
        if (dto.getRequiredProcedures() != null) {
            builder.requiredProcedures(dto.getRequiredProcedures()
                                          .stream()
                                          .map(id -> {
                                              RequiredProcedures rp = new RequiredProcedures();
                                              rp.setId(id);
                                              return rp;
                                          })
                                          .collect(Collectors.toSet()));
        }
        if (dto.getDirectionSheds() != null) {
            builder.directionSheds(dto.getDirectionSheds()
                                      .stream()
                                      .map(id -> {
                                          DirectionsShed ds = new DirectionsShed();
                                          ds.setId(id);
                                          return ds;
                                      })
                                      .collect(Collectors.toSet()));
        }
        if (dto.getIdSpeciality() != null) {
            builder.speciality(Speciality.builder()
                                         .id(dto.getIdSpeciality())
                                         .build());
        }
        return builder.build();
    }

    // Mapping method to convert ShedSetting to ShedSettingToDisplayDto
    public ShedSettingToDisplayDto toDisplayDto(ShedSetting shedSetting) {
        return ShedSettingToDisplayDto.builder()
                                      .id(shedSetting.getId())
                                      .shedCategory(shedSetting.getShedCategory()
                                                               .getName())
                                      .target(shedSetting.getTarget())
                                      .reference(shedSetting.getReference())
                                      .syndromeDiagnostic(shedSetting.getSyndromeDiagnostic())
                                      .number(shedSetting.getNumber())
                                      .hasGroup(shedSetting.getHasGroup())
                                      .groupName(shedSetting.getGroupName())
                                      .supportCounselors(shedSetting.getSupportCounselors() != null ?
                                              shedSetting.getSupportCounselors()
                                                         .stream()
                                                         .map(SupportCounselor::getId)
                                                         .collect(Collectors.toList())
                                              : null)
                                      .supportStudents(shedSetting.getSupportStudents() != null ?
                                              shedSetting.getSupportStudents()
                                                         .stream()
                                                         .map(SupportStudent::getId)
                                                         .collect(Collectors.toList())
                                              : null)
                                      .requiredProcedures(shedSetting.getRequiredProcedures() != null ?
                                              shedSetting.getRequiredProcedures()
                                                         .stream()
                                                         .map(RequiredProcedures::getId)
                                                         .collect(Collectors.toList())
                                              : null)
                                      .directionSheds(shedSetting.getDirectionSheds() != null ?
                                              shedSetting.getDirectionSheds()
                                                         .stream()
                                                         .map(DirectionsShed::getId)
                                                         .collect(Collectors.toList())
                                              : null)
                                      .build();
    }


    public List<ShedSettingConditionDto> allFilter(DiagnosticType target, Long idSpeciality, Long idShedCategory) {
        List<ShedSettingConditionDto> settings = shedSettingRepository.allFilter(target, idSpeciality == -1 ? null : idSpeciality, idShedCategory == -1 ? null : idShedCategory);
        return settings;
    }

    public List<ShedSettingConditionDto> allByCategory(Long idShedCategory) {
        List<ShedSettingConditionDto> settings = shedSettingRepository.allByCategory(idShedCategory);
        return settings;
    }

    public List<ShedSettingWithSupportDto> getByInterview(Long idInterview) {
        List<ShedSetting> entities = shedSettingRepository.findWithAssociationsByInterviewId(idInterview);
        return entities.stream()
                       .map(entity -> new ShedSettingWithSupportDto(
                               entity,
                               entity.getDirectionSheds(),
                               entity.getSupportStudents(),
                               entity.getSupportCounselors(),
                               entity.getRequiredProcedures()
                       ))
                       .toList();
    }

    public List<ShedSettingWithSupportDto> getByFollowUp(Long idFollowup) {
        List<ShedSetting> entities = shedSettingRepository.findWithAssociationsByFollowUpId(idFollowup);
        return entities.stream()
                       .map(entity -> new ShedSettingWithSupportDto(entity,
                               entity.getDirectionSheds(),
                               entity.getSupportStudents(),
                               entity.getSupportCounselors(),
                               entity.getRequiredProcedures()))
                       .toList();
    }
}
