package com.pajiniweb.oriachad_backend.administration.services.scripts;

import com.pajiniweb.oriachad_backend.actions.domains.entities.ShedSetting;
import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCO001.conditions.*;
import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCO001.data.TCO001GuidanceSpecialityConfig;
import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCO001.data.TCO001SubjectConfig;
import com.pajiniweb.oriachad_backend.actions.repositories.scripts.TCO001.condtions.*;
import com.pajiniweb.oriachad_backend.actions.repositories.scripts.TCO001.data.TCO001GuidanceSpecialityConfigRepository;
import com.pajiniweb.oriachad_backend.actions.repositories.scripts.TCO001.data.TCO001SubjectAverageRepository;
import com.pajiniweb.oriachad_backend.actions.repositories.scripts.TCO001.data.TCO001SubjectConfigRepository;
import com.pajiniweb.oriachad_backend.actions.services.TCE002.TCE002ConditionService;
import com.pajiniweb.oriachad_backend.administration.domains.dtos.scripts.TCO001.conditions.*;
import com.pajiniweb.oriachad_backend.administration.domains.dtos.scripts.TCO001.data.TCO001GuidanceSpecialityConfigDto;
import com.pajiniweb.oriachad_backend.administration.domains.dtos.scripts.TCO001.data.TCO001SubjectConfigDto;
import com.pajiniweb.oriachad_backend.domains.dtos.LevelDto;
import com.pajiniweb.oriachad_backend.domains.entities.ClasseBreak;
import com.pajiniweb.oriachad_backend.domains.entities.GuidanceSpeciality;
import com.pajiniweb.oriachad_backend.domains.entities.Level;
import com.pajiniweb.oriachad_backend.domains.entities.Subject;
import com.pajiniweb.oriachad_backend.helps.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional
public class TCO001Service {

    @Autowired
    TCO001StudentConditionRepository tco001StudentConditionRepository;
    @Autowired
    private TCO001ClasseConditionRepository tco001ClasseConditionRepository;

    @Autowired
    private TCO001SubjectConditionRepository tco001SubjectConditionRepository;
    @Autowired
    private TCO001LevelConditionRepository tco001LevelConditionRepository;
    @Autowired
    private TCO001SpecialityConditionRepository tco001SpecialityConditionRepository;
    @Autowired
    private TCO001EstablishmentConditionRepository tco001EstablishmentConditionRepository;
    @Autowired
    private TCO001GuidanceSpecialityConfigRepository tco001GuidanceSpecialityConfigRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(TCE002ConditionService.class);
    @Autowired
    private TCO001SubjectAverageRepository tCO001SubjectAverageRepository;
    @Autowired
    private TCO001SubjectConfigRepository tCO001SubjectConfigRepository;

    // --- CRUD Methods ---

    public TCO001StudentConditionDto findStudentConditionById(Long id) throws Exception {
        LOGGER.info(Messages.START_FUNCTION, "findById TCE002Condition");
        return tco001StudentConditionRepository.findById(id).map(condition -> TCO001StudentConditionDto.builder().guidanceOperate(condition.getGuidanceOperate()).rank(condition.getRank()).number(condition.getNumber()).subjectAverage(condition.getSubjectAverage()).subjectOperate(condition.getSubjectOperate()).idShedSetting(condition.getIdShedSetting()).average(condition.getAverage()).subjectType(condition.getSubjectType()).id(condition.getId()).subjectAverageMax(condition.getSubjectAverageMax()).averageMax(condition.getAverageMax()).build()).orElseThrow(() -> new Exception("Could not find student Condition"));
    }

    public boolean saveStudentCondition(TCO001StudentConditionDto condition) {
        LOGGER.info(Messages.START_FUNCTION, "save TCE002Condition");

        TCO001StudentCondition tco001StudentCondition = TCO001StudentCondition.builder().guidanceOperate(condition.getGuidanceOperate()).rank(condition.getRank()).number(condition.getNumber()).shedSetting(ShedSetting.builder().id(condition.getIdShedSetting()).build()).average(condition.getAverage()).subjectAverage(condition.getSubjectAverage()).subjectOperate(condition.getSubjectOperate()).subjectType(condition.getSubjectType()).subjectAverageMax(condition.getSubjectAverageMax()).averageMax(condition.getAverageMax()).build();

        tco001StudentConditionRepository.save(tco001StudentCondition);

        return true;
    }

    public boolean updateStudentCondition(Long id, TCO001StudentConditionDto condition) {
        LOGGER.info(Messages.START_FUNCTION, "update TCE002Condition");
        if (!tco001StudentConditionRepository.existsById(id)) {
            LOGGER.error(Messages.ENTITY_IS_NOT_EXIST, "TCE002Condition");
            throw new IllegalArgumentException(Messages.ENTITY_IS_NOT_EXIST.replace("{}", "TCE002Condition"));
        }
        TCO001StudentCondition tco001StudentCondition = tco001StudentConditionRepository.findById(id).orElseThrow();
        tco001StudentCondition.setGuidanceOperate(condition.getGuidanceOperate());
        tco001StudentCondition.setRank(condition.getRank());
        tco001StudentCondition.setNumber(condition.getNumber());
        tco001StudentCondition.setShedSetting(ShedSetting.builder().id(condition.getIdShedSetting()).build());
        tco001StudentCondition.setAverage(condition.getAverage());
        tco001StudentCondition.setSubjectType(condition.getSubjectType());
        tco001StudentCondition.setSubjectAverage(condition.getSubjectAverage());
        tco001StudentCondition.setSubjectOperate(condition.getSubjectOperate());

        return true;

    }

    public boolean deleteStudentConditionById(Long id) {
        LOGGER.info(Messages.START_FUNCTION, "search deleteStudentConditionById ");
        tco001StudentConditionRepository.deleteById(id);
        return true;
    }

    public Page<TCO001StudentConditionDto> searchStudentCondition(String search, Pageable pageable) {
        LOGGER.info(Messages.START_FUNCTION, "search TCO001StudentConditionDto by syndromeDiagnostic");
        return tco001StudentConditionRepository.search(search, pageable).map(condition -> TCO001StudentConditionDto.builder().guidanceOperate(condition.getGuidanceOperate()).number(condition.getNumber()).rank(condition.getRank()).idShedSetting(condition.getIdShedSetting()).shedSetting(condition.getShedSetting().getSyndromeDiagnostic()).subjectType(condition.getSubjectType()).average(condition.getAverage()).subjectOperate(condition.getSubjectOperate()).id(condition.getId()).build());
    }

    // --- CRUD Methods for Student Condition ---
    public TCO001ClasseConditionDto findClasseConditionById(Long id) throws Exception {
        LOGGER.info(Messages.START_FUNCTION, "findById TCO001ClasseCondition");
        return tco001ClasseConditionRepository.findById(id).map(condition -> TCO001ClasseConditionDto.builder().guidanceOperate(condition.getGuidanceOperate()).rank(condition.getRank()).number(condition.getNumber()).subjectAverage(condition.getSubjectAverage()).subjectOperate(condition.getSubjectOperate()).idShedSetting(condition.getIdShedSetting()).average(condition.getAverage()).subjectType(condition.getSubjectType()).id(condition.getId()).subjectAverageMax(condition.getSubjectAverageMax()).rateMax(condition.getRateMax()).rateOperate(condition.getRateOperate()).averageMax(condition.getAverageMax()).rate(condition.getRate()).build()).orElseThrow(() -> new Exception("Could not find classe Condition"));
    }

    public boolean saveClasseCondition(TCO001ClasseConditionDto condition) {
        LOGGER.info(Messages.START_FUNCTION, "save TCO001ClasseCondition");
        TCO001ClasseCondition tco001ClasseCondition = TCO001ClasseCondition.builder().guidanceOperate(condition.getGuidanceOperate()).rank(condition.getRank()).number(condition.getNumber()).shedSetting(ShedSetting.builder().id(condition.getIdShedSetting()).build()).average(condition.getAverage()).subjectType(condition.getSubjectType()).subjectAverage(condition.getSubjectAverage()).subjectOperate(condition.getSubjectOperate()).rate(condition.getRate()).subjectAverageMax(condition.getSubjectAverageMax()).rateMax(condition.getRateMax()).rateOperate(condition.getRateOperate()).averageMax(condition.getAverageMax()).build();

        tco001ClasseConditionRepository.save(tco001ClasseCondition);
        return true;
    }

    public boolean updateClasseCondition(Long id, TCO001ClasseConditionDto condition) {
        LOGGER.info(Messages.START_FUNCTION, "update TCO001ClasseCondition");
        if (!tco001ClasseConditionRepository.existsById(id)) {
            LOGGER.error(Messages.ENTITY_IS_NOT_EXIST, "TCO001ClasseCondition");
            throw new IllegalArgumentException(Messages.ENTITY_IS_NOT_EXIST.replace("{}", "TCO001ClasseCondition"));
        }
        TCO001ClasseCondition tco001ClasseCondition = tco001ClasseConditionRepository.findById(id).orElseThrow();
        tco001ClasseCondition.setGuidanceOperate(condition.getGuidanceOperate());
        tco001ClasseCondition.setRank(condition.getRank());
        tco001ClasseCondition.setNumber(condition.getNumber());
        tco001ClasseCondition.setShedSetting(ShedSetting.builder().id(condition.getIdShedSetting()).build());
        tco001ClasseCondition.setAverage(condition.getAverage());
        tco001ClasseCondition.setSubjectType(condition.getSubjectType());
        tco001ClasseCondition.setSubjectAverage(condition.getSubjectAverage());
        tco001ClasseCondition.setSubjectOperate(condition.getSubjectOperate());
        tco001ClasseCondition.setSubjectAverageMax(condition.getSubjectAverageMax());
        tco001ClasseCondition.setRateMax(condition.getRateMax());
        tco001ClasseCondition.setRateOperate(condition.getRateOperate());
        tco001ClasseCondition.setAverageMax(condition.getAverageMax());

        tco001ClasseConditionRepository.save(tco001ClasseCondition);
        return true;
    }

    public boolean deleteClasseConditionById(Long id) {
        LOGGER.info(Messages.START_FUNCTION, "delete TCO001ClasseCondition");
        tco001ClasseConditionRepository.deleteById(id);
        return true;
    }

    public Page<TCO001ClasseConditionDto> searchClasseCondition(String search, Pageable pageable) {
        LOGGER.info(Messages.START_FUNCTION, "search TCO001ClasseCondition by syndromeDiagnostic");
        return tco001ClasseConditionRepository.search(search, pageable).map(condition -> TCO001ClasseConditionDto.builder().guidanceOperate(condition.getGuidanceOperate()).rank(condition.getRank()).number(condition.getNumber()).subjectAverage(condition.getSubjectAverage()).subjectOperate(condition.getSubjectOperate()).idShedSetting(condition.getIdShedSetting()).average(condition.getAverage()).subjectType(condition.getSubjectType()).id(condition.getId()).rate(condition.getRate()).shedSetting(condition.getShedSetting().getSyndromeDiagnostic()).build());
    }


    // --- CRUD Methods for Subject Condition ---
    public TCO001SubjectConditionDto findSubjectConditionById(Long id) throws Exception {
        LOGGER.info(Messages.START_FUNCTION, "findById TCO001ClasseCondition");
        return tco001SubjectConditionRepository.findById(id).map(condition -> TCO001SubjectConditionDto.builder().operate(condition.getOperate()).number(condition.getNumber()).idGuidanceSpeciality(condition.getIdGuidanceSpeciality()).idShedSetting(condition.getIdShedSetting()).average(condition.getAverage()).idSubject(condition.getIdSubject()).rate(condition.getRate()).id(condition.getId()).rateMax(condition.getRateMax()).rateOperate(condition.getRateOperate()).averageMax(condition.getAverageMax()).rate(condition.getRate()).build()).orElseThrow(() -> new Exception("Could not find classe Condition"));
    }

    public boolean saveSubjectCondition(TCO001SubjectConditionDto condition) {
        LOGGER.info(Messages.START_FUNCTION, "save TCO001ClasseCondition");
        TCO001SubjectCondition tco001SubjectConditionDto = TCO001SubjectCondition.builder().operate(condition.getOperate()).number(condition.getNumber()).guidanceSpeciality(GuidanceSpeciality.builder().id(condition.getIdGuidanceSpeciality()).build()).shedSetting(ShedSetting.builder().id(condition.getIdShedSetting()).build()).average(condition.getAverage()).subject(Subject.builder().id(condition.getIdSubject()).build()).rate(condition.getRate()).rateMax(condition.getRateMax()).rateOperate(condition.getRateOperate()).averageMax(condition.getAverageMax()).rate(condition.getRate()).build();

        tco001SubjectConditionRepository.save(tco001SubjectConditionDto);
        return true;
    }

    public boolean updateSubjectCondition(Long id, TCO001SubjectConditionDto condition) {
        LOGGER.info(Messages.START_FUNCTION, "update TCO001ClasseCondition");
        if (!tco001SubjectConditionRepository.existsById(id)) {
            LOGGER.error(Messages.ENTITY_IS_NOT_EXIST, "TCO001ClasseCondition");
            throw new IllegalArgumentException(Messages.ENTITY_IS_NOT_EXIST.replace("{}", "TCO001ClasseCondition"));
        }
        TCO001SubjectCondition tco001SubjectCondition = tco001SubjectConditionRepository.findById(id).orElseThrow();
        tco001SubjectCondition.setOperate(condition.getOperate());
        tco001SubjectCondition.setNumber(condition.getNumber());
        tco001SubjectCondition.setGuidanceSpeciality(GuidanceSpeciality.builder().id(condition.getIdGuidanceSpeciality()).build());
        tco001SubjectCondition.setShedSetting(ShedSetting.builder().id(condition.getIdShedSetting()).build());
        tco001SubjectCondition.setAverage(condition.getAverage());
        tco001SubjectCondition.setSubject(Subject.builder().id(condition.getIdSubject()).build());
        tco001SubjectCondition.setRate(condition.getRate());
        tco001SubjectCondition.setRateMax(condition.getRateMax());
        tco001SubjectCondition.setRateOperate(condition.getRateOperate());
        tco001SubjectCondition.setAverageMax(condition.getAverageMax());
        tco001SubjectConditionRepository.save(tco001SubjectCondition);
        return true;
    }

    public boolean deleteSubjectConditionById(Long id) {
        LOGGER.info(Messages.START_FUNCTION, "delete TCO001ClasseCondition");
        tco001SubjectConditionRepository.deleteById(id);
        return true;
    }

    public Page<TCO001SubjectConditionDto> searchSubjectCondition(String search, Pageable pageable) {
        LOGGER.info(Messages.START_FUNCTION, "search TCO001ClasseCondition by syndromeDiagnostic");
        return tco001SubjectConditionRepository.search(search, pageable).map(condition -> TCO001SubjectConditionDto.builder().id(condition.getId()).operate(condition.getOperate()).number(condition.getNumber()).guidanceSpeciality(condition.getGuidanceSpeciality().getTitle()).shedSetting(condition.getShedSetting().getSyndromeDiagnostic()).average(condition.getAverage()).subject(condition.getSubject().getTitle()).rate(condition.getRate()).build());
    }


    // --- CRUD Methods for Level Condition ---
    public TCO001LevelConditionDto findLevelConditionById(Long id) throws Exception {
        LOGGER.info(Messages.START_FUNCTION, "findById TCO001ClasseCondition");
        return tco001LevelConditionRepository.findById(id).map(condition -> TCO001LevelConditionDto.builder().operate(condition.getOperate()).number(condition.getNumber()).idGuidanceSpeciality(condition.getIdGuidanceSpeciality()).idShedSetting(condition.getIdShedSetting()).average(condition.getAverage()).idLevel(condition.getIdLevel()).rate(condition.getRate()).id(condition.getId()).rateMax(condition.getRateMax()).rateOperate(condition.getRateOperate()).averageMax(condition.getAverageMax()).rate(condition.getRate()).build()).orElseThrow(() -> new Exception("Could not find classe Condition"));
    }

    public boolean saveLevelCondition(TCO001LevelConditionDto condition) {
        LOGGER.info(Messages.START_FUNCTION, "save TCO001ClasseCondition");
        TCO001LevelCondition tco001LevelConditionDto = TCO001LevelCondition.builder().operate(condition.getOperate()).number(condition.getNumber()).guidanceSpeciality(GuidanceSpeciality.builder().id(condition.getIdGuidanceSpeciality()).build()).shedSetting(ShedSetting.builder().id(condition.getIdShedSetting()).build()).average(condition.getAverage()).level(Level.builder().id(condition.getIdLevel()).build()).rate(condition.getRate()).rateMax(condition.getRateMax()).rateOperate(condition.getRateOperate()).averageMax(condition.getAverageMax()).rate(condition.getRate()).build();

        tco001LevelConditionRepository.save(tco001LevelConditionDto);
        return true;
    }

    public boolean updateLevelCondition(Long id, TCO001LevelConditionDto condition) {
        LOGGER.info(Messages.START_FUNCTION, "update TCO001ClasseCondition");
        if (!tco001LevelConditionRepository.existsById(id)) {
            LOGGER.error(Messages.ENTITY_IS_NOT_EXIST, "TCO001ClasseCondition");
            throw new IllegalArgumentException(Messages.ENTITY_IS_NOT_EXIST.replace("{}", "TCO001ClasseCondition"));
        }
        TCO001LevelCondition tco001LevelCondition = tco001LevelConditionRepository.findById(id).orElseThrow();
        tco001LevelCondition.setOperate(condition.getOperate());
        tco001LevelCondition.setNumber(condition.getNumber());
        tco001LevelCondition.setGuidanceSpeciality(GuidanceSpeciality.builder().id(condition.getIdGuidanceSpeciality()).build());
        tco001LevelCondition.setShedSetting(ShedSetting.builder().id(condition.getIdShedSetting()).build());
        tco001LevelCondition.setAverage(condition.getAverage());
        tco001LevelCondition.setLevel(Level.builder().id(condition.getIdLevel()).build());
        tco001LevelCondition.setRate(condition.getRate());
        tco001LevelCondition.setRateMax(condition.getRateMax());
        tco001LevelCondition.setRateOperate(condition.getRateOperate());
        tco001LevelCondition.setAverageMax(condition.getAverageMax());
        tco001LevelConditionRepository.save(tco001LevelCondition);
        return true;
    }

    public boolean deleteLevelConditionById(Long id) {
        LOGGER.info(Messages.START_FUNCTION, "delete TCO001ClasseCondition");
        tco001LevelConditionRepository.deleteById(id);
        return true;
    }

    public Page<TCO001LevelConditionDto> searchLevelCondition(String search, Pageable pageable) {
        LOGGER.info(Messages.START_FUNCTION, "search TCO001ClasseCondition by syndromeDiagnostic");
        return tco001LevelConditionRepository.search(search, pageable).map(condition -> TCO001LevelConditionDto.builder().id(condition.getId()).operate(condition.getOperate()).number(condition.getNumber()).level(LevelDto.builder().id(condition.getIdLevel()).title(condition.getLevel().getTitle()).type(condition.getLevel().getType()).build()).guidanceSpeciality(condition.getGuidanceSpeciality().getTitle()).shedSetting(condition.getShedSetting().getSyndromeDiagnostic()).average(condition.getAverage()).rate(condition.getRate()).build());
    }


    // --- CRUD Methods for Speciality Condition ---
    public TCO001SpecialityConditionDto findSpecialityConditionById(Long id) throws Exception {
        LOGGER.info(Messages.START_FUNCTION, "findById TCO001ClasseCondition");
        return tco001SpecialityConditionRepository.findById(id).map(condition -> TCO001SpecialityConditionDto.builder().guidanceOperate(condition.getGuidanceOperate()).number(condition.getNumber()).idGuidanceSpeciality(condition.getIdGuidanceSpeciality()).idShedSetting(condition.getIdShedSetting()).average(condition.getAverage()).subjectAverage(condition.getSubjectAverage()).subjectOperate(condition.getSubjectOperate()).rate(condition.getRate()).id(condition.getId()).subjectAverageMax(condition.getSubjectAverageMax()).averageMax(condition.getAverageMax()).rateMax(condition.getRateMax()).rateOperate(condition.getRateOperate()).idSubject(condition.getIdSubject()).idLevel(condition.getIdLevel()).subjectType(condition.getSubjectType()).build()).orElseThrow(() -> new Exception("Could not find classe Condition"));
    }

    public boolean saveSpecialityCondition(TCO001SpecialityConditionDto condition) {
        LOGGER.info(Messages.START_FUNCTION, "save TCO001ClasseCondition");
        TCO001SpecialityCondition tco001SpecialityConditionDto = TCO001SpecialityCondition.builder().guidanceOperate(condition.getGuidanceOperate()).number(condition.getNumber()).guidanceSpeciality(GuidanceSpeciality.builder().id(condition.getIdGuidanceSpeciality()).build()).average(condition.getAverage()).subjectType(condition.getSubjectType()).subjectAverage(condition.getSubjectAverage()).level(Level.builder().id(condition.getIdLevel()).build()).subjectOperate(condition.getSubjectOperate()).shedSetting(ShedSetting.builder().id(condition.getIdShedSetting()).build()).subjectAverageMax(condition.getSubjectAverageMax()).rateMax(condition.getRateMax()).rateOperate(condition.getRateOperate()).averageMax(condition.getAverageMax()).rate(condition.getRate()).build();

        if (condition.getSubjectType() == TCO001SpecialityCondition.TCO001SpecialityConditionSubjectType.one) {
            tco001SpecialityConditionDto.setSubject(Subject.builder().id(condition.getIdSubject()).build());
        }
        tco001SpecialityConditionRepository.save(tco001SpecialityConditionDto);
        return true;
    }

    public boolean updateSpecialityCondition(Long id, TCO001SpecialityConditionDto condition) {
        LOGGER.info(Messages.START_FUNCTION, "update TCO001ClasseCondition");
        if (!tco001SpecialityConditionRepository.existsById(id)) {
            LOGGER.error(Messages.ENTITY_IS_NOT_EXIST, "TCO001ClasseCondition");
            throw new IllegalArgumentException(Messages.ENTITY_IS_NOT_EXIST.replace("{}", "TCO001ClasseCondition"));
        }
        TCO001SpecialityCondition tco001SpecialityCondition = tco001SpecialityConditionRepository.findById(id).orElseThrow();
        tco001SpecialityCondition.setGuidanceOperate(condition.getGuidanceOperate());
        tco001SpecialityCondition.setNumber(condition.getNumber());
        tco001SpecialityCondition.setGuidanceSpeciality(GuidanceSpeciality.builder().id(condition.getIdGuidanceSpeciality()).build());
        tco001SpecialityCondition.setAverage(condition.getAverage());
        tco001SpecialityCondition.setSubjectType(condition.getSubjectType());
        tco001SpecialityCondition.setSubjectAverage(condition.getSubjectAverage());
        tco001SpecialityCondition.setSubjectOperate(condition.getSubjectOperate());
        tco001SpecialityCondition.setLevel(Level.builder().id(condition.getIdLevel()).build());
        tco001SpecialityCondition.setShedSetting(ShedSetting.builder().id(condition.getIdShedSetting()).build());
        tco001SpecialityCondition.setRate(condition.getRate());
        tco001SpecialityCondition.setSubjectAverageMax(condition.getSubjectAverageMax());
        tco001SpecialityCondition.setRateMax(condition.getRateMax());
        tco001SpecialityCondition.setRateOperate(condition.getRateOperate());
        tco001SpecialityCondition.setAverageMax(condition.getAverageMax());
        if (condition.getSubjectType() == TCO001SpecialityCondition.TCO001SpecialityConditionSubjectType.one) {
            tco001SpecialityCondition.setSubject(Subject.builder().id(condition.getIdSubject()).build());
        }
        tco001SpecialityConditionRepository.save(tco001SpecialityCondition);
        return true;
    }

    public boolean deleteSpecialityConditionById(Long id) {
        LOGGER.info(Messages.START_FUNCTION, "delete TCO001ClasseCondition");
        tco001SpecialityConditionRepository.deleteById(id);
        return true;
    }

    public Page<TCO001SpecialityConditionDto> searchSpecialityCondition(String search, Pageable pageable) {
        LOGGER.info(Messages.START_FUNCTION, "search TCO001ClasseCondition by syndromeDiagnostic");
        return tco001SpecialityConditionRepository.search(search, pageable).map(condition -> TCO001SpecialityConditionDto.builder().guidanceOperate(condition.getGuidanceOperate()).number(condition.getNumber()).guidanceSpeciality(condition.getGuidanceSpeciality().getTitle()).shedSetting(condition.getShedSetting().getSyndromeDiagnostic()).average(condition.getAverage()).subjectOperate(condition.getSubjectOperate()).rate(condition.getRate()).level(LevelDto.builder().id(condition.getIdLevel()).title(condition.getLevel().getTitle()).type(condition.getLevel().getType()).build()).id(condition.getId()).subject(condition.getSubjectType() == TCO001SpecialityCondition.TCO001SpecialityConditionSubjectType.one ? condition.getSubject().getTitle() : null).subjectType(condition.getSubjectType()).build());
    }

    // --- CRUD Methods for Establishment Condition ---
    public TCO001EstablishmentConditionDto findEstablishmentConditionById(Long id) throws Exception {
        LOGGER.info(Messages.START_FUNCTION, "findById TCO001ClasseCondition");
        return tco001EstablishmentConditionRepository.findById(id).map(condition -> TCO001EstablishmentConditionDto.builder().guidanceOperate(condition.getGuidanceOperate()).number(condition.getNumber()).idGuidanceSpeciality(condition.getIdGuidanceSpeciality()).idShedSetting(condition.getIdShedSetting()).average(condition.getAverage()).idGuidanceSpeciality(condition.getIdGuidanceSpeciality()).rate(condition.getRate()).id(condition.getId()).idLevel(condition.getIdLevel()).subjectOperate(condition.getSubjectOperate()).idSubject(condition.getIdSubject()).subjectType(condition.getSubjectType()).subjectAverage(condition.getSubjectAverage()).subjectAverageMax(condition.getSubjectAverageMax()).rateMax(condition.getRateMax()).rateOperate(condition.getRateOperate()).averageMax(condition.getAverageMax()).build()).orElseThrow(() -> new Exception("Could not find classe Condition"));
    }

    public boolean saveEstablishmentCondition(TCO001EstablishmentConditionDto condition) {
        LOGGER.info(Messages.START_FUNCTION, "save TCO001ClasseCondition");
        TCO001EstablishmentCondition tco001EstablishmentConditionDto = TCO001EstablishmentCondition.builder().guidanceOperate(condition.getGuidanceOperate()).number(condition.getNumber()).guidanceSpeciality(GuidanceSpeciality.builder().id(condition.getIdGuidanceSpeciality()).build()).average(condition.getAverage()).subjectType(condition.getSubjectType()).subjectAverage(condition.getSubjectAverage()).level(Level.builder().id(condition.getIdLevel()).build()).subjectOperate(condition.getSubjectOperate()).shedSetting(ShedSetting.builder().id(condition.getIdShedSetting()).build()).subjectAverageMax(condition.getSubjectAverageMax()).rateMax(condition.getRateMax()).rateOperate(condition.getRateOperate()).averageMax(condition.getAverageMax()).rate(condition.getRate()).build();
        if (condition.getSubjectType() == TCO001EstablishmentCondition.TCO001EstablishmentConditionSubjectType.one) {
            tco001EstablishmentConditionDto.setSubject(Subject.builder().id(condition.getIdSubject()).build());
        }
        tco001EstablishmentConditionRepository.save(tco001EstablishmentConditionDto);
        return true;
    }

    public boolean updateEstablishmentCondition(Long id, TCO001EstablishmentConditionDto condition) {
        LOGGER.info(Messages.START_FUNCTION, "update TCO001ClasseCondition");
        if (!tco001EstablishmentConditionRepository.existsById(id)) {
            LOGGER.error(Messages.ENTITY_IS_NOT_EXIST, "TCO001ClasseCondition");
            throw new IllegalArgumentException(Messages.ENTITY_IS_NOT_EXIST.replace("{}", "TCO001ClasseCondition"));
        }
        TCO001EstablishmentCondition tco001EstablishmentCondition = tco001EstablishmentConditionRepository.findById(id).orElseThrow();
        tco001EstablishmentCondition.setGuidanceOperate(condition.getGuidanceOperate());
        tco001EstablishmentCondition.setNumber(condition.getNumber());
        tco001EstablishmentCondition.setGuidanceSpeciality(GuidanceSpeciality.builder().id(condition.getIdGuidanceSpeciality()).build());
        tco001EstablishmentCondition.setAverage(condition.getAverage());
        tco001EstablishmentCondition.setSubjectType(condition.getSubjectType());
        tco001EstablishmentCondition.setSubjectAverage(condition.getSubjectAverage());
        tco001EstablishmentCondition.setSubjectOperate(condition.getSubjectOperate());
        tco001EstablishmentCondition.setShedSetting(ShedSetting.builder().id(condition.getIdShedSetting()).build());
        tco001EstablishmentCondition.setRate(condition.getRate());
        tco001EstablishmentCondition.setLevel(Level.builder().id(condition.getIdLevel()).build());
        tco001EstablishmentCondition.setSubjectAverageMax(condition.getSubjectAverageMax());
        tco001EstablishmentCondition.setAverageMax(condition.getAverageMax());

        tco001EstablishmentCondition.setRateMax(condition.getRateMax());
        tco001EstablishmentCondition.setRateOperate(condition.getRateOperate());
        if (condition.getSubjectType() == TCO001EstablishmentCondition.TCO001EstablishmentConditionSubjectType.one) {
            tco001EstablishmentCondition.setSubject(Subject.builder().id(condition.getIdSubject()).build());
        }
        tco001EstablishmentConditionRepository.save(tco001EstablishmentCondition);
        return true;
    }

    public boolean deleteEstablishmentConditionById(Long id) {
        LOGGER.info(Messages.START_FUNCTION, "delete TCO001ClasseCondition");
        tco001EstablishmentConditionRepository.deleteById(id);
        return true;
    }

    public Page<TCO001EstablishmentConditionDto> searchEstablishmentCondition(String search, Pageable pageable) {
        LOGGER.info(Messages.START_FUNCTION, "search TCO001ClasseCondition by syndromeDiagnostic");
        return tco001EstablishmentConditionRepository.search(search, pageable).map(condition -> TCO001EstablishmentConditionDto.builder().guidanceOperate(condition.getGuidanceOperate()).number(condition.getNumber()).guidanceSpeciality(condition.getGuidanceSpeciality().getTitle()).shedSetting(condition.getShedSetting().getSyndromeDiagnostic()).average(condition.getAverage()).guidanceOperate(condition.getGuidanceOperate()).rate(condition.getRate()).id(condition.getId()).subjectAverageMax(condition.getSubjectAverageMax()).rateMax(condition.getRateMax()).rateOperate(condition.getRateOperate()).averageMax(condition.getAverageMax()).level(LevelDto.builder().id(condition.getIdLevel()).title(condition.getLevel().getTitle()).type(condition.getLevel().getType()).build()).subjectOperate(condition.getSubjectOperate()).subject(condition.getSubjectType() == TCO001EstablishmentCondition.TCO001EstablishmentConditionSubjectType.one ? condition.getSubject().getTitle() : null).subjectType(condition.getSubjectType()).build());
    }


    // --- CRUD Methods for TCO001GuidanceSpecialityConfig  ---
    public TCO001GuidanceSpecialityConfigDto findGuidanceSpecialityConfigById(Long id) throws Exception {
        LOGGER.info(Messages.START_FUNCTION, "findById TCO001ClasseCondition");
        return tco001GuidanceSpecialityConfigRepository.findById(id).map(guidanceSpecialityConfig -> TCO001GuidanceSpecialityConfigDto.builder().id(guidanceSpecialityConfig.getId()).idGuidanceSpeciality(guidanceSpecialityConfig.getIdGuidanceSpeciality()).idLevel(guidanceSpecialityConfig.getIdLevel()).subjectConfigs(guidanceSpecialityConfig.getSubjectConfigs().stream().map(tco001SubjectConfig -> TCO001SubjectConfigDto.builder().basic(tco001SubjectConfig.getBasic()).idSubject(tco001SubjectConfig.getIdSubject()).coefficient(tco001SubjectConfig.getCoefficient()).build()).toList())

                .build()).orElseThrow(() -> new Exception("Could not find classe Condition"));
    }

    public boolean saveGuidanceSpecialityConfig(TCO001GuidanceSpecialityConfigDto tco001GuidanceSpecialityConfigDto) {
        LOGGER.info(Messages.START_FUNCTION, "save TCO001ClasseCondition");
        TCO001GuidanceSpecialityConfig tco001GuidanceSpecialityConfig = TCO001GuidanceSpecialityConfig.builder().guidanceSpeciality(GuidanceSpeciality.builder().id(tco001GuidanceSpecialityConfigDto.getIdGuidanceSpeciality()).build()).level(Level.builder().id(tco001GuidanceSpecialityConfigDto.getIdLevel()).build()).build();
        if (tco001GuidanceSpecialityConfigDto.getSubjectConfigs() != null) {
            tco001GuidanceSpecialityConfig.setSubjectConfigs(tco001GuidanceSpecialityConfigDto.getSubjectConfigs().stream().map(tco001SubjectConfigDto -> TCO001SubjectConfig.builder()
                    .basic(tco001SubjectConfigDto.getBasic())
                    .guidanceSpecialityConfig(tco001GuidanceSpecialityConfig)
                    .coefficient(tco001SubjectConfigDto.getCoefficient())
                    .subject(Subject.builder().id(tco001SubjectConfigDto.getIdSubject()).build())
                    .build()).collect(Collectors.toList()));
        }
        tco001GuidanceSpecialityConfigRepository.save(tco001GuidanceSpecialityConfig);
        return true;
    }
    @Transactional
    public boolean updateGuidanceSpecialityConfig(Long id, TCO001GuidanceSpecialityConfigDto tco001GuidanceSpecialityConfigDto) {
        LOGGER.info(Messages.START_FUNCTION, "update TCO001ClasseCondition");
        if (!tco001GuidanceSpecialityConfigRepository.existsById(id)) {
            LOGGER.error(Messages.ENTITY_IS_NOT_EXIST, "TCO001ClasseCondition");
            throw new IllegalArgumentException(Messages.ENTITY_IS_NOT_EXIST.replace("{}", "TCO001ClasseCondition"));
        }
        TCO001GuidanceSpecialityConfig tco001GuidanceSpecialityConfig = tco001GuidanceSpecialityConfigRepository.findById(id).orElseThrow();
        tco001GuidanceSpecialityConfig.setGuidanceSpeciality(GuidanceSpeciality.builder().id(tco001GuidanceSpecialityConfigDto.getIdGuidanceSpeciality()).build());
        tco001GuidanceSpecialityConfig.setLevel(Level.builder().id(tco001GuidanceSpecialityConfigDto.getIdLevel()).build());
        tco001GuidanceSpecialityConfig.getSubjectConfigs().clear();
        if (tco001GuidanceSpecialityConfigDto.getSubjectConfigs() != null) {
            tco001GuidanceSpecialityConfig.getSubjectConfigs().addAll(tco001GuidanceSpecialityConfigDto.getSubjectConfigs().stream().map(tco001SubjectConfigDto ->
                            TCO001SubjectConfig.builder()
                                    .basic(tco001SubjectConfigDto.getBasic())
                                    .guidanceSpecialityConfig(tco001GuidanceSpecialityConfig)  // Make sure to keep this reference correct
                                    .coefficient(tco001SubjectConfigDto.getCoefficient())
                                    .subject(Subject.builder().id(tco001SubjectConfigDto.getIdSubject()).build())
                                    .build())
                    .collect(Collectors.toList()));
        }

        tco001GuidanceSpecialityConfigRepository.save(tco001GuidanceSpecialityConfig);
        return true;
    }

    public boolean deleteGuidanceSpecialityConfigById(Long id) {
        LOGGER.info(Messages.START_FUNCTION, "delete TCO001ClasseCondition");
        tco001GuidanceSpecialityConfigRepository.deleteById(id);
        return true;
    }

    public Page<TCO001GuidanceSpecialityConfigDto> searchGuidanceSpecialityConfig(String search, Pageable pageable) {
        LOGGER.info(Messages.START_FUNCTION, "search TCO001ClasseCondition by syndromeDiagnostic");
        return tco001GuidanceSpecialityConfigRepository.search(search, pageable).map(tco001GuidanceSpecialityConfig -> TCO001GuidanceSpecialityConfigDto.builder().level(LevelDto.builder().title(tco001GuidanceSpecialityConfig.getLevel().getTitle()).type(tco001GuidanceSpecialityConfig.getLevel().getType()).build()).guidanceSpeciality(tco001GuidanceSpecialityConfig.getGuidanceSpeciality().getTitle()).id(tco001GuidanceSpecialityConfig.getId()).build());
    }
}
