package com.pajiniweb.oriachad_backend.services.imports;

import com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos.AddEditClasseDto;
import com.pajiniweb.oriachad_backend.domains.entities.*;
import com.pajiniweb.oriachad_backend.domains.enums.Gender;
import com.pajiniweb.oriachad_backend.domains.enums.StudyType;
import com.pajiniweb.oriachad_backend.domains.enums.TypeEstablishment;
import com.pajiniweb.oriachad_backend.repositories.*;
import com.pajiniweb.oriachad_backend.services.ClasseService;
import com.pajiniweb.oriachad_backend.services.HelperService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.ZoneId;

import static com.pajiniweb.oriachad_backend.services.StudentService.generateCodeStudent;

@Service
public class ExcelStudentService {

	private final ClasseRepository classeRepository;
	private final StudentRepository studentRepository;
	private final YearRepository yearRepository;
	private final LevelRepository levelRepository;
	private final HelperService helperService;
	private final ClasseService classeService;
	private final SpecialityRepository specialityRepository;
	private final StudentClasseRepository studentClasseRepository;
	private final InfosStudentRepository infosStudentRepository;

	@Autowired
	public ExcelStudentService(ClasseRepository classeRepository, StudentRepository studentRepository, YearRepository yearRepository, LevelRepository levelRepository, HelperService helperService, ClasseService classeService, SpecialityRepository specialityRepository, StudentClasseRepository studentClasseRepository, InfosStudentRepository infosStudentRepository) {
		this.classeRepository = classeRepository;
		this.studentRepository = studentRepository;
		this.yearRepository = yearRepository;
		this.levelRepository = levelRepository;
		this.helperService = helperService;
		this.classeService = classeService;
		this.specialityRepository = specialityRepository;
		this.studentClasseRepository = studentClasseRepository;
		this.infosStudentRepository = infosStudentRepository;
	}

	public ByteArrayInputStream generateExcelTemplate() throws IOException {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Students Template");
		Establishment establishment = helperService.getCurrentEstablishment();

		// Create header row
		Row headerRow = sheet.createRow(0);
		String[] headers = null;
		if (establishment.getType() == TypeEstablishment.secondary)
			headers = new String[]{"رقم التعريف", "اللقب", "الاسم", "الجنس", "تاريخ الازدياد", "مولود بحكم", "عقد الميلاد", "سنة التسجيل في سجل الولادات", "رقم عقد الميلاد", "مكان الازدياد", "السنة", "الشعبة", "القسم", "نظام التمدرس", "رقم القيد", "تاريخ التسجيل"};
		else
			headers = new String[]{"رقم التعريف", "اللقب", "الاسم", "الجنس", "تاريخ الازدياد", "مولود بحكم", "عقد الميلاد", "سنة التسجيل في سجل الولادات", "رقم عقد الميلاد", "مكان الازدياد", "السنة", "القسم", "نظام التمدرس", "رقم القيد", "تاريخ التسجيل"};

		for (int i = 0; i < headers.length; i++) {
			headerRow.createCell(i).setCellValue(headers[i]);
		}

		// Auto-size columns
		for (int i = 0; i < headers.length; i++) {
			sheet.autoSizeColumn(i);
		}

		// Convert workbook to ByteArrayInputStream
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		workbook.write(baos);
		workbook.close();

		return new ByteArrayInputStream(baos.toByteArray());
	}

	@Transactional(rollbackFor = Exception.class)
	public void importStudents(Long idYear, MultipartFile file) throws Exception {
		Workbook workbook = new XSSFWorkbook(file.getInputStream());
		Sheet sheet = workbook.getSheetAt(0);

		Year year = yearRepository.findById(idYear).orElseThrow();
		Establishment establishment = helperService.getCurrentEstablishment();

		for (int rowIdx = 1; rowIdx <= sheet.getLastRowNum() - 1; rowIdx++) {
			Row row = sheet.getRow(rowIdx);
			int colNum = 0;
			if (row != null) {
				String nbrRakmana = null;
				try {
					nbrRakmana = String.valueOf((long) row.getCell(colNum++).getNumericCellValue());
				} catch (Exception e) {
					throw new Exception(String.format("%s خاطئ للتلميذ رقم %s", " رقم التعريف للتميذ", rowIdx));
				}
				Student student = studentRepository.findByNbrRakmanaAndIdEstablishment(nbrRakmana, establishment.getId()).orElseGet(() -> Student.builder().establishment(establishment).build());
				boolean isNewStudent = student.getId() == null;
				student.setNbrRakmana(nbrRakmana);
				try {
					student.setLastName(row.getCell(colNum++).getStringCellValue());
				} catch (Exception e) {
					throw new Exception(String.format("%s خاطئ للتلميذ رقم %s", "اللقب التلميذ", rowIdx));
				}
				try {
					student.setFirstName(row.getCell(colNum++).getStringCellValue());
				} catch (Exception e) {
					throw new Exception(String.format("%s خاطئ للتلميذ رقم %s", "الاسم التلميذ", rowIdx));
				}
				try {
					student.setSexe(Gender.getGanderByString(row.getCell(colNum++).getStringCellValue()));
				} catch (Exception e) {
					throw new Exception(String.format("%s خاطئ للتلميذ رقم %s", " جنس التلميذ", rowIdx));
				}
				try {
					student.setBirthDate(row.getCell(colNum).getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
				} catch (Exception e) {
					throw new Exception(String.format("%s خاطئ للتلميذ رقم %s", " تاريخ الازدياد التلميذ", rowIdx));
				}
				colNum += 5;
				try {
					student.setPlaceBirth(row.getCell(colNum++).getStringCellValue());
				} catch (Exception e) {
					throw new Exception(String.format("%s خاطئ للتلميذ رقم %s", " مكان الازدياد التلميذ", rowIdx));
				}
				studentRepository.save(student);
				if (isNewStudent) {
					student.setCodeStudent(generateCodeStudent(student));
					studentRepository.save(student);
					infosStudentRepository.save(InfosStudent.builder().student(student).build());
				}
				String levelTitle = "";
				try {
					levelTitle = row.getCell(colNum++).getStringCellValue();
				} catch (Exception e) {
					throw new Exception(String.format("%s خاطئ للتلميذ رقم %s", " السنة التلميذ", rowIdx));
				}
				String finalLevelTitle = levelTitle;
				Level level = levelRepository.findByTitleAndType(levelTitle, establishment.getType()).orElseThrow(() -> new Exception("  لا يوجد مستوى بهذا الاسم : " + finalLevelTitle));
				Long idSpeciality;
				if (establishment.getType() == TypeEstablishment.secondary) {
					String specialityTitle = null;
					try {
						specialityTitle = row.getCell(colNum++).getStringCellValue();
					} catch (Exception e) {
						throw new Exception(String.format("%s خاطئ للتلميذ رقم %s", " تخصص التلميذ", rowIdx));
					}
					String finalSpecialityTitle = specialityTitle;
					idSpeciality = specialityRepository.getByTitleAndIdLevel(specialityTitle, level.getId()).orElseThrow(() -> new Exception("لا يوجد تخصص بهذا الاسم:" + finalSpecialityTitle + " في المستوى: " + finalLevelTitle)).getId();
				} else {
					idSpeciality = null;
				}
				int classNumber = -1;
				try {
					classNumber = (int) row.getCell(colNum++).getNumericCellValue();
				} catch (Exception e) {
					throw new Exception(String.format("%s خاطئ للتلميذ رقم %s", " القسم التلميذ", rowIdx));
				}
				int finalClassNumber = classNumber;
				Classe classe = classeRepository.getFirstByIdYearAndIdEstablishmentAndNumberAndIdLevelAndIdSpeciality(idYear, establishment.getId(), classNumber, level.getId(), idSpeciality).orElseGet(() -> classeService.save(AddEditClasseDto.builder().idLevel(level.getId()).idYear(year.getId()).idSpeciality(idSpeciality).number(finalClassNumber).build()));

				StudentClasse studentClasse = studentClasseRepository.findByIdStudentAndClasseIdYear(student.getId(), year.getId()).orElseGet(() -> StudentClasse.builder().build());
				studentClasse.setRemoved(false);
				studentClasse.setClasse(classe);
				studentClasse.setStudent(student);
				try {
					studentClasse.setSchoolingSystem(StudyType.getStudyTypeByString(row.getCell(colNum++).getStringCellValue()));
				} catch (Exception e) {
					throw new Exception(String.format("%s خاطئ للتلميذ رقم %s", " نظام التمدرس التلميذ", rowIdx));
				}
				try {
					studentClasse.setDateStudentInscription(row.getCell(++colNum).getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
				} catch (Exception e) {
					throw new Exception(String.format("%s خاطئ للتلميذ رقم %s", " تاريخ التسجيل التلميذ", rowIdx));
				}
				studentClasseRepository.save(studentClasse);
			}
		}

		workbook.close();
	}
}
