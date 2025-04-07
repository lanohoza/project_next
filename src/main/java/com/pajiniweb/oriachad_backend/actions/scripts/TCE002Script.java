package com.pajiniweb.oriachad_backend.actions.scripts;

import com.pajiniweb.oriachad_backend.actions.domains.dtos.TCE002.*;
import com.pajiniweb.oriachad_backend.actions.domains.entities.Script;
import com.pajiniweb.oriachad_backend.actions.domains.entities.UserScript;
import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCE002.*;
import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCE002.projections.CategoryCountDto;
import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCE002.projections.SubjectAverageDto;
import com.pajiniweb.oriachad_backend.actions.helps.Functions;
import com.pajiniweb.oriachad_backend.actions.repositories.ScriptRepository;
import com.pajiniweb.oriachad_backend.actions.repositories.UserScriptRepository;
import com.pajiniweb.oriachad_backend.actions.repositories.scripts.TCE002.TCE002AverageRepository;
import com.pajiniweb.oriachad_backend.actions.repositories.scripts.TCE002.TCE002ConditionRepository;
import com.pajiniweb.oriachad_backend.actions.repositories.scripts.TCE002.TCE002DiagnosticRepository;
import com.pajiniweb.oriachad_backend.actions.repositories.scripts.TCE002.TCE002ResultRepository;
import com.pajiniweb.oriachad_backend.actions.scripts.core.ScriptImplementation;
import com.pajiniweb.oriachad_backend.actions.scripts.core.TCScriptService;
import com.pajiniweb.oriachad_backend.domains.entities.*;
import com.pajiniweb.oriachad_backend.domains.enums.TypeEstablishment;
import com.pajiniweb.oriachad_backend.repositories.ProfessorRepository;
import com.pajiniweb.oriachad_backend.repositories.SpecialityRepository;
import com.pajiniweb.oriachad_backend.repositories.SubjectRepository;
import com.pajiniweb.oriachad_backend.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class TCE002Script extends ScriptImplementation {

    @Autowired
    private TCE002ConditionRepository conditionRepository;
    @Autowired
    private HelperService helperService;
    @Autowired
    private YearService yearService;
    @Autowired
    private LevelService levelService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private ClasseService classeService;
    @Autowired
    private NoteService noteService;
    @Autowired
    private TCE002ResultRepository resultRepository;
    @Autowired
    private TCE002AverageRepository averageRepository;
    @Autowired
    private TCE002DiagnosticRepository diagnosticRepository;
    @Autowired
    private UserScriptRepository userScriptRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private ProfessorRepository professorRepository;
    @Autowired
    private ScriptRepository scriptRepository;
    @Autowired
    private SpecialityRepository specialityRepository;
    Map<TCE002Condition, List<Student>> groups = new HashMap<>();
    @Autowired
    private GuidanceGroupService guidanceGroupService;

    @Autowired
    public TCE002Script(TCScriptService tcScriptService) {
        super(tcScriptService);

    }

    @Transactional
    @Override
    public boolean run(Object[] args) throws Exception {
        Establishment establishment = helperService.getCurrentEstablishment();

        Script script = scriptRepository.findByCode(this.getCode()).orElseThrow();
        Year currentYear = helperService.getCurrentYear();
        OriachadUser user = helperService.getCurrentUser().getOriachadUser();
        Year previousYear = yearService.getYearByOrder(currentYear.getOrder() - 1);
        if (previousYear == null) throw new Exception("previousYear type is null");

        List<Level> levels = null;

        if (establishment.getType() == TypeEstablishment.secondary) {
            levels = levelService.getByTypeEstablishmentAndNumbers(establishment.getType(), new int[]{2, 3});
        }else{
            levels =   levelService.getByTypeEstablishmentAndNumbers(establishment.getType(), new int[]{2,3,4});
        }

        List<TCE002Condition> studentConditions = conditionRepository.findByTarget(DiagnosticType.student);
        List<TCE002Condition> classeConditions = conditionRepository.findByTarget(DiagnosticType.classe);
        List<TCE002Condition> levelConditions = conditionRepository.findByTarget(DiagnosticType.level);
        List<TCE002Condition> subjectConditions = conditionRepository.findByTarget(DiagnosticType.subject);
        List<TCE002Condition> professorConditions = conditionRepository.findByTarget(DiagnosticType.professor);
        List<TCE002Condition> specialityConditions = conditionRepository.findByTarget(DiagnosticType.speciality);
        studentConditions.stream().filter(condition -> condition.getShedSetting().getHasGroup()).forEach(condition -> groups.put(condition, new ArrayList<>()));

        clearPreviousData(currentYear, user);
        processLevels(levels, establishment, currentYear, previousYear, user, studentConditions, classeConditions, levelConditions, specialityConditions);
        processSubjects(currentYear, user, establishment, subjectConditions);
        processProfessors(currentYear, user, establishment, professorConditions);
        groups.entrySet().forEach(condition -> {
            if (condition.getValue().size() > 0)
                guidanceGroupService.saveGroups(condition.getKey().getShedSetting().getGroupName(), condition.getValue());

        });

        userScriptRepository.delete(user.getId(), currentYear.getId(), script.getId());
        userScriptRepository.save(UserScript.builder().script(script).user(user).year(currentYear).build());
        return true;
    }

    private void processSpecialties(Year currentYear, OriachadUser currentUser, Level level, List<TCE002Condition> specialityConditions) {

        List<Speciality> specialities = specialityRepository.getAllByIdLevel(level.getId());
        specialities.forEach(speciality -> {
            specialityConditions.stream().filter(tce002Condition -> tce002Condition.getIdSpeciality().equals(speciality.getId())).forEach(condition -> {
                Double averageValue = resultRepository.getAverageSpeciality(speciality.getId(), currentYear.getId(), currentYear.getId(), condition.getSubjects().stream().map(Subject::getId).toList());
                if (averageValue != null)
                    if (Functions.applyOperate(condition.getOperate(), condition.getAverage(), condition.getAverageMax(), averageValue)) {
                        diagnosticRepository.save(TCE002Diagnostic.builder().type(DiagnosticType.speciality).speciality(speciality).level(level).createdBy(currentUser).shedSetting(condition.getShedSetting()).year(currentYear).build());
                    }
            });
        });
    }

    private void processProfessors(Year currentYear, OriachadUser currentUser, Establishment establishment, List<TCE002Condition> professorConditions) {
        List<Professor> professors = professorRepository.findAllActive(establishment.getId(), currentYear.getId());
        professors.forEach(professor -> {
            Double averageValue = resultRepository.getAverageSubject(currentYear.getId(), currentYear.getId(), professor.getIdSubject());
            professorConditions.forEach(condition -> {
                if (Functions.applyOperate(condition.getOperate(), condition.getAverage(), condition.getAverageMax(), averageValue)) {
                    diagnosticRepository.save(TCE002Diagnostic.builder().type(DiagnosticType.professor).professor(professor).createdBy(currentUser).shedSetting(condition.getShedSetting()).year(currentYear).build());
                }
            });
        });
    }

    private void processSubjects(Year currentYear, OriachadUser currentUser, Establishment establishment, List<TCE002Condition> subjectConditions) {

        List<Subject> subjects = subjectRepository.findAllByType(establishment.getType());
        subjects.forEach(subject -> {
            Double averageValue = resultRepository.getAverageSubject(currentYear.getId(), currentYear.getId(), subject.getId());
            subjectConditions.forEach(condition -> {
                if (Functions.applyOperate(condition.getOperate(), condition.getAverage(), condition.getAverageMax(), averageValue)) {
                    diagnosticRepository.save(TCE002Diagnostic.builder().type(DiagnosticType.subject).subject(subject).createdBy(currentUser).shedSetting(condition.getShedSetting()).year(currentYear).build());
                }
            });
        });
    }

    private void clearPreviousData(Year currentYear, OriachadUser currentUser) {
        resultRepository.deleteBys(currentYear.getId(), currentUser.getId());
        averageRepository.deleteBys(currentYear.getId(), currentUser.getId());
        diagnosticRepository.deleteBys(currentYear.getId(), currentUser.getId());
    }

    private void processLevels(List<Level> levels, Establishment establishment, Year currentYear, Year previousYear, OriachadUser currentUser, List<TCE002Condition> studentConditions, List<TCE002Condition> classeConditions, List<TCE002Condition> levelConditions, List<TCE002Condition> specialityConditions) {
        levels.forEach(level -> {
            processClasses(level, currentYear, previousYear, currentUser, studentConditions, classeConditions);
            if (establishment.getType() == TypeEstablishment.secondary)
                processSpecialties(currentYear, currentUser, level, specialityConditions);
            processLevelConditions(level, currentYear, currentUser, levelConditions);
        });


    }

    private void processClasses(Level level, Year currentYear, Year previousYear, OriachadUser currentUser, List<TCE002Condition> studentConditions, List<TCE002Condition> classeConditions) {
        classeService.getAllByIdYearAndIdEstablishmentAndLevel(currentYear.getId(), helperService.getCurrentEstablishment().getId(), level.getId()).forEach(classe -> {
            List<Student> students = studentService.getAllStudentByClassId(classe.getId());
            students.forEach(student -> processStudent(student, classe, level, currentYear, previousYear, currentUser, studentConditions));
            processClasseConditions(classe, currentYear, currentUser, classeConditions);
        });
    }

    private void processStudent(Student student, Classe classe, Level level, Year currentYear, Year previousYear, OriachadUser currentUser, List<TCE002Condition> studentConditions) {
        List<TCE002Result> results = noteService.getAllNoteByIdStudentAndIdYear(student.getId(), previousYear.getId()).stream().map(note -> TCE002Result.builder().subject(note.getSubject()).idSubject(note.getSubject().getId()).createdBy(currentUser).year(currentYear).classe(classe).level(level).value(note.getSumValue()).student(student).build()).collect(Collectors.toList());

        TCE002Average average = TCE002Average.builder().student(student).value(noteService.getAllAverageByIdStudentAndIdYear(student.getId(), previousYear.getId()).orElse(0.0)).createdBy(currentUser).year(currentYear).level(level).classe(classe).build();

        resultRepository.saveAll(results);
        averageRepository.save(average);

        studentConditions.forEach(condition -> {
            Double averageValue = resultRepository.getAverageSubjectStudent(student.getId(), currentYear.getId(), currentYear.getId(), condition.getSubjects().stream().map(Subject::getId).toList());
            if (averageValue != null)
                if (Functions.applyOperate(condition.getOperate(), condition.getAverage(), condition.getAverageMax(), averageValue)) {
                    if (condition.getShedSetting().getHasGroup()) {
                        groups.get(condition).add(student);
                    }
                    diagnosticRepository.save(TCE002Diagnostic.builder().type(DiagnosticType.student).student(student).createdBy(currentUser).shedSetting(condition.getShedSetting()).year(currentYear).build());
                }
        });
    }

    private void processClasseConditions(Classe classe, Year currentYear, OriachadUser currentUser, List<TCE002Condition> classeConditions) {
        classeConditions.forEach(condition -> {
            Double averageValue = resultRepository.getAverageSubjectClasse(classe.getId(), currentYear.getId(), currentYear.getId(), condition.getSubjects().stream().map(Subject::getId).toList());
            if (averageValue != null)
                if (Functions.applyOperate(condition.getOperate(), condition.getAverage(), condition.getAverageMax(), averageValue)) {
                    diagnosticRepository.save(TCE002Diagnostic.builder().type(DiagnosticType.classe).classe(classe).createdBy(currentUser).shedSetting(condition.getShedSetting()).year(currentYear).build());
                }
        });
    }

    private void processLevelConditions(Level level, Year currentYear, OriachadUser currentUser, List<TCE002Condition> levelConditions) {
        levelConditions.forEach(condition -> {
            Double averageValue = resultRepository.getAverageSubjectLevel(level.getId(), currentYear.getId(), currentYear.getId(), condition.getSubjects().stream().map(Subject::getId).toList());
            if (averageValue != null)
                if (Functions.applyOperate(condition.getOperate(), condition.getAverage(), condition.getAverageMax(), averageValue)) {
                    diagnosticRepository.save(TCE002Diagnostic.builder().type(DiagnosticType.classe).level(level).createdBy(currentUser).shedSetting(condition.getShedSetting()).year(currentYear).build());
                }
        });
    }


    @Override

    public Object getStudentCardData(Long idStudent, Long idYear) {
        OriachadUser currentUser = helperService.getCurrentUser().getOriachadUser();

        Map<String, Double> subjectAverages = resultRepository.findBys(idStudent, idYear, currentUser.getId()).stream().collect(Collectors.toMap(result -> result.getSubject().getTitle(), result -> result.getValue()));
        List<String> diagnostics = diagnosticRepository.findByStudent(idStudent, idYear, currentUser.getId());
        return TCEM002StudentCardDto.builder().subjectAverages(subjectAverages).diagnostics(diagnostics).build();
    }

    @Override

    public TCE002ClasseCardDto getClassCardData(Long idClass, Long idYear) {
        OriachadUser currentUser = helperService.getCurrentUser().getOriachadUser();

        List<SubjectAverageDto> subjectAverages = resultRepository.findSubjectAveragesClassWithCategories(idClass, idYear, currentUser.getId());
        List<SubjectAverageDto> overallAverage = averageRepository.findOverallAverageClassWithCategory(idClass, idYear, currentUser.getId());
        //subjectAverages.addAll(overallAverage);

        List<TCE002SubjectAverageCategoryDto> subjectAverageCategoryDtos = subjectAverages.stream().collect(Collectors.groupingBy(SubjectAverageDto::getSubjectTitle)).entrySet().stream().map(entry -> {
            TCE002SubjectAverageCategoryDto subjectDetails = new TCE002SubjectAverageCategoryDto(entry.getKey());
            entry.getValue().forEach(dto -> {
                subjectDetails.setSubjectAverage(subjectDetails.getSubjectAverage() + dto.getAverageValue());
                addCategoryCount(subjectDetails, dto.getGradeCategory());
            });
            subjectDetails.setSubjectAverage(subjectDetails.getSubjectAverage() / entry.getValue().size());
            return subjectDetails;
        }).collect(Collectors.toList());
        List<String> diagnostics = diagnosticRepository.findByClasse(idClass, idYear, currentUser.getId());
        //	subjectAverageCategoryDtos.add(TCE002SubjectAverageCategoryDto.builder()..build())
        TCE002ClasseCardDto tce002ClasseCardDto = TCE002ClasseCardDto.builder().subjectAverageCategories(subjectAverageCategoryDtos).diagnostics(diagnostics).build();
        return tce002ClasseCardDto;
    }

    private void addCategoryCount(TCE002SubjectAverageCategoryDto dto, String category) {
        switch (category) {
            case "category_18_20" -> dto.setCategory_18_20(dto.getCategory_18_20() + 1);
            case "category_16_18" -> dto.setCategory_16_18(dto.getCategory_16_18() + 1);
            case "category_14_16" -> dto.setCategory_14_16(dto.getCategory_14_16() + 1);
            case "category_12_14" -> dto.setCategory_12_14(dto.getCategory_12_14() + 1);
            case "category_10_12" -> dto.setCategory_10_12(dto.getCategory_10_12() + 1);
            case "category_greater_10" -> dto.setCategory_greater_10(dto.getCategory_greater_10() + 1);
            case "category_less_10" -> dto.setCategory_less_10(dto.getCategory_less_10() + 1);
        }
    }

    @Override

    public Object getLevelCardData(Long idLevel, Long idYear) {


        OriachadUser currentUser = helperService.getCurrentUser().getOriachadUser();

        List<SubjectAverageDto> subjectAverages = resultRepository.findSubjectAveragesLevelWithCategories(idLevel, idYear, currentUser.getId());
        List<SubjectAverageDto> overallAverage = averageRepository.findOverallAverageLevelWithCategory(idLevel, idYear, currentUser.getId());

        List<TCE002SubjectAverageCategoryDto> overallAverageCategoryDtos = subjectAverages.stream().collect(Collectors.groupingBy(SubjectAverageDto::getSubjectTitle)).entrySet().stream().map(entry -> {
            TCE002SubjectAverageCategoryDto subjectDetails = new TCE002SubjectAverageCategoryDto(entry.getKey());
            entry.getValue().forEach(dto -> {
                subjectDetails.setSubjectAverage(subjectDetails.getSubjectAverage() + dto.getAverageValue());
                addCategoryCount(subjectDetails, dto.getGradeCategory());
            });
            subjectDetails.setSubjectAverage(subjectDetails.getSubjectAverage() / entry.getValue().size());
            return subjectDetails;
        }).collect(Collectors.toList());
        List<TCE002SubjectAverageCategoryDto> subjectAverageCategoryDtos = subjectAverages.stream().collect(Collectors.groupingBy(SubjectAverageDto::getSubjectTitle)).entrySet().stream().map(entry -> {
            TCE002SubjectAverageCategoryDto subjectDetails = new TCE002SubjectAverageCategoryDto(entry.getKey());
            entry.getValue().forEach(dto -> {
                subjectDetails.setSubjectAverage(subjectDetails.getSubjectAverage() + dto.getAverageValue());
                addCategoryCount(subjectDetails, dto.getGradeCategory());
            });
            subjectDetails.setSubjectAverage(subjectDetails.getSubjectAverage() / entry.getValue().size());
            return subjectDetails;
        }).collect(Collectors.toList());
        List<String> diagnostics = diagnosticRepository.findByLevel(idLevel, idYear, currentUser.getId());

        TCE002LevelCardDto tce002ClasseCardDto = TCE002LevelCardDto.builder().subjectAverageCategories(subjectAverageCategoryDtos).diagnostics(diagnostics).build();
        return tce002ClasseCardDto;
    }

    @Override
    public TCEM002SubjectCardDto getSubjectCardData(Long idSubject, Long idYear) {


        OriachadUser currentUser = helperService.getCurrentUser().getOriachadUser();

        Double averageValue = resultRepository.getAverageSubject(idYear, currentUser.getId(), idSubject);
        List<CategoryCountDto> categoryCountDtos = resultRepository.findSubjectCategoryCounts(idSubject, idYear, currentUser.getId());
        List<String> diagnostics = diagnosticRepository.findBySubject(idSubject, idYear, currentUser.getId());

        return TCEM002SubjectCardDto.builder().diagnostics(diagnostics).average(averageValue).averageCategoryCounts(categoryCountDtos).build();
    }

    @Override
    public Object getSpecialityCardData(Long idSpecialty, Long idLevel, Long idYear) {

        OriachadUser currentUser = helperService.getCurrentUser().getOriachadUser();

        List<SubjectAverageDto> subjectAverages = resultRepository.findSubjectAveragesSpecialityWithCategories(idSpecialty, idLevel, idYear, currentUser.getId());
        List<SubjectAverageDto> overallAverage = averageRepository.findOverallAverageSpecialityWithCategory(idSpecialty, idLevel, idYear, currentUser.getId());

        List<TCE002SubjectAverageCategoryDto> overallAverageCategoryDtos = subjectAverages.stream().collect(Collectors.groupingBy(SubjectAverageDto::getSubjectTitle)).entrySet().stream().map(entry -> {
            TCE002SubjectAverageCategoryDto subjectDetails = new TCE002SubjectAverageCategoryDto(entry.getKey());
            entry.getValue().forEach(dto -> {
                subjectDetails.setSubjectAverage(subjectDetails.getSubjectAverage() + dto.getAverageValue());
                addCategoryCount(subjectDetails, dto.getGradeCategory());
            });
            subjectDetails.setSubjectAverage(subjectDetails.getSubjectAverage() / entry.getValue().size());
            return subjectDetails;
        }).collect(Collectors.toList());
        List<TCE002SubjectAverageCategoryDto> subjectAverageCategoryDtos = subjectAverages.stream().collect(Collectors.groupingBy(SubjectAverageDto::getSubjectTitle)).entrySet().stream().map(entry -> {
            TCE002SubjectAverageCategoryDto subjectDetails = new TCE002SubjectAverageCategoryDto(entry.getKey());
            entry.getValue().forEach(dto -> {
                subjectDetails.setSubjectAverage(subjectDetails.getSubjectAverage() + dto.getAverageValue());
                addCategoryCount(subjectDetails, dto.getGradeCategory());
            });
            subjectDetails.setSubjectAverage(subjectDetails.getSubjectAverage() / entry.getValue().size());
            return subjectDetails;
        }).collect(Collectors.toList());
        List<String> diagnostics = diagnosticRepository.findBySpeciality(idLevel, idLevel, idYear, currentUser.getId());

        TCE002SpecialityCardDto tce002SpecialityCardDto = TCE002SpecialityCardDto.builder().subjectAverageCategories(subjectAverageCategoryDtos).diagnostics(diagnostics).build();
        return tce002SpecialityCardDto;
    }

    @Override
    public Object getEstablishmentCardData(Long idEstablishment, Long idYear) {
        OriachadUser currentUser = helperService.getCurrentUser().getOriachadUser();

        List<SubjectAverageDto> subjectAverages = resultRepository.findSubjectAveragesWithCategories(idYear, currentUser.getId());
        List<SubjectAverageDto> overallAverage = averageRepository.findOverallAverageWithCategory(idYear, currentUser.getId());

        List<TCE002SubjectAverageCategoryDto> overallAverageCategoryDtos = subjectAverages.stream().collect(Collectors.groupingBy(SubjectAverageDto::getSubjectTitle)).entrySet().stream().map(entry -> {
            TCE002SubjectAverageCategoryDto subjectDetails = new TCE002SubjectAverageCategoryDto(entry.getKey());
            entry.getValue().forEach(dto -> {
                subjectDetails.setSubjectAverage(subjectDetails.getSubjectAverage() + dto.getAverageValue());
                addCategoryCount(subjectDetails, dto.getGradeCategory());
            });
            subjectDetails.setSubjectAverage(subjectDetails.getSubjectAverage() / entry.getValue().size());
            return subjectDetails;
        }).collect(Collectors.toList());
        List<TCE002SubjectAverageCategoryDto> subjectAverageCategoryDtos = subjectAverages.stream().collect(Collectors.groupingBy(SubjectAverageDto::getSubjectTitle)).entrySet().stream().map(entry -> {
            TCE002SubjectAverageCategoryDto subjectDetails = new TCE002SubjectAverageCategoryDto(entry.getKey());
            entry.getValue().forEach(dto -> {
                subjectDetails.setSubjectAverage(subjectDetails.getSubjectAverage() + dto.getAverageValue());
                addCategoryCount(subjectDetails, dto.getGradeCategory());
            });
            subjectDetails.setSubjectAverage(subjectDetails.getSubjectAverage() / entry.getValue().size());
            return subjectDetails;
        }).collect(Collectors.toList());
        List<String> diagnostics = new ArrayList<>(); //diagnosticRepository.findByLevel(idLevel, idYear, currentUser.getId());

        TCE002LevelCardDto tce002ClasseCardDto = TCE002LevelCardDto.builder().subjectAverageCategories(subjectAverageCategoryDtos).diagnostics(diagnostics).build();
        return tce002ClasseCardDto;
    }

    public Object getGuardianCardData(Long idStudent, Long idYear) {
        return null;
    }

    @Override
    public Boolean hasStudentCardData() {
        return true;
    }

    @Override
    public Boolean hasClassCardData() {
        return true;
    }

    @Override
    public Boolean hasLevelCardData() {
        return true;
    }

    @Override
    public Boolean hasEstablishmentCardData() {
        return true;
    }

    @Override
    public Boolean hasGuardianCardData() {
        return false;
    }

    @Override
    public Boolean hasSpecialtyCardData() {
        Establishment establishment = helperService.getCurrentEstablishment();
        return establishment.getType() == TypeEstablishment.secondary;
    }

    @Override
    public Boolean hasSubjectCardData() {
        return true;
    }
}