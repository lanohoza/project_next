package com.pajiniweb.oriachad_backend.services;

import com.pajiniweb.oriachad_backend.domains.dtos.*;
import com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos.AddEditStudentDto;
import com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos.AddNewClassStudentDto;
import com.pajiniweb.oriachad_backend.domains.entities.*;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.repositories.ClasseRepository;
import com.pajiniweb.oriachad_backend.repositories.InfosStudentRepository;
import com.pajiniweb.oriachad_backend.repositories.StudentClasseRepository;
import com.pajiniweb.oriachad_backend.repositories.StudentRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@AllArgsConstructor
public class StudentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    private final InfosStudentRepository infosStudentRepository;
    private final ClasseRepository classeRepository;
    private final StudentClasseRepository studentClasseRepository;

	private final HelperService helperService;

    public List<StudentDto> findAll() {
        LOGGER.info(Messages.START_FUNCTION, "findAll student");
        try {
            List<Student> students = studentRepository.findAll();
            //return students.stream().map(this::convertToDto).collect(Collectors.toList());
            return null;
        } catch (Exception e) {
            LOGGER.error(Messages.ENTITY_IS_NOT_FOUND, "student", e.getMessage());
            throw new RuntimeException(Messages.PROCESS_IS_NOT_DONE);
        }
    }

	public StudentDto findById(Long id, Long idClasse) {
		LOGGER.info(Messages.START_FUNCTION, "findById student");
		try {
			    return studentRepository.findById(id).map(student -> convertToDto(student,idClasse)).orElseThrow(() -> new RuntimeException(Messages.ENTITY_IS_NOT_FOUND));
		} catch (Exception e) {
			LOGGER.error("Error fetching student by id", e);
			throw e;
		}
	}

    @Transactional

    public boolean save(AddEditStudentDto addEditStudentDto) {
        LOGGER.info(Messages.START_FUNCTION, "save student");
        try {

            Classe classe = classeRepository.findById(addEditStudentDto.getIdClasse()).orElseThrow();
            Student student = Student.builder().establishment(classe.getEstablishment()).nbrRakmana(addEditStudentDto.getNbrRakmana()).firstName(addEditStudentDto.getFirstName()).lastName(addEditStudentDto.getLastName()).sexe(addEditStudentDto.getSexe()).birthDate(addEditStudentDto.getBirthDate()).placeBirth(addEditStudentDto.getPlaceBirth()).build();

            student = studentRepository.save(student);

            // Generate the codeStudent after saving the student to get the ID
            student.setCodeStudent(generateCodeStudent(student));
            studentRepository.save(student);


			studentClasseRepository.save(StudentClasse.builder().removed(false).student(student).classe(classe).dateStudentInscription(addEditStudentDto.getDateStudentInscription()).schoolingSystem(addEditStudentDto.getSchoolingSystem()).build());

            InfosStudent infosStudent = InfosStudent.builder().isMain(addEditStudentDto.getIsMain()).student(student).repeatClasseActual(addEditStudentDto.getRepeatClasseActual()).nbrRepeatClasse(addEditStudentDto.getNbrRepeatClasse()).fatherProfession(addEditStudentDto.getFatherProfession()).motherProfession(addEditStudentDto.getMotherProfession()).tutorName(addEditStudentDto.getTutorName()).tutorMobPhone(addEditStudentDto.getTutorMobPhone()).tutorEmail(addEditStudentDto.getTutorEmail()).healthProblem(addEditStudentDto.getHealthProblem()).isMotherOrphan(addEditStudentDto.getIsMotherOrphan()).isFatherOrphan(addEditStudentDto.getIsFatherOrphan()).isDisease(addEditStudentDto.getIsDisease()).isNeed(addEditStudentDto.getIsNeed()).build();
            infosStudentRepository.save(infosStudent);

            return true;
        } catch (Exception e) {
            LOGGER.error("Error saving student", e);
            throw e;
        }
    }

    @Transactional

    public boolean update(AddEditStudentDto addEditStudentDto, Long idClasse) {
        LOGGER.info(Messages.START_FUNCTION, "update student");
        try {
            Classe classe = classeRepository.findById(idClasse).orElseThrow();
            Student student = studentRepository.findById(addEditStudentDto.getId()).orElseThrow(() -> {
                LOGGER.error(Messages.ENTITY_IS_NOT_EXIST, "student");
                throw new IllegalArgumentException(Messages.ENTITY_IS_NOT_EXIST.replace("{}", "student"));
            });

            student.setNbrRakmana(addEditStudentDto.getNbrRakmana());
            student.setFirstName(addEditStudentDto.getFirstName());
            student.setLastName(addEditStudentDto.getLastName());
            student.setSexe(addEditStudentDto.getSexe());
            student.setBirthDate(addEditStudentDto.getBirthDate());
            student.setPlaceBirth(addEditStudentDto.getPlaceBirth());
            studentRepository.save(student);

	        StudentClasse studentClasse = studentClasseRepository.findByIdStudentAndIdClasse(student.getId(), classe.getId()).orElseThrow();
            studentClasse.setSchoolingSystem(addEditStudentDto.getSchoolingSystem());
            studentClasse.setDateStudentInscription(addEditStudentDto.getDateStudentInscription());
            studentClasseRepository.save(studentClasse);

            InfosStudent infosStudent = infosStudentRepository.findByIdStudent(student.getId()).orElseGet(() -> new InfosStudent());
            infosStudent.setStudent(student);
            infosStudent.setRepeatClasseActual(addEditStudentDto.getRepeatClasseActual());
            infosStudent.setNbrRepeatClasse(addEditStudentDto.getNbrRepeatClasse());
            infosStudent.setFatherProfession(addEditStudentDto.getFatherProfession());
            infosStudent.setMotherProfession(addEditStudentDto.getMotherProfession());
            infosStudent.setTutorName(addEditStudentDto.getTutorName());
            infosStudent.setTutorMobPhone(addEditStudentDto.getTutorMobPhone());
            infosStudent.setTutorEmail(addEditStudentDto.getTutorEmail());
            infosStudent.setIsMotherOrphan(addEditStudentDto.getIsMotherOrphan());
            infosStudent.setIsFatherOrphan(addEditStudentDto.getIsFatherOrphan());
            infosStudent.setIsDisease(addEditStudentDto.getIsDisease());
            infosStudent.setIsNeed(addEditStudentDto.getIsNeed());
            infosStudent.setIsMain(addEditStudentDto.getIsMain());
            // infosStudent.setDateStudentInscription(addEditStudentDto.getDateStudentInscription());
            infosStudent.setHealthProblem(addEditStudentDto.getHealthProblem());

            infosStudentRepository.save(infosStudent);

            return true;
        } catch (Exception e) {
            LOGGER.error("Error updating student", e);
            throw e;
        }
    }

    @Transactional
    public boolean deleteById(Long id) {
        LOGGER.info(Messages.START_FUNCTION, "deleteById student");
        try {

            if (studentRepository.existsById(id) && infosStudentRepository.existsByIdStudent(id)) {
                infosStudentRepository.deleteByIdStudent(id);
                studentRepository.deleteById(id);
                LOGGER.info(Messages.DELETE_SUCCESSFULLY, "student");
                return true;
            } else {
                LOGGER.warn(Messages.ENTITY_IS_NOT_EXIST, "student");
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("Error deleting student", e);
            throw e;
        }
    }


    @Transactional
    public boolean saveToNewClass(AddNewClassStudentDto addNewClassStudentDto) {
        LOGGER.info(Messages.START_FUNCTION, "save student");
        try {
            Establishment establishment = helperService.getCurrentEstablishment();

            Student student = studentRepository.findByNbrRakmanaAndIdEstablishment(addNewClassStudentDto.getNbrRakmana(), establishment.getId()).orElseThrow(() -> {
                LOGGER.error(Messages.ENTITY_IS_NOT_EXIST, "student");
                throw new IllegalArgumentException(Messages.ENTITY_IS_NOT_EXIST.replace("{}", "student"));
            });
            Classe classe = classeRepository.findById(addNewClassStudentDto.getIdClasse()).orElseThrow();
            studentClasseRepository.save(StudentClasse.builder().student(student).classe(classe).dateStudentInscription(addNewClassStudentDto.getDateStudentInscription()).schoolingSystem(addNewClassStudentDto.getSchoolingSystem()).build());
            return true;
        } catch (Exception e) {
            LOGGER.error("Error saving student", e);
            throw e;
        }
    }

    @Transactional
    public boolean changeClass(Long idStudent, Long idOldClasse, Long idNewClasse) {
        LOGGER.info(Messages.START_FUNCTION, "save student");
        try {
            Student student = studentRepository.findById(idStudent).orElseThrow(() -> {
                LOGGER.error(Messages.ENTITY_IS_NOT_EXIST, "student");
                throw new IllegalArgumentException(Messages.ENTITY_IS_NOT_EXIST.replace("{}", "student"));
            });
            Classe classe = classeRepository.findById(idNewClasse).orElseThrow();
            StudentClasse studentClasse = studentClasseRepository.findByIdStudentAndIdClasse(student.getId(), idOldClasse).orElseThrow();
            studentClasse.setClasse(classe);
            studentClasseRepository.save(studentClasse);
            return true;
        } catch (Exception e) {
            LOGGER.error("Error saving student", e);
            throw e;
        }
    }

	public Page<StudentDto> searchStudents(String search, Long idClasse, Pageable pageable) {
		LOGGER.info(Messages.START_FUNCTION, "searchStudents");
		try {
			return studentRepository.search(idClasse, search, pageable).map(student -> convertToDto(student,idClasse));
		} catch (Exception e) {
			LOGGER.error("Error searching students", e);
			throw e;
		}
	}
	public Page<StudentDesireDto> searchWithDesire(Long idClasse, String search, int page, int size) {

		LOGGER.info(Messages.START_FUNCTION, "searchWithDesire");
		try {

			Pageable pageable = PageRequest.of(Math.max(page, 0), size);
			Classe classe = classeRepository.findById(idClasse).orElseThrow();

			return studentRepository.searchWithDesire(classe.getId(), classe.getIdYear(), search, pageable);

		} catch (Exception e) {
			LOGGER.error("Error searching students", e);
			throw e;
		}
	}


    private StudentDto convertToDto(Student student,Long idClass) {
        InfosStudent infosStudent = infosStudentRepository.findByIdStudent(student.getId()).orElseThrow();
        StudentClasse studentClasse = studentClasseRepository.findByIdStudentAndIdClasse(student.getId(),idClass).orElseThrow();
        return StudentDto.builder().id(student.getId()).nbrRakmana(student.getNbrRakmana()).codeStudent(student.getCodeStudent()).firstName(student.getFirstName()).lastName(student.getLastName()).sexe(student.getSexe()).birthDate(student.getBirthDate()).placeBirth(student.getPlaceBirth()).schoolingSystem(studentClasse.getSchoolingSystem()).idClasse(studentClasse.getIdClasse()).repeatClasseActual(infosStudent.getRepeatClasseActual()).nbrRepeatClasse(infosStudent.getNbrRepeatClasse()).fatherProfession(infosStudent.getFatherProfession()).motherProfession(infosStudent.getMotherProfession()).tutorName(infosStudent.getTutorName()).tutorMobPhone(infosStudent.getTutorMobPhone()).tutorEmail(infosStudent.getTutorEmail()).dateStudentInscription(studentClasse.getDateStudentInscription()).healthProblem(infosStudent.getHealthProblem()).classeTitle(studentClasse.getClasse().getTitle()).isMotherOrphan(infosStudent.getIsMotherOrphan()).isFatherOrphan(infosStudent.getIsFatherOrphan()).isDisease(infosStudent.getIsDisease()).isMain(infosStudent.getIsMain()).isNeed(infosStudent.getIsNeed()).build();
    }

    public static String generateCodeStudent(Student student) {
        LocalDate birthDate = student.getBirthDate();
        LocalDate createDate = LocalDate.now();
        String birthDateString = birthDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String createDateString = createDate.format(DateTimeFormatter.ofPattern("dd"));
        String idString = String.format("%04d", student.getId());

        return birthDateString + createDateString + idString;
    }

    public Page<StudentNoteDto> searchStudentsWithStatusByTrimestreAndClass(Long idClasse, Long idTrimestre, String search, int page, int size) {

        LOGGER.info(Messages.START_FUNCTION, "searchStudents");
        try {

            Pageable pageable = PageRequest.of(Math.max(page, 0), size);
            return studentRepository.searchStudentsWithStatusByTrimestreAndClass(idClasse, idTrimestre, search, pageable);

        } catch (Exception e) {
            LOGGER.error("Error searching students", e);
            throw e;
        }

    }

    @Transactional
    public List<CurrentStudentDto> getStudentByCurrentYear() {
        Establishment establishment = helperService.getCurrentEstablishment();
        Year year = helperService.getCurrentYear();
        return classeRepository.findStudentDtosByYearAndEstablishment(year.getId(), establishment.getId());
    }


	public Page<StudentDto> searchCurrentYearStudents(String search, Pageable pageable) {
		LOGGER.info(Messages.START_FUNCTION, "searchStudents");
		try {
            Establishment establishment=helperService.getCurrentEstablishment();
			return studentRepository.searchCurrentYear(search,establishment.getId(), pageable).map(student -> convertToDtoWithCurrentYear(student));
		} catch (Exception e) {
			LOGGER.error("Error searching students", e);
			throw e;
		}
	}

    private StudentDto convertToDtoWithCurrentYear(Student student) {
        InfosStudent infosStudent = infosStudentRepository.findByIdStudent(student.getId()).orElseThrow();
        StudentClasse studentClasse = studentClasseRepository.findByIdStudentCurrentYear(student.getId()).orElseThrow();
        return StudentDto.builder().id(student.getId()).nbrRakmana(student.getNbrRakmana()).codeStudent(student.getCodeStudent()).firstName(student.getFirstName()).lastName(student.getLastName()).sexe(student.getSexe()).birthDate(student.getBirthDate()).placeBirth(student.getPlaceBirth()).schoolingSystem(studentClasse.getSchoolingSystem()).idClasse(studentClasse.getIdClasse()).repeatClasseActual(infosStudent.getRepeatClasseActual()).nbrRepeatClasse(infosStudent.getNbrRepeatClasse()).fatherProfession(infosStudent.getFatherProfession()).motherProfession(infosStudent.getMotherProfession()).tutorName(infosStudent.getTutorName()).tutorMobPhone(infosStudent.getTutorMobPhone()).tutorEmail(infosStudent.getTutorEmail()).dateStudentInscription(studentClasse.getDateStudentInscription()).healthProblem(infosStudent.getHealthProblem()).classeTitle(studentClasse.getClasse().getTitle()).isMotherOrphan(infosStudent.getIsMotherOrphan()).isFatherOrphan(infosStudent.getIsFatherOrphan()).isDisease(infosStudent.getIsDisease()).isMain(infosStudent.getIsMain()).isNeed(infosStudent.getIsNeed()).build();
    }

    @Transactional
	public boolean removeById(Long idStudent, Long idClass) {
		studentClasseRepository.removeByIdStudentAndIdClass(idStudent,idClass);
		return true;
	}

    @Transactional
    public boolean restoreById(Long idStudentClass) {
        studentClasseRepository.restoreStudent(idStudentClass);
        return true;
    }

    public Page<StudentDto> getRemovedStudent(String search, int page, int size) {
        Establishment establishment = helperService.getCurrentEstablishment();
        Year year = helperService.getCurrentYear();
        Pageable pageable = PageRequest.of(page < 0 ? 0 : page, size);
        return studentClasseRepository.removedSearch(search, establishment.getId(), year.getId(), pageable)
                .map(studentClasse -> StudentDto.builder()
                        .id(studentClasse.getStudent().getId())
                        .nbrRakmana(studentClasse.getStudent().getNbrRakmana())
                        .codeStudent(studentClasse.getStudent().getCodeStudent())
                        .firstName(studentClasse.getStudent().getFirstName())
                        .lastName(studentClasse.getStudent().getLastName())
                        .sexe(studentClasse.getStudent().getSexe())
                        .birthDate(studentClasse.getStudent().getBirthDate())
                        .placeBirth(studentClasse.getStudent().getPlaceBirth())
                        .schoolingSystem(studentClasse.getSchoolingSystem())
                        .idClasse(studentClasse.getIdClasse())
                        .idStudentClasse(studentClasse.getId())
                        .repeatClasseActual(studentClasse.getStudent().getInfosStudent().getRepeatClasseActual())
                        .nbrRepeatClasse(studentClasse.getStudent().getInfosStudent().getNbrRepeatClasse())
                        .fatherProfession(studentClasse.getStudent().getInfosStudent().getFatherProfession())
                        .motherProfession(studentClasse.getStudent().getInfosStudent().getMotherProfession())
                        .tutorName(studentClasse.getStudent().getInfosStudent().getTutorName())
                        .tutorMobPhone(studentClasse.getStudent().getInfosStudent().getTutorMobPhone())
                        .tutorEmail(studentClasse.getStudent().getInfosStudent().getTutorEmail())
                        .dateStudentInscription(studentClasse.getDateStudentInscription())
                        .healthProblem(studentClasse.getStudent().getInfosStudent().getHealthProblem())
                        .classeTitle(studentClasse.getClasse().getTitle())
                        .isMotherOrphan(studentClasse.getStudent().getInfosStudent().getIsMotherOrphan())
                        .isFatherOrphan(studentClasse.getStudent().getInfosStudent().getIsFatherOrphan())
                        .isDisease(studentClasse.getStudent().getInfosStudent().getIsDisease())
                        .isMain(studentClasse.getStudent().getInfosStudent().getIsMain())
                        .isNeed(studentClasse.getStudent().getInfosStudent().getIsNeed()).build());

	}

	public List<Student> getAllStudentByClassId(Long id) {
		return studentRepository.getAllStudentByClassId(id);
	}

	public List<StudentDto> getAllByClasse(Long idClasse) {

		return studentRepository.getAllStudentByClassId(idClasse).stream()
				.map(student ->StudentDto.builder()
						.id(student.getId())
						.nbrRakmana(student.getNbrRakmana())
						.codeStudent(student.getCodeStudent())
						.firstName(student.getFirstName())
						.lastName(student.getLastName())
						.build()).toList();

	}
	public Page<StudentWithDesireDto> allStudentsWithDesire(Long idClasse, Integer page, int size) {
		Pageable pageable = PageRequest.of(Math.max(page, 0), size);
		Classe classe = classeRepository.findById(idClasse).orElseThrow();

		return studentRepository.allWithDesire(classe.getId(),classe.getIdYear(),pageable);
	}

	public StudentWithDesireDto oneStudentWithDesire(Long idStudent) {

		Year year = helperService.getCurrentYear();

		return studentRepository.oneWithDesire(year.getId(),idStudent);
	}

}
