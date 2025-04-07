package com.pajiniweb.oriachad_backend.controllers;

import com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos.AddEditNoteDto;
import com.pajiniweb.oriachad_backend.domains.dtos.NoteDto;
import com.pajiniweb.oriachad_backend.exceptions.BadRequestException;
import com.pajiniweb.oriachad_backend.exceptions.ResourceNotFoundException;
import com.pajiniweb.oriachad_backend.services.NoteService;
import com.pajiniweb.oriachad_backend.services.imports.ExcelNoteService;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;


@RestController
@RequestMapping("/api/v1/notes")
public class NoteController {
	private static final Logger LOGGER = LoggerFactory.getLogger(NoteController.class);

	private final NoteService noteService;
	private final ExcelNoteService excelNoteService;

	@Autowired
	public NoteController(NoteService noteService, ExcelNoteService excelNoteService) {
		this.noteService = noteService;
		this.excelNoteService = excelNoteService;
	}

	@GetMapping("/find/{idStudent}/{idTrimestre}")
	public ResponseEntity<NoteDto> findNoteByStudentAndTrimestre(@PathVariable Long idStudent, @PathVariable Long idTrimestre) {
		LOGGER.info("Start find Note By Student And Trimestre");
		try {
			NoteDto noteDto = noteService.findNoteByStudentAndTrimestre(idStudent, idTrimestre);
			return new ResponseEntity<>(noteDto, HttpStatus.OK);
		} catch (ResourceNotFoundException e) {
			LOGGER.error("Note not found", e);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			LOGGER.error("Error finding note by student and trimestre", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("save")
	public ResponseEntity<Boolean> saveNote(@RequestBody @Valid AddEditNoteDto addEditNoteDto) {
		LOGGER.info("Start createNote");
		try {
			boolean result = noteService.saveNote(addEditNoteDto);
			return new ResponseEntity<>(result, HttpStatus.CREATED);
		} catch (BadRequestException e) {
			LOGGER.error("Invalid input", e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			LOGGER.error("Error creating note", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{idStudent}/{idTrimestre}")
	public ResponseEntity<Boolean> updateNote(@PathVariable Long idStudent, @PathVariable Long idTrimestre, @RequestBody @Valid AddEditNoteDto addEditNoteDto) {
		LOGGER.info("Start updateNote");
		try {
			addEditNoteDto.setIdStudent(idStudent);
			addEditNoteDto.setIdTrimestre(idTrimestre);
			boolean result = noteService.updateNote(addEditNoteDto);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (ResourceNotFoundException e) {
			LOGGER.error("Note not found", e);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (BadRequestException e) {
			LOGGER.error("Invalid input", e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			LOGGER.error("Error updating note", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{idStudent}/{idTrimestre}")
	public ResponseEntity<Boolean> deleteNoteByStudentAndTrimestre(@PathVariable Long idStudent, @PathVariable Long idTrimestre) {
		LOGGER.info("Start deleteNoteByStudentAndTrimestre");
		try {
			boolean result = noteService.deleteNoteByStudentAndTrimestre(idStudent, idTrimestre);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (ResourceNotFoundException e) {
			LOGGER.error("Note not found", e);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			LOGGER.error("Error deleting note by student and trimestre", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}



    @PostMapping("/import/{idClasse}/{idTrimestre}")
    public ResponseEntity<Boolean> importNotes(@PathVariable Long idClasse, @PathVariable Long idTrimestre, @RequestParam("file") @NonNull MultipartFile file) throws IOException {
        excelNoteService.importNotes(idClasse, idTrimestre, file);
        return ResponseEntity.ok(true);
    }


	@GetMapping("/export/template")
	public ResponseEntity<byte[]> exportTemplate(@RequestParam(defaultValue = "-1") Long idClasse) throws Exception {
		byte[] content = excelNoteService.createExcelTemplate(idClasse); // idClasse should be provided accordingly


		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=notes-students-template.xlsx");
		headers.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

		return new ResponseEntity<>(content, headers, HttpStatus.OK);

	}
}
