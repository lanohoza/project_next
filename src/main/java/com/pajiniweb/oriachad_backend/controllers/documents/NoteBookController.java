package com.pajiniweb.oriachad_backend.controllers.documents;

import com.pajiniweb.oriachad_backend.domains.dtos.documents.DailyNotebookDto;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.services.documents.NoteBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notebooks")
public class NoteBookController {
	private static final Logger LOGGER = LoggerFactory.getLogger(NoteBookController.class);

	private final NoteBookService noteBookService;

	@Autowired
	public NoteBookController(NoteBookService noteBookService) {
		this.noteBookService = noteBookService;
	}


	@GetMapping("/daily/{day}")
	public ResponseEntity<DailyNotebookDto> getDailyNotebookData(@PathVariable String day) {
		LOGGER.info(Messages.START_FUNCTION, "daily Notebook");
		DailyNotebookDto dailyNotebookDto = noteBookService.getDailyNotebookData(day);
		return new ResponseEntity<>(dailyNotebookDto, HttpStatus.OK);
	}

}
