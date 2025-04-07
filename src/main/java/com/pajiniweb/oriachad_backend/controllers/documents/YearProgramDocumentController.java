package com.pajiniweb.oriachad_backend.controllers.documents;

import com.pajiniweb.oriachad_backend.domains.dtos.documents.CurrentYearProgramDto;
import com.pajiniweb.oriachad_backend.domains.dtos.documents.DailyNotebookDto;
import com.pajiniweb.oriachad_backend.domains.dtos.documents.TechnicalCardDocumentDto;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.services.documents.NoteBookService;
import com.pajiniweb.oriachad_backend.services.documents.YearProgramDocumentService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/year-program/documents")
@AllArgsConstructor
public class YearProgramDocumentController {
	private static final Logger LOGGER = LoggerFactory.getLogger(YearProgramDocumentController.class);

	private final YearProgramDocumentService yearProgramDocumentService;


	@GetMapping("/current")
	public ResponseEntity<CurrentYearProgramDto> getCurrentYearProgramData() {
		LOGGER.info(Messages.START_FUNCTION, "getCurrentYearProgramData ");
		CurrentYearProgramDto currentYearProgramDto = yearProgramDocumentService.getCurrentYearProgramData();
		return new ResponseEntity<>(currentYearProgramDto, HttpStatus.OK);
	}

	@GetMapping("/technical-card/{idTechnicalCard}")
	public ResponseEntity<TechnicalCardDocumentDto> getTechnicalCardData(@PathVariable Long idTechnicalCard) {
		LOGGER.info(Messages.START_FUNCTION, "getCurrentYearProgramData ");
		TechnicalCardDocumentDto technicalCardDocumentDto = yearProgramDocumentService.getTechnicalCardData(idTechnicalCard);
		return new ResponseEntity<>(technicalCardDocumentDto, HttpStatus.OK);
	}
	@GetMapping("/technical-cards")
	public ResponseEntity<List<TechnicalCardDocumentDto>> getAllTechnicalCardData() {
		LOGGER.info(Messages.START_FUNCTION, "getAllTechnicalCardData ");
		List<TechnicalCardDocumentDto> technicalCardDocumentDtos = yearProgramDocumentService.getAllTechnicalCardData();
		return new ResponseEntity<>(technicalCardDocumentDtos, HttpStatus.OK);
	}

}
