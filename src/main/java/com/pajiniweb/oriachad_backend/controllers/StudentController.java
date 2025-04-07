package com.pajiniweb.oriachad_backend.controllers;

import com.pajiniweb.oriachad_backend.domains.dtos.*;
import com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos.AddEditStudentDto;
import com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos.AddNewClassStudentDto;
import com.pajiniweb.oriachad_backend.domains.dtos.CurrentStudentDto;
import com.pajiniweb.oriachad_backend.domains.dtos.StudentDto;
import com.pajiniweb.oriachad_backend.domains.dtos.StudentNoteDto;
import com.pajiniweb.oriachad_backend.domains.dtos.TechnicalCardDto;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.services.StudentService;
import com.pajiniweb.oriachad_backend.services.imports.ExcelStudentService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);

	private final StudentService studentService;
	private final ExcelStudentService excelStudentService;

	@Autowired
	public StudentController(StudentService studentService, ExcelStudentService excelStudentService) {
		this.studentService = studentService;
		this.excelStudentService = excelStudentService;
	}

	@GetMapping("/search")
	public ResponseEntity<Page<StudentDto>> searchStudents(@RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "-1") Long idClasse, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") int size) {
		LOGGER.info(Messages.START_FUNCTION, "searchStudents");
		Pageable pageable = PageRequest.of(page < 0 ? 0 : page, size);
		Page<StudentDto> students = studentService.searchStudents(search, idClasse, pageable);
		return new ResponseEntity<>(students, HttpStatus.OK);
	}
	@GetMapping("/current/search")
	public ResponseEntity<Page<StudentDto>> searchCurrentYearStudents(@RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") int size) {
		LOGGER.info(Messages.START_FUNCTION, "searchStudents");
		Pageable pageable = PageRequest.of(page < 0 ? 0 : page, size);
		Page<StudentDto> students = studentService.searchCurrentYearStudents(search, pageable);
		return new ResponseEntity<>(students, HttpStatus.OK);
	}
	@GetMapping("/by-classe/{idClasse}")
	public ResponseEntity<List<StudentDto>> getAllByClasse(@PathVariable  Long idClasse) {
		LOGGER.info(Messages.START_FUNCTION, "getAllByClasse");
		List<StudentDto> students = studentService.getAllByClasse(idClasse);
		return new ResponseEntity<>(students, HttpStatus.OK);
	}

	@GetMapping("/search-with-note")
	public ResponseEntity<Page<StudentNoteDto>> searchStudentsWithStatusByTrimestreAndClass(@RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "-1") Long IdClasse, @RequestParam(defaultValue = "-1") Long IdTrimestre, @RequestParam(defaultValue = "10") int size) {
		LOGGER.info(Messages.START_FUNCTION, "searchStudentsWithYearAndClass");
		try {
			Page<StudentNoteDto> studentDto = studentService.searchStudentsWithStatusByTrimestreAndClass(IdClasse, IdTrimestre, search, page, size);
			return new ResponseEntity<>(studentDto, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Error finding student by id", e);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/search-with-desires")
	public ResponseEntity<Page<StudentDesireDto>> searchStudentsWithDesire(@RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "-1") Long IdClasse, @RequestParam(defaultValue = "10") int size) {
		LOGGER.info(Messages.START_FUNCTION, "searchStudentsWithYearAndClass");
		try {
			Page<StudentDesireDto> studentDto = studentService.searchWithDesire(IdClasse, search, page, size);
			return new ResponseEntity<>(studentDto, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Error finding student by id", e);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@GetMapping("/all-with-desires/{idClasse}")
	public ResponseEntity<Page<StudentWithDesireDto>> allStudentsWithDesire(@PathVariable  Long idClasse,@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") int size) {
		LOGGER.info(Messages.START_FUNCTION, "searchStudentsWithYearAndClass");
		try {
			Page<StudentWithDesireDto> studentDto = studentService.allStudentsWithDesire(idClasse, page, size);
			return new ResponseEntity<>(studentDto, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Error finding student by id", e);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@GetMapping("/one-with-desires/{idStudent}")
	public ResponseEntity<StudentWithDesireDto> oneStudentsWithDesire(@PathVariable  Long idStudent) {
		LOGGER.info(Messages.START_FUNCTION, "oneStudentsWithDesire");
		try {
			StudentWithDesireDto studentDto = studentService.oneStudentWithDesire(idStudent);
			return new ResponseEntity<>(studentDto, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Error finding student by id", e);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/findById/{id}/{idClass}")
	public ResponseEntity<StudentDto> findById(@PathVariable Long id, @PathVariable Long idClass) {
		LOGGER.info(Messages.START_FUNCTION, "findById");
		try {
			StudentDto studentDto = studentService.findById(id,idClass);
			return new ResponseEntity<>(studentDto, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Error finding student by id", e);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/save")
	public ResponseEntity<Boolean> create(@RequestBody @Valid AddEditStudentDto addEditStudentDto) {
		LOGGER.info(Messages.START_FUNCTION, "create");
		try {
			boolean result = studentService.save(addEditStudentDto);
			return new ResponseEntity<>(result, HttpStatus.CREATED);
		} catch (Exception e) {
			LOGGER.error("Error creating student", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/save-to-new-class")
	public ResponseEntity<Boolean> saveToNewClass(@RequestBody @Valid AddNewClassStudentDto addNewClassStudentDto) {
		LOGGER.info(Messages.START_FUNCTION, "create");
		try {
			boolean result = studentService.saveToNewClass(addNewClassStudentDto);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Error creating student", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/change-class/{idStudent}/{idOldClasse}/{idNewClasse}")
	public ResponseEntity<Boolean> changeClass(@PathVariable Long idStudent, @PathVariable Long idOldClasse, @PathVariable Long idNewClasse) {
		LOGGER.info(Messages.START_FUNCTION, "create");
		try {
			boolean result = studentService.changeClass(idStudent, idOldClasse, idNewClasse);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Error creating student", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/update/{id}/{idClasse}")
	public ResponseEntity<Boolean> update(@PathVariable Long id, @PathVariable Long idClasse, @RequestBody AddEditStudentDto addEditStudentDto) {
		LOGGER.info(Messages.START_FUNCTION, "update");
		try {
			addEditStudentDto.setId(id);
			boolean result = studentService.update(addEditStudentDto, idClasse);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Error updating student", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
		LOGGER.info(Messages.START_FUNCTION, "deleteById");
		try {
			boolean result = studentService.deleteById(id);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Error deleting student", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@DeleteMapping("/remove/{id}/{idClass}")
	public ResponseEntity<Boolean> removeById(@PathVariable Long id, @PathVariable Long idClass) {
		LOGGER.info(Messages.START_FUNCTION, "deleteById");
		try {
			boolean result = studentService.removeById(id, idClass);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Error deleting student", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/import/{idYear}")
	public ResponseEntity<String> importStudents(@PathVariable Long idYear, @RequestParam("file") MultipartFile file) throws Exception {
		excelStudentService.importStudents(idYear, file);
		return ResponseEntity.ok("Students imported successfully.");
	}

	@GetMapping("/export/template")
	public ResponseEntity<byte[]> exportTemplate() throws Exception {
		ByteArrayInputStream bais = excelStudentService.generateExcelTemplate(); // idClasse should be provided accordingly

		byte[] content = bais.readAllBytes();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=students-template.xlsx");
		headers.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

		return new ResponseEntity<>(content, headers, HttpStatus.OK);

	}

	@GetMapping("/current-year")
	public ResponseEntity<List<CurrentStudentDto>> getStudentByCurrentYear() {
		LOGGER.info(Messages.START_FUNCTION, "getStudentByCurrentYear");
		List<CurrentStudentDto> result = studentService.getStudentByCurrentYear();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/removed-students")
	public Page<StudentDto> getRemovedStudent(@RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		return studentService.getRemovedStudent(search, page, size);
	}

	@PostMapping("/restore/{idStudentClass}")
	public Boolean restoreStudent(@PathVariable Long idStudentClass) throws Exception {
		studentService.restoreById(idStudentClass);
		return studentService.restoreById(idStudentClass);
	}


}
