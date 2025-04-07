package com.pajiniweb.oriachad_backend.services.imports;

import com.pajiniweb.oriachad_backend.domains.entities.*;
import com.pajiniweb.oriachad_backend.exceptions.ResourceNotFoundException;
import com.pajiniweb.oriachad_backend.repositories.*;
import com.pajiniweb.oriachad_backend.services.HelperService;
import lombok.AllArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ExcelNoteService {

	private final SubjectRepository subjectRepository;
	private final ClasseRepository classeRepository;
	private final StudentRepository studentRepository;
	private final TrimestreRepository trimestreRepository;
	private final ResultRepository resultRepository;
	private final AverageRepository averageRepository;
	private final RepeatStudentRepository repeatStudentRepository;
	private final SubjectLevelRepository subjectLevelRepository;
	private final HelperService helperService;


	public byte[] createExcelTemplate(Long idClasse) throws IOException {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Template");
		//	Classe classe = classeRepository.findById(idClasse).orElseThrow(() -> new RuntimeException("Class not found"));
		Establishment establishment = helperService.getCurrentEstablishment();
		// Create a header row
		sheet.createRow(0).createCell(0).setCellValue("الجمهورية الجزائرية الديمقراطية الشعبية");
		sheet.createRow(1).createCell(0).setCellValue("وزارة التربية الوطنية");
		sheet.createRow(2).createCell(0).setCellValue("مديرية التربية لولاية " + establishment.getCommune().getWilaya().getName());
		sheet.createRow(3).createCell(0).setCellValue(establishment.getName());
		sheet.createRow(4).createCell(0).setCellValue("تحليل النتائج التلاميذ  ");
		Row headerRow = sheet.createRow(5);

		// Assuming the uploaded file has specific headers
		String[] headers = {"الرقم", "اللقب و الاسم", "تاريخ الميلاد", "الجنس", "الإعادة", "اللغة العربية وآدابها ف 1", "اللغة العربية وآدابها ف 2", "اللغة العربية وآدابها ف 3", "الرياضيات ف 1", "الرياضيات ف 2", "الرياضيات ف 3", "العلوم الفيزيائية ف 1", "العلوم الفيزيائية ف 2", "العلوم الفيزيائية ف 3", "علوم الطبيعة والحياة ف 1", "علوم الطبيعة والحياة ف 2", "علوم الطبيعة والحياة ف 3", "العلوم الإسلامية ف 1", "العلوم الإسلامية ف 2", "العلوم الإسلامية ف 3", "التاريخ والجغرافيا ف 1", "التاريخ والجغرافيا ف 2", "التاريخ والجغرافيا ف 3", "اللغة الفرنسية ف 1", "اللغة الفرنسية ف 2", "اللغة الفرنسية ف 3", "اللغة الانجليزية ف 1", "اللغة الانجليزية ف 2", "اللغة الانجليزية ف 3", "المعلوماتية ف 1", "المعلوماتية ف 2", "المعلوماتية ف 3", "التربية الفنية ف 1", "التربية الفنية ف 2", "التربية الفنية ف 3", "ت البدنية و الرياضية ف 1", "ت البدنية و الرياضية ف 2", "ت البدنية و الرياضية ف 3", "اللغة اﻷمازيغية ف 1", "اللغة اﻷمازيغية ف 2", "اللغة اﻷمازيغية ف 3", "معدل الفصل 1", "معدل الفصل 2", "معدل الفصل 3"};
		for (int i = 0; i < headers.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(headers[i]);

			// You can set style for the header if needed
			CellStyle style = workbook.createCellStyle();
			Font font = workbook.createFont();
			font.setBold(true);
			style.setFont(font);
			cell.setCellStyle(style);
		}

		// Optionally, you can add some data rows if the template requires it
		// Row dataRow = sheet.createRow(1);
		// dataRow.createCell(0).setCellValue("Data1");

		// Adjust columns to fit the content
		for (int i = 0; i < headers.length; i++) {
			sheet.autoSizeColumn(i);
		}

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		workbook.write(outputStream);
		workbook.close();

		return outputStream.toByteArray();
	}

	@Transactional(rollbackFor = Exception.class)
	public boolean importNotes(Long idClasse, Long idTrimestre, MultipartFile file) throws IOException {
		Workbook workbook;
		try (InputStream is = file.getInputStream()) {
			if (file.getOriginalFilename().endsWith(".xlsx")) {
				workbook = new XSSFWorkbook(is);
			} else {
				throw new IllegalArgumentException("  xlsx الرجاء تصحويل صيغة الملف إلى ");
			}
		}
		Sheet sheet = workbook.getSheetAt(0);
		int numberOfTrimestre=1;
		if (sheet.getRow(5).getCell(6).getStringCellValue().contains(" ف 2")){
			 numberOfTrimestre=2;
		}
		if (sheet.getRow(5).getCell(7).getStringCellValue().contains(" ف 3")){
			numberOfTrimestre=3;
		}
		//sheet.getRow(5).getCell(6).getStringCellValue().replace(" ف 1", "").replace(" ف 2", "").replace(" ف 3", "")
		// Fetch class details
		Classe classe = classeRepository.findById(idClasse).orElseThrow(() -> new RuntimeException("هذا القسم غير موجود"));
		Trimestre trimestre = trimestreRepository.findById(idTrimestre).orElseThrow(() -> new RuntimeException(" هذا الفصل غير موجود "));

		// Fetch subjects from the database
		List<SubjectLevel> subjectLevels = subjectLevelRepository.findAllByIdSpecialityAndIdLevel(classe.getIdSpeciality(), classe.getIdLevel());

		for (int rowIdx = 6; rowIdx <= sheet.getLastRowNum() - 1; rowIdx++) {
			Row row = sheet.getRow(rowIdx);
			if (row != null) {
				String fullName = row.getCell(1).getStringCellValue();
				LocalDate BirthDate = row.getCell(2).getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				Student student = studentRepository.findByFullNameAndBirthDate(fullName, BirthDate, classe.getId()).orElseThrow(() -> new ResourceNotFoundException(String.format("لا يود تلميذ بمعلومات التالي : اللقب والاسم  :%s  تاريخ الميلاد : %s في هذا القسم", fullName, BirthDate)));
				List<Result> results = new LinkedList<>();
				for (int colIdx = 4 + numberOfTrimestre; colIdx < row.getLastCellNum() - numberOfTrimestre; colIdx += numberOfTrimestre)
				{
					Cell cell = row.getCell(colIdx);
					if (cell != null) {
						String subjectTitle = sheet.getRow(5).getCell(colIdx).getStringCellValue().replace(" ف 1", "").replace(" ف 2", "").replace(" ف 3", "");
						Optional<SubjectLevel> optionalSubject = subjectLevels.stream().filter(subjectLevel -> subjectLevel.getSubject().getTitle().equals(subjectTitle)).findFirst();
						if (optionalSubject.isPresent()) {
							SubjectLevel subjectLevel = optionalSubject.get();
							double value = cell.getNumericCellValue();
							Result result = resultRepository.findByStudentIdAndTrimestreIdAndIdSubjectLevel(student.getId(), trimestre.getId(), subjectLevel.getId()).orElseGet(() -> Result.builder().subjectLevel(subjectLevel).student(student).trimestre(trimestre).build());
							result.setValue(value);
							results.add(result);
						}
					}
				}
				Cell cell = null;
				if (numberOfTrimestre == 1)
					cell = row.getCell(row.getLastCellNum() - 1);
				else
					cell = row.getCell(row.getLastCellNum() - 1 - numberOfTrimestre + trimestre.getNumber());
				if (cell != null) {
					double averageValue = cell.getNumericCellValue();
					Average average = averageRepository.findByIdStudentAndIdTrimestre(student.getId(), trimestre.getId()).orElseGet(() -> Average.builder().student(student).trimestre(trimestre).build());
					average.setValue(averageValue);
					averageRepository.save(average);
				}

				resultRepository.saveAll(results);
			}
		}

		workbook.close();
		return true;
	}
}
