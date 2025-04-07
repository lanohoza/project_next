package com.pajiniweb.oriachad_backend.actions.scripts;

import com.pajiniweb.oriachad_backend.actions.domains.dtos.TCO001.*;
import com.pajiniweb.oriachad_backend.actions.domains.entities.Script;
import com.pajiniweb.oriachad_backend.actions.domains.entities.UserScript;
import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCE002.DiagnosticType;
import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCO001.conditions.*;
import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCO001.data.TCO001GuidanceSpecialityAverage;
import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCO001.data.TCO001GuidanceSpecialityConfig;
import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCO001.data.TCO001SubjectAverage;
import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCO001.projections.TCO001StudentGuidanceSpecialityAverage;
import com.pajiniweb.oriachad_backend.actions.helps.Functions;
import com.pajiniweb.oriachad_backend.actions.repositories.ScriptRepository;
import com.pajiniweb.oriachad_backend.actions.repositories.UserScriptRepository;
import com.pajiniweb.oriachad_backend.actions.repositories.scripts.TCO001.condtions.*;
import com.pajiniweb.oriachad_backend.actions.repositories.scripts.TCO001.data.TCO001GuidanceSpecialityAverageRepository;
import com.pajiniweb.oriachad_backend.actions.repositories.scripts.TCO001.data.TCO001GuidanceSpecialityConfigRepository;
import com.pajiniweb.oriachad_backend.actions.repositories.scripts.TCO001.data.TCO001SubjectAverageRepository;
import com.pajiniweb.oriachad_backend.actions.scripts.core.ScriptImplementation;
import com.pajiniweb.oriachad_backend.actions.scripts.core.TCScriptService;
import com.pajiniweb.oriachad_backend.domains.entities.*;
import com.pajiniweb.oriachad_backend.domains.enums.TypeEstablishment;
import com.pajiniweb.oriachad_backend.repositories.*;
import com.pajiniweb.oriachad_backend.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class TCO001Script extends ScriptImplementation {

    @Autowired
    private HelperService helperService;

    @Autowired
    private ScriptRepository scriptRepository;
    @Autowired
    private UserScriptRepository userScriptRepository;
    @Autowired
    private NoteService noteService;

    @Autowired
    private DesireSpecialtyRepository desireSpecialtyRepository;
    @Autowired
    private LevelService levelService;
    @Autowired
    private ClasseService classeService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TCO001SubjectAverageRepository tco001SubjectAverageRepository;
    @Autowired
    private TCO001StudentConditionRepository tco001StudentConditionRepository;
    @Autowired
    private TCO001GuidanceSpecialityConfigRepository tco001GuidanceSpecialityConfigRepository;
    @Autowired
    private TCO001GuidanceSpecialityAverageRepository tCO001GuidanceSpecialityAverageRepository;
    @Autowired
    private TCO001DiagnosticRepository diagnosticRepository;
    @Autowired
    private TCO001ClasseConditionRepository tCO001ClasseConditionRepository;
    @Autowired
    private TCO001LevelConditionRepository tCO001LevelConditionRepository;
    @Autowired
    private SpecialityRepository specialityRepository;
    @Autowired
    private TCO001SpecialityConditionRepository tCO001SpecialityConditionRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private TCO001SubjectConditionRepository tCO001SubjectConditionRepository;
    @Autowired
    private TCO001EstablishmentConditionRepository tCO001EstablishmentConditionRepository;
    @Autowired
    private GuidanceGroupService guidanceGroupService;
    final Integer trimesetNumber = 1;


    public TCO001Script(TCScriptService tcScriptService) {
        super(tcScriptService);

    }

    @Override
    @Transactional
    public boolean run(Object[] args) throws Exception {
        Establishment establishment = helperService.getCurrentEstablishment();
        Script script = scriptRepository.findByCode(this.getCode()).orElseThrow();
        Year currentYear = helperService.getCurrentYear();
        OriachadUser user = helperService.getCurrentUser().getOriachadUser();

        diagnosticRepository.deleteBys(currentYear.getId(), user.getId());
        tco001SubjectAverageRepository.deleteBys(currentYear.getId(), user.getId());
        tCO001GuidanceSpecialityAverageRepository.deleteBys(currentYear.getId(), user.getId());

        // fetch the all types of conditions
        List<TCO001StudentCondition> studentConditions = tco001StudentConditionRepository.findAll();
        List<TCO001ClasseCondition> classeConditions = tCO001ClasseConditionRepository.findAll();
        List<TCO001LevelCondition> levelConditions = tCO001LevelConditionRepository.findAll();
        List<TCO001SpecialityCondition> specialityConditions = tCO001SpecialityConditionRepository.findAll();
        List<TCO001SubjectCondition> subjectConditions = tCO001SubjectConditionRepository.findAll();
        List<TCO001EstablishmentCondition> establishmentConditions = tCO001EstablishmentConditionRepository.findAll();

        Map<Classe, Map<TCO001ClasseCondition, List<Boolean>>> classeBooleans = new HashMap<>();
        Map<Level, Map<TCO001LevelCondition, List<Boolean>>> levelBooleans = new HashMap<>();
        Map<Level, Map<Speciality, Map<TCO001SpecialityCondition, List<Boolean>>>> specialityBooleans = new HashMap<>();
        Map<Subject, Map<TCO001SubjectCondition, List<Boolean>>> subjectBooleans = new HashMap<>();
        Map<TCO001EstablishmentCondition, List<Boolean>> establishmentBooleans = new HashMap<>();
        List<Level> levels = null;
        if (establishment.getType() == TypeEstablishment.secondary) {
            levels = levelService.getByTypeEstablishmentAndNumbers(establishment.getType(), new int[]{1});
        } else {
            levels = levelService.getByTypeEstablishmentAndNumbers(establishment.getType(), new int[]{4});
        }
        List<Subject> subjects = subjectRepository.findAllByType(establishment.getType());

        subjects.forEach(subject -> {
            subjectBooleans.put(subject, new HashMap<>());
            subjectConditions.forEach(condition -> subjectBooleans.get(subject).put(condition, new ArrayList<>()));
        });

        Map<TCO001StudentCondition, List<Student>> groups = new HashMap<>();
        studentConditions.stream().filter(condition -> condition.getShedSetting().getHasGroup()).forEach(condition -> groups.put(condition, new ArrayList<>()));

        levels.forEach(level -> {
            var guidanceSpecialityConfigs = tco001GuidanceSpecialityConfigRepository.findAllByIdLevel(level.getId());
            levelBooleans.put(level, new HashMap<>());
            levelConditions.forEach(condition -> levelBooleans.get(level).put(condition, new ArrayList<>()));

            specialityBooleans.put(level, new HashMap<>());
            List<Speciality> specialities = specialityRepository.getAllByIdLevel(level.getId());
            specialities.forEach(speciality -> {
                specialityBooleans.get(level).put(speciality, new HashMap<>());
                specialityConditions.forEach(condition -> specialityBooleans.get(level).get(speciality).put(condition, new ArrayList<>()));
            });

            classeService.getAllByIdYearAndIdEstablishmentAndLevel(currentYear.getId(), establishment.getId(), level.getId()).forEach(classe -> {
                List<Student> students = studentService.getAllStudentByClassId(classe.getId());
                classeBooleans.put(classe, new HashMap<>());
                classeConditions.forEach(condition -> classeBooleans.get(classe).put(condition, new ArrayList<>()));

                for (Student student : students) {
                    List<DesireSpecialty> desireSpecialties = desireSpecialtyRepository.findAllByStudent(currentYear.getId(), user.getId(), student.getId());
                    if (desireSpecialties.size() > 0) {


                        guidanceSpecialityConfigs.forEach(tco001GuidanceSpecialityConfig -> {
                            DesireSpecialty desireSpecialty = desireSpecialties.stream().filter(desireSpecialty1 -> desireSpecialty1.getIdGuidanceSpeciality() == tco001GuidanceSpecialityConfig.getIdGuidanceSpeciality()).findAny().orElse(null);
                            processGuidanceSpecialityConfig(tco001GuidanceSpecialityConfig, desireSpecialty, student, classe, level, user, currentYear);
                        });

                        // التحقق من شروط الطلاب
                        studentConditions.forEach(condition -> {

                            if (applyStudentConditions(condition, student, currentYear, user)) {
                                if (condition.getShedSetting().getHasGroup()) {
                                    groups.get(condition).add(student);
                                }
                                saveStudentDiagnostic(student, condition, currentYear, user);
                            }
                        });
                        classeConditions.forEach(condition -> {
                            classeBooleans.get(classe).get(condition).add(applyClassConditions(condition, student, currentYear, user));
                        });
                        levelConditions.forEach(condition -> {
                            levelBooleans.get(level).get(condition).add(applyLevelConditions(condition, student, currentYear, user));

                        });
                        establishmentConditions.forEach(condition -> {
                            if (desireSpecialtyRepository.isOrder(currentYear.getId(), user.getId(), student.getId(), condition.getIdGuidanceSpeciality(), condition.getNumber()))
                                establishmentBooleans.get(condition).add(applyEstablishmentConditions(condition, student, currentYear, user));

                        });
                        if (establishment.getType() != TypeEstablishment.secondary) {
                            specialities.forEach(speciality -> {
                                specialityConditions.stream().forEach(condition -> {
                                    if (desireSpecialtyRepository.isOrder(currentYear.getId(), user.getId(), student.getId(), condition.getIdGuidanceSpeciality(), condition.getNumber()))
                                        specialityBooleans.get(level).get(speciality).get(condition).add(applySpecialityConditions(condition, student, currentYear, user));

                                });
                            });

                        }
                        subjects.forEach(subject -> {
                            subjectConditions.stream().filter(tco001SubjectCondition -> tco001SubjectCondition.getIdSubject() == subject.getId()).forEach(condition -> {
                                if (desireSpecialtyRepository.isOrder(currentYear.getId(), user.getId(), student.getId(), condition.getIdGuidanceSpeciality(), condition.getNumber()))
                                    subjectBooleans.get(subject).get(condition).add(applySubjectConditions(condition, subject, student, currentYear, user));
                            });
                        });
                    }
                }


            });

        });
        classeBooleans.entrySet().forEach(subjectBoolean -> {
            var classe = subjectBoolean.getKey();
            subjectBoolean.getValue().entrySet().forEach(tco001ClasseConditionListEntry -> {
                var condition = tco001ClasseConditionListEntry.getKey();
                var booleans = tco001ClasseConditionListEntry.getValue();
                long trueCount = tco001ClasseConditionListEntry.getValue().stream().filter(Boolean::booleanValue).count();
                long count = booleans.size();
                count = count == 0 ? 1 : count;
                if (Functions.applyOperate(condition.getRateOperate(), condition.getRate(), condition.getRateMax(), (double) ((trueCount * 100) / count))) {
                    saveClassDiagnostic(classe, condition, currentYear, user);

                }
            });
        });
        levelBooleans.entrySet().forEach(levelEntrySet -> {
            var level = levelEntrySet.getKey();
            levelEntrySet.getValue().entrySet().forEach(tco001ClasseConditionListEntry -> {
                var condition = tco001ClasseConditionListEntry.getKey();
                var booleans = tco001ClasseConditionListEntry.getValue();
                long trueCount = tco001ClasseConditionListEntry.getValue().stream().filter(Boolean::booleanValue).count();
                long count = booleans.size();
                count = count == 0 ? 1 : count;
                if (Functions.applyOperate(condition.getRateOperate(), condition.getRate(), condition.getRateMax(), (double) ((trueCount * 100) / count))) {
                    saveLevelDiagnostic(level, condition, currentYear, user);

                }
            });
        });
        specialityBooleans.entrySet().forEach(levelEntrySet -> {
            var level = levelEntrySet.getKey();
            levelEntrySet.getValue().entrySet().forEach(subjectBoolean -> {
                var speciality = subjectBoolean.getKey();
                subjectBoolean.getValue().entrySet().forEach(tco001ClasseConditionListEntry -> {
                    var condition = tco001ClasseConditionListEntry.getKey();
                    var booleans = tco001ClasseConditionListEntry.getValue();
                    long trueCount = tco001ClasseConditionListEntry.getValue().stream().filter(Boolean::booleanValue).count();
                    long count = booleans.size();
                    count = count == 0 ? 1 : count;
                    if (Functions.applyOperate(condition.getGuidanceOperate(), condition.getRate(), condition.getRateMax(), (double) ((trueCount * 100) / count))) {
                        saveSpecialityDiagnostic(speciality, level, condition, currentYear, user);

                    }
                });
            });
        });

        subjectBooleans.entrySet().forEach(levelEntrySet -> {
            var subject = levelEntrySet.getKey();
            levelEntrySet.getValue().entrySet().forEach(tco001SubjectConditionListEntry -> {
                var condition = tco001SubjectConditionListEntry.getKey();
                var booleans = tco001SubjectConditionListEntry.getValue();
                long trueCount = tco001SubjectConditionListEntry.getValue().stream().filter(Boolean::booleanValue).count();
                long count = booleans.size();
                count = count == 0 ? 1 : count;
                if (Functions.applyOperate(condition.getRateOperate(), condition.getRate(), condition.getRateMax(), (double) ((trueCount * 100) / count))) {
                    saveSubjectDiagnostic(subject, condition, currentYear, user);

                }
            });
        });

        establishmentBooleans.entrySet().forEach(tco001ClasseConditionListEntry -> {
            var condition = tco001ClasseConditionListEntry.getKey();
            var booleans = tco001ClasseConditionListEntry.getValue();
            long trueCount = tco001ClasseConditionListEntry.getValue().stream().filter(Boolean::booleanValue).count();
            long count = booleans.size();
            count = count == 0 ? 1 : count;
            if (Functions.applyOperate(condition.getRateOperate(), condition.getRate(), condition.getRateMax(), (double) ((trueCount * 100) / count))) {
                saveEstablishmentDiagnostic(establishment, condition, currentYear, user);

            }
        });
        groups.entrySet().forEach(condition -> {
            if (condition.getValue().size() > 0)
                guidanceGroupService.saveGroups(condition.getKey().getShedSetting().getGroupName(), condition.getValue());
        });
        userScriptRepository.delete(user.getId(), currentYear.getId(), script.getId());
        userScriptRepository.save(UserScript.builder().script(script).user(user).year(currentYear).build());
        return false;
    }

    private Boolean applyEstablishmentConditions(TCO001EstablishmentCondition condition, Student student, Year year, OriachadUser user) {

        TCO001GuidanceSpecialityAverage SpecialityAverage = tCO001GuidanceSpecialityAverageRepository.find(year.getId(), user.getId(), student.getId(), condition.getIdGuidanceSpeciality());
        if (SpecialityAverage == null) {
            return false;
        }

        Boolean appliesGuidanceOperate = Functions.applyOperate(condition.getGuidanceOperate(), condition.getAverage(), condition.getAverageMax(), SpecialityAverage.getValue());

        if (!appliesGuidanceOperate) {
            return false;
        }
        if (condition.getSubjectType() == TCO001EstablishmentCondition.TCO001EstablishmentConditionSubjectType.all) {
            return SpecialityAverage.getSubjectAverages().stream().allMatch(tco001SubjectAverage -> Functions.applyOperate(condition.getSubjectOperate(), condition.getSubjectAverage(), condition.getSubjectAverageMax(), tco001SubjectAverage.getAverage()));
        }

        if (condition.getSubjectType() == TCO001EstablishmentCondition.TCO001EstablishmentConditionSubjectType.one) {
            return SpecialityAverage.getSubjectAverages().stream().anyMatch(tco001SubjectAverage -> Functions.applyOperate(condition.getSubjectOperate(), condition.getSubjectAverage(), condition.getSubjectAverageMax(), tco001SubjectAverage.getAverage()));
        }
        return true;
    }


    private Boolean applySubjectConditions(TCO001SubjectCondition condition, Subject subject, Student student, Year year, OriachadUser user) {
        TCO001SubjectAverage subjectAverage = tco001SubjectAverageRepository.find(year.getId(), user.getId(), student.getId(), condition.getIdGuidanceSpeciality(), condition.getIdSubject());
        if (subjectAverage == null) {
            return false;
        }

        Boolean appliesGuidanceOperate = Functions.applyOperate(condition.getOperate(), condition.getAverage(), condition.getAverage(), subjectAverage.getAverage());

        if (!appliesGuidanceOperate) {
            return false;
        }
        return true;
    }


    private Boolean applySpecialityConditions(TCO001SpecialityCondition condition, Student student, Year year, OriachadUser user) {

        TCO001GuidanceSpecialityAverage SpecialityAverage = tCO001GuidanceSpecialityAverageRepository.find(year.getId(), user.getId(), student.getId(), condition.getIdGuidanceSpeciality());
        if (SpecialityAverage == null) {
            return false;
        }

        Boolean appliesGuidanceOperate = Functions.applyOperate(condition.getGuidanceOperate(), condition.getAverage(), condition.getAverageMax(), SpecialityAverage.getValue());

        if (!appliesGuidanceOperate) {
            return false;
        }
        if (condition.getSubjectType() == TCO001SpecialityCondition.TCO001SpecialityConditionSubjectType.all) {
            return SpecialityAverage.getSubjectAverages().stream().allMatch(tco001SubjectAverage -> Functions.applyOperate(condition.getSubjectOperate(), condition.getSubjectAverage(), condition.getSubjectAverageMax(), tco001SubjectAverage.getAverage()));
        }

        if (condition.getSubjectType() == TCO001SpecialityCondition.TCO001SpecialityConditionSubjectType.one) {
            return SpecialityAverage.getSubjectAverages().stream().anyMatch(tco001SubjectAverage -> Functions.applyOperate(condition.getSubjectOperate(), condition.getSubjectAverage(), condition.getSubjectAverageMax(), tco001SubjectAverage.getAverage()));
        }
        return true;

    }


    private Boolean applyLevelConditions(TCO001LevelCondition condition, Student student, Year year, OriachadUser user) {

        DesireSpecialty desireSpecialty = desireSpecialtyRepository.findByOrder(year.getId(), user.getId(), student.getId(), condition.getNumber());
        if (desireSpecialty == null) {
            return false;
        }

        TCO001GuidanceSpecialityAverage SpecialityAverage = tCO001GuidanceSpecialityAverageRepository.find(year.getId(), user.getId(), student.getId(), desireSpecialty.getIdGuidanceSpeciality());
        if (SpecialityAverage == null) {
            return false;
        }

        Boolean appliesGuidanceOperate = Functions.applyOperate(condition.getOperate(), condition.getAverage(), condition.getAverageMax(), SpecialityAverage.getValue());

        if (!appliesGuidanceOperate) {
            return false;
        }

        return true;

    }

    private void processGuidanceSpecialityConfig(TCO001GuidanceSpecialityConfig tco001GuidanceSpecialityConfig, DesireSpecialty desireSpecialty, Student student, Classe classe, Level level, OriachadUser user, Year currentYear) {
        TCO001GuidanceSpecialityAverage tco001GuidanceSpecialityAverage = new TCO001GuidanceSpecialityAverage();
        List<TCO001SubjectAverage> tco001SubjectAverages = new ArrayList<>();
        tco001GuidanceSpecialityConfig.getSubjectConfigs().forEach(tco001SubjectConfig -> {
            Double subjectValue = noteService.findSubjectNote(student.getId(), tco001SubjectConfig.getIdSubject(), currentYear.getId(), trimesetNumber).orElse(0.0);
            tco001SubjectAverages.add(TCO001SubjectAverage.builder()
                    .subject(tco001SubjectConfig.getSubject())
                    .student(student)
                    .level(level)
                    .speciality(Speciality.builder().id(classe.getIdSpeciality()).build())
                    .classe(classe)
                    .createdBy(user)
                    .year(currentYear)
                    .coefficient(tco001SubjectConfig.getCoefficient())
                    .average(subjectValue)
                    .averageTotal(subjectValue * tco001SubjectConfig.getCoefficient())
                    .guidanceSpecialityAverage(tco001GuidanceSpecialityAverage).build());
        });

        Double sumAverage = tco001SubjectAverages.stream().mapToDouble(TCO001SubjectAverage::getAverageTotal).sum();
        Integer sumCoefficient = tco001SubjectAverages.stream().mapToInt(TCO001SubjectAverage::getCoefficient).sum();
        sumCoefficient = sumCoefficient > 0 ? sumCoefficient : 1;

        tco001GuidanceSpecialityAverage.setSubjectAverages(tco001SubjectAverages);
        tco001GuidanceSpecialityAverage.setValue(sumAverage / sumCoefficient);
        tco001GuidanceSpecialityAverage.setStudent(student);
        tco001GuidanceSpecialityAverage.setYear(currentYear);
        tco001GuidanceSpecialityAverage.setClasse(classe);
        tco001GuidanceSpecialityAverage.setSpeciality(Speciality.builder().id(classe.getIdSpeciality()).build());

        tco001GuidanceSpecialityAverage.setLevel(level);
        if (desireSpecialty != null) {
            tco001GuidanceSpecialityAverage.setOrder(desireSpecialty.getOrder());
            tco001GuidanceSpecialityAverage.setDesireSpecialty(desireSpecialty);
        }
        tco001GuidanceSpecialityAverage.setGuidanceSpeciality(tco001GuidanceSpecialityConfig.getGuidanceSpeciality());
        tco001GuidanceSpecialityAverage.setCreatedBy(user);

        tCO001GuidanceSpecialityAverageRepository.save(tco001GuidanceSpecialityAverage);
    }

    private void saveStudentDiagnostic(Student student, TCO001StudentCondition condition, Year currentYear, OriachadUser user) {
        diagnosticRepository.save(TCO001Diagnostic.builder().type(DiagnosticType.student).student(student).createdBy(user).shedSetting(condition.getShedSetting()).year(currentYear).build());
    }

    private void saveClassDiagnostic(Classe classe, TCO001ClasseCondition condition, Year currentYear, OriachadUser user) {
        diagnosticRepository.save(TCO001Diagnostic.builder().type(DiagnosticType.classe).classe(classe).createdBy(user).shedSetting(condition.getShedSetting()).year(currentYear).build());
    }

    private void saveLevelDiagnostic(Level level, TCO001LevelCondition condition, Year currentYear, OriachadUser user) {
        diagnosticRepository.save(TCO001Diagnostic.builder().type(DiagnosticType.classe).level(level).createdBy(user).shedSetting(condition.getShedSetting()).year(currentYear).build());

    }

    private void saveSpecialityDiagnostic(Speciality speciality, Level level, TCO001SpecialityCondition condition, Year currentYear, OriachadUser user) {
        diagnosticRepository.save(TCO001Diagnostic.builder().type(DiagnosticType.speciality).level(level).speciality(speciality).createdBy(user).shedSetting(condition.getShedSetting()).year(currentYear).build());

    }

    private void saveEstablishmentDiagnostic(Establishment establishment, TCO001EstablishmentCondition condition, Year currentYear, OriachadUser user) {
        diagnosticRepository.save(TCO001Diagnostic.builder().type(DiagnosticType.establishment).establishment(establishment).createdBy(user).shedSetting(condition.getShedSetting()).year(currentYear).build());

    }

    private void saveSubjectDiagnostic(Subject subject, TCO001SubjectCondition condition, Year currentYear, OriachadUser user) {
        diagnosticRepository.save(TCO001Diagnostic.builder().type(DiagnosticType.subject).subject(subject).createdBy(user).shedSetting(condition.getShedSetting()).year(currentYear).build());

    }

    private Boolean applyStudentConditions(TCO001StudentCondition condition, Student student, Year year, OriachadUser user) {
        DesireSpecialty desireSpecialty = desireSpecialtyRepository.findByOrder(year.getId(), user.getId(), student.getId(), condition.getNumber());
        if (desireSpecialty == null) {
            return false;
        }

        TCO001GuidanceSpecialityAverage SpecialityAverage = tCO001GuidanceSpecialityAverageRepository.find(year.getId(), user.getId(), student.getId(), desireSpecialty.getIdGuidanceSpeciality());
        if (SpecialityAverage == null) {
            return false;
        }

        Boolean appliesGuidanceOperate = Functions.applyOperate(condition.getGuidanceOperate(), condition.getAverage(), condition.getAverageMax(), SpecialityAverage.getValue());
        Integer appliesRank = tCO001GuidanceSpecialityAverageRepository.testRank(year.getId(), user.getId(), student.getId(), SpecialityAverage.getId());

        if (appliesRank == null || !appliesGuidanceOperate || appliesRank != condition.getRank()) {
            return false;
        }

        if (condition.getSubjectType() == TCO001StudentCondition.TCO001StudentConditionSubjectType.all) {
            return SpecialityAverage.getSubjectAverages().stream().allMatch(tco001SubjectAverage -> Functions.applyOperate(condition.getSubjectOperate(), condition.getSubjectAverage(), condition.getSubjectAverageMax(), tco001SubjectAverage.getAverage()));
        }

        if (condition.getSubjectType() == TCO001StudentCondition.TCO001StudentConditionSubjectType.one) {
            return SpecialityAverage.getSubjectAverages().stream().anyMatch(tco001SubjectAverage -> Functions.applyOperate(condition.getSubjectOperate(), condition.getSubjectAverage(), condition.getSubjectAverageMax(), tco001SubjectAverage.getAverage()));
        }

        return true;
    }

    private Boolean applyClassConditions(TCO001ClasseCondition condition, Student student, Year year, OriachadUser user) {

        DesireSpecialty desireSpecialty = desireSpecialtyRepository.findByOrder(year.getId(), user.getId(), student.getId(), condition.getNumber());
        if (desireSpecialty == null) {
            return false;
        }

        TCO001GuidanceSpecialityAverage specialityAverage = tCO001GuidanceSpecialityAverageRepository.find(year.getId(), user.getId(), student.getId(), desireSpecialty.getIdGuidanceSpeciality());
        if (specialityAverage == null) {
            return false;
        }

        Boolean appliesGuidanceOperate = Functions.applyOperate(condition.getGuidanceOperate(), condition.getAverage(), condition.getAverageMax(), specialityAverage.getValue());
        Integer appliesRank = tCO001GuidanceSpecialityAverageRepository.testRank(year.getId(), user.getId(), student.getId(), specialityAverage.getId());

        if (appliesRank == null || !appliesGuidanceOperate || appliesRank != condition.getRank()) {
            return false;
        }

        if (condition.getSubjectType() == TCO001StudentCondition.TCO001StudentConditionSubjectType.all) {
            return specialityAverage.getSubjectAverages().stream().allMatch(tco001SubjectAverage -> Functions.applyOperate(condition.getSubjectOperate(), condition.getSubjectAverage(), condition.getSubjectAverageMax(), tco001SubjectAverage.getAverage()));
        }

        if (condition.getSubjectType() == TCO001StudentCondition.TCO001StudentConditionSubjectType.one) {
            return specialityAverage.getSubjectAverages().stream().anyMatch(tco001SubjectAverage -> Functions.applyOperate(condition.getSubjectOperate(), condition.getSubjectAverage(), condition.getSubjectAverageMax(), tco001SubjectAverage.getAverage()));
        }

        return true;

    }

    @Override
    public Object getStudentCardData(Long idStudent, Long idYear) {
        OriachadUser currentUser = helperService.getCurrentUser().getOriachadUser();
        var results = tCO001GuidanceSpecialityAverageRepository.findAllByStudent(idYear, currentUser.getId(), idStudent);
        List<TCO001StudentGuidanceSpecialityAverage> guidanceSpecialityAverages = results.stream()
                .map(result -> TCO001StudentGuidanceSpecialityAverage.builder()
                        .title(result.getGuidanceSpeciality().getTitle())
                        .average(result.getValue())
                        .order(result.getOrder()) // Keep the order as-is
                        .rank(results.indexOf(result) + 1)
                        .build())
                .sorted(Comparator.comparing(
                        (TCO001StudentGuidanceSpecialityAverage avg) -> avg.getOrder() == null ? Integer.MAX_VALUE : avg.getOrder()
                ))
                .collect(Collectors.toList());
        List<String> diagnostics = diagnosticRepository.findByStudent(idStudent, idYear, currentUser.getId());
        return TCO0001StudentCardDto.builder().guidanceSpecialityAverage(guidanceSpecialityAverages).diagnostics(diagnostics).build();
    }

    @Override
    public Object getClassCardData(Long idClass, Long idYear) {
        OriachadUser currentUser = helperService.getCurrentUser().getOriachadUser();
        TCO0001ClasseCardDto tco0001ClasseCardDto = TCO0001ClasseCardDto.builder().guidanceSpecialityAverage(new ArrayList<>()).build();
        tCO001GuidanceSpecialityAverageRepository.findAllByClasse(idClass, idYear, currentUser.getId(), 1).stream().collect(Collectors.groupingBy(TCO001GuidanceSpecialityAverage::getIdGuidanceSpeciality))
                .forEach((idGuidanceSpeciality, tco001GuidanceSpecialityAverages) -> {
                    Double average = tco001GuidanceSpecialityAverages.stream().mapToDouble(TCO001GuidanceSpecialityAverage::getValue).sum();
                    List<Double> subjectAverages = tco001SubjectAverageRepository.getMaxCoefficientAverageClass(idClass, idYear, currentUser.getId(), idGuidanceSpeciality);
                    Integer total = tco001GuidanceSpecialityAverages.size();
                    total = total == 0 ? 1 : total;
                    double subjectAverage1 = subjectAverages != null && subjectAverages.size() > 0 ? subjectAverages.get(0) : 0.0;
                    double subjectAverage2 = subjectAverages != null && subjectAverages.size() > 1 ? subjectAverages.get(1) : 0.0;
                    double countLess10 = tco001GuidanceSpecialityAverages.stream().filter(ag -> ag.getValue() < 10).count();
                    double percentageLess10 = countLess10 * 100 / total;
                    double countGreater10 = tco001GuidanceSpecialityAverages.stream().filter(ag -> ag.getValue() >= 10).count();
                    double percentageGreater10 = countGreater10 * 100 / total;
                    double countGreater14 = tco001GuidanceSpecialityAverages.stream().filter(ag -> ag.getValue() > 14).count();
                    double percentageGreater14 = countGreater14 * 100 / total;
                    double count10_14 = tco001GuidanceSpecialityAverages.stream().filter(ag -> ag.getValue() >= 10 && ag.getValue() <= 14).count();
                    double percentage10_14 = count10_14 * 100 / total;

                    tco0001ClasseCardDto.getGuidanceSpecialityAverage()
                            .add(TCO0001ClasseCardDto.GuidanceSpecialityAverage.builder()
                                    .average(average / total)
                                    .specialityTitle(tco001GuidanceSpecialityAverages.stream().findAny().orElseThrow().getGuidanceSpeciality().getTitle())
                                    .subjectAverage1(subjectAverage1)
                                    .subjectAverage2(subjectAverage2)
                                    .countLess10(countLess10)
                                    .percentageLess10(percentageLess10)
                                    .countGreater10(countGreater10)
                                    .percentageGreater10(percentageGreater10)
                                    .countGreater14(countGreater14)
                                    .percentageGreater14(percentageGreater14)
                                    .count10_14(count10_14)
                                    .percentage10_14(percentage10_14)
                                    .build());

                });

        List<String> diagnostics = diagnosticRepository.findByClasse(idClass, idYear, currentUser.getId());
        tco0001ClasseCardDto.setDiagnostics(diagnostics);
        return tco0001ClasseCardDto;
    }

    @Override
    public Object getLevelCardData(Long idLevel, Long idYear) {
        OriachadUser currentUser = helperService.getCurrentUser().getOriachadUser();
        TCO0001LevelCardDto tco0001LevelCardDto = TCO0001LevelCardDto.builder().guidanceSpecialityAverage(new ArrayList<>()).build();
        tCO001GuidanceSpecialityAverageRepository.findAllByLevel(idLevel, idYear, currentUser.getId(), 1).stream().collect(Collectors.groupingBy(TCO001GuidanceSpecialityAverage::getIdGuidanceSpeciality))
                .forEach((idGuidanceSpeciality, tco001GuidanceSpecialityAverages) -> {
                    Double average = tco001GuidanceSpecialityAverages.stream().mapToDouble(TCO001GuidanceSpecialityAverage::getValue).sum();
                    List<Double> subjectAverages = tco001SubjectAverageRepository.getMaxCoefficientAverageLevel(idLevel, idYear, currentUser.getId(), idGuidanceSpeciality);
                    Integer total = tco001GuidanceSpecialityAverages.size();
                    total = total == 0 ? 1 : total;
                    double subjectAverage1 = subjectAverages != null && subjectAverages.size() > 0 ? subjectAverages.get(0) : 0.0;
                    double subjectAverage2 = subjectAverages != null && subjectAverages.size() > 1 ? subjectAverages.get(1) : 0.0;
                    double countLess10 = tco001GuidanceSpecialityAverages.stream().filter(ag -> ag.getValue() < 10).count();
                    double percentageLess10 = countLess10 * 100 / total;
                    double countGreater10 = tco001GuidanceSpecialityAverages.stream().filter(ag -> ag.getValue() >= 10).count();
                    double percentageGreater10 = countGreater10 * 100 / total;
                    double countGreater14 = tco001GuidanceSpecialityAverages.stream().filter(ag -> ag.getValue() > 14).count();
                    double percentageGreater14 = countGreater14 * 100 / total;
                    double count10_14 = tco001GuidanceSpecialityAverages.stream().filter(ag -> ag.getValue() >= 10 && ag.getValue() <= 14).count();
                    double percentage10_14 = count10_14 * 100 / total;

                    tco0001LevelCardDto.getGuidanceSpecialityAverage()
                            .add(TCO0001LevelCardDto.GuidanceSpecialityAverage.builder()
                                    .average(average / total)
                                    .specialityTitle(tco001GuidanceSpecialityAverages.stream().findAny().orElseThrow().getGuidanceSpeciality().getTitle())
                                    .subjectAverage1(subjectAverage1)
                                    .subjectAverage2(subjectAverage2)
                                    .countLess10(countLess10)
                                    .percentageLess10(percentageLess10)
                                    .countGreater10(countGreater10)
                                    .percentageGreater10(percentageGreater10)
                                    .countGreater14(countGreater14)
                                    .percentageGreater14(percentageGreater14)
                                    .count10_14(count10_14)
                                    .percentage10_14(percentage10_14)
                                    .build());

                });

        List<String> diagnostics = diagnosticRepository.findByLevel(idLevel, idYear, currentUser.getId());
        tco0001LevelCardDto.setDiagnostics(diagnostics);
        return tco0001LevelCardDto;

    }

    @Override
    public Object getSubjectCardData(Long idSubject, Long idYear) {
        OriachadUser currentUser = helperService.getCurrentUser().getOriachadUser();
        TCO0001SubjectCardDto tco0001SubjectCardDto = TCO0001SubjectCardDto.builder().guidanceSpecialityAverage(new ArrayList<>()).build();
        tCO001GuidanceSpecialityAverageRepository.findAllBySubject(idSubject, idYear, currentUser.getId(), 1).stream().collect(Collectors.groupingBy(TCO001GuidanceSpecialityAverage::getIdGuidanceSpeciality))
                .forEach((idGuidanceSpeciality, tco001GuidanceSpecialityAverages) -> {
                    Double average = tco001GuidanceSpecialityAverages.stream().mapToDouble(TCO001GuidanceSpecialityAverage::getValue).sum();
                    Double subjectAverage = tco001SubjectAverageRepository.getMaxCoefficientAverageSubject(idSubject, idYear, currentUser.getId(), idGuidanceSpeciality);
                    Integer total = tco001GuidanceSpecialityAverages.size();
                    total = total == 0 ? 1 : total;
                    double countLess10 = tco001GuidanceSpecialityAverages.stream().filter(ag -> ag.getValue() < 10).count();
                    double percentageLess10 = countLess10 * 100 / total;
                    double countGreater10 = tco001GuidanceSpecialityAverages.stream().filter(ag -> ag.getValue() >= 10).count();
                    double percentageGreater10 = countGreater10 * 100 / total;
                    double countGreater14 = tco001GuidanceSpecialityAverages.stream().filter(ag -> ag.getValue() > 14).count();
                    double percentageGreater14 = countGreater14 * 100 / total;
                    double count10_14 = tco001GuidanceSpecialityAverages.stream().filter(ag -> ag.getValue() >= 10 && ag.getValue() <= 14).count();
                    double percentage10_14 = count10_14 * 100 / total;
                    tco0001SubjectCardDto.getGuidanceSpecialityAverage()
                            .add(TCO0001SubjectCardDto.GuidanceSpecialityAverage.builder()
                                    .average(average / total)
                                    .specialityTitle(tco001GuidanceSpecialityAverages.stream().findAny().orElseThrow().getGuidanceSpeciality().getTitle())
                                    .subjectAverage(subjectAverage)
                                    .countLess10(countLess10)
                                    .percentageLess10(percentageLess10)
                                    .countGreater10(countGreater10)
                                    .percentageGreater10(percentageGreater10)
                                    .countGreater14(countGreater14)
                                    .percentageGreater14(percentageGreater14)
                                    .count10_14(count10_14)
                                    .percentage10_14(percentage10_14)
                                    .build());

                });
        List<String> diagnostics = diagnosticRepository.findBySubject(idSubject, idYear, currentUser.getId());
        tco0001SubjectCardDto.setDiagnostics(diagnostics);
        return tco0001SubjectCardDto;
    }

    @Override
    public Object getSpecialityCardData(Long idSpecialty, Long idLevel, Long idYear) {
        Establishment establishment = helperService.getCurrentEstablishment();
        if (establishment.getType() != TypeEstablishment.secondary) {
            return null;
        }
        OriachadUser currentUser = helperService.getCurrentUser().getOriachadUser();
        TCO0001SpecialityCardDto tco0001SpecialityCardDto = TCO0001SpecialityCardDto.builder().guidanceSpecialityAverage(new ArrayList<>()).build();
        tCO001GuidanceSpecialityAverageRepository.findAllBySpecialty(idSpecialty, idLevel, idYear, currentUser.getId(), 1).stream().collect(Collectors.groupingBy(TCO001GuidanceSpecialityAverage::getIdGuidanceSpeciality))
                .forEach((idGuidanceSpeciality, tco001GuidanceSpecialityAverages) -> {
                    Double average = tco001GuidanceSpecialityAverages.stream().mapToDouble(TCO001GuidanceSpecialityAverage::getValue).sum();
                    List<Double> subjectAverages = tco001SubjectAverageRepository.getMaxCoefficientAverageSpecialty(idSpecialty, idLevel, idYear, currentUser.getId(), idGuidanceSpeciality);
                    double subjectAverage1 = subjectAverages != null && subjectAverages.size() > 0 ? subjectAverages.get(0) : 0.0;
                    double subjectAverage2 = subjectAverages != null && subjectAverages.size() > 1 ? subjectAverages.get(1) : 0.0;
                    Integer total = tco001GuidanceSpecialityAverages.size();
                    total = total == 0 ? 1 : total;
                    double countLess10 = tco001GuidanceSpecialityAverages.stream().filter(ag -> ag.getValue() < 10).count();
                    double percentageLess10 = countLess10 * 100 / total;
                    double countGreater10 = tco001GuidanceSpecialityAverages.stream().filter(ag -> ag.getValue() >= 10).count();
                    double percentageGreater10 = countGreater10 * 100 / total;
                    double countGreater14 = tco001GuidanceSpecialityAverages.stream().filter(ag -> ag.getValue() > 14).count();
                    double percentageGreater14 = countGreater14 * 100 / total;
                    double count10_14 = tco001GuidanceSpecialityAverages.stream().filter(ag -> ag.getValue() >= 10 && ag.getValue() <= 14).count();
                    double percentage10_14 = count10_14 * 100 / total;

                    tco0001SpecialityCardDto.getGuidanceSpecialityAverage()
                            .add(TCO0001SpecialityCardDto.GuidanceSpecialityAverage.builder()
                                    .average(average / total)
                                    .specialityTitle(tco001GuidanceSpecialityAverages.stream().findAny().orElseThrow().getGuidanceSpeciality().getTitle())
                                    .subjectAverage1(subjectAverage1)
                                    .subjectAverage2(subjectAverage2)
                                    .countLess10(countLess10)
                                    .percentageLess10(percentageLess10)
                                    .countGreater10(countGreater10)
                                    .percentageGreater10(percentageGreater10)
                                    .countGreater14(countGreater14)
                                    .percentageGreater14(percentageGreater14)
                                    .count10_14(count10_14)
                                    .percentage10_14(percentage10_14)
                                    .build());

                });

        List<String> diagnostics = diagnosticRepository.findBySpeciality(idSpecialty, idLevel, idYear, currentUser.getId());
        tco0001SpecialityCardDto.setDiagnostics(diagnostics);
        return tco0001SpecialityCardDto;
    }

    @Override
    public Object getEstablishmentCardData(Long idEstablishment, Long idYear) {
        OriachadUser currentUser = helperService.getCurrentUser().getOriachadUser();
        TCO0001EstablishmentCardDto tco0001EstablishmentCardDto = TCO0001EstablishmentCardDto.builder().guidanceSpecialityAverage(new ArrayList<>()).build();
        tCO001GuidanceSpecialityAverageRepository.findAllByEstablishment(idYear, currentUser.getId(), 1).stream().collect(Collectors.groupingBy(TCO001GuidanceSpecialityAverage::getIdGuidanceSpeciality))
                .forEach((idGuidanceSpeciality, tco001GuidanceSpecialityAverages) -> {
                    Double average = tco001GuidanceSpecialityAverages.stream().mapToDouble(TCO001GuidanceSpecialityAverage::getValue).sum();
                    List<Double> subjectAverages = tco001SubjectAverageRepository.getMaxCoefficientAverageEstablishment(idYear, currentUser.getId(), idGuidanceSpeciality);
                    double subjectAverage1 = subjectAverages != null && subjectAverages.size() > 0 ? subjectAverages.get(0) : 0.0;
                    double subjectAverage2 = subjectAverages != null && subjectAverages.size() > 1 ? subjectAverages.get(1) : 0.0;
                    Integer total = tco001GuidanceSpecialityAverages.size();
                    total = total == 0 ? 1 : total;
                    double countLess10 = tco001GuidanceSpecialityAverages.stream().filter(ag -> ag.getValue() < 10).count();
                    double percentageLess10 = countLess10 * 100 / total;
                    double countGreater10 = tco001GuidanceSpecialityAverages.stream().filter(ag -> ag.getValue() >= 10).count();
                    double percentageGreater10 = countGreater10 * 100 / total;
                    double countGreater14 = tco001GuidanceSpecialityAverages.stream().filter(ag -> ag.getValue() > 14).count();
                    double percentageGreater14 = countGreater14 * 100 / total;
                    double count10_14 = tco001GuidanceSpecialityAverages.stream().filter(ag -> ag.getValue() >= 10 && ag.getValue() <= 14).count();
                    double percentage10_14 = count10_14 * 100 / total;

                    tco0001EstablishmentCardDto.getGuidanceSpecialityAverage()
                            .add(TCO0001EstablishmentCardDto.GuidanceSpecialityAverage.builder()
                                    .average(average / total)
                                    .specialityTitle(tco001GuidanceSpecialityAverages.stream().findAny().orElseThrow().getGuidanceSpeciality().getTitle())
                                    .subjectAverage1(subjectAverage1)
                                    .subjectAverage2(subjectAverage2)
                                    .countLess10(countLess10)
                                    .percentageLess10(percentageLess10)
                                    .countGreater10(countGreater10)
                                    .percentageGreater10(percentageGreater10)
                                    .countGreater14(countGreater14)
                                    .percentageGreater14(percentageGreater14)
                                    .count10_14(count10_14)
                                    .percentage10_14(percentage10_14)
                                    .build());

                });

        List<String> diagnostics = diagnosticRepository.findByEstablishment(idEstablishment, idYear, currentUser.getId());
        tco0001EstablishmentCardDto.setDiagnostics(diagnostics);
        return tco0001EstablishmentCardDto;
    }

    @Override
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
