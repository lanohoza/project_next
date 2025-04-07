package com.pajiniweb.oriachad_backend.controllers;

import com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos.AddEditProfessorDto;
import com.pajiniweb.oriachad_backend.domains.dtos.ProfessorDto;
import com.pajiniweb.oriachad_backend.services.ProfessorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/professors")
@AllArgsConstructor
public class ProfessorController {

	final ProfessorService professorService;

	@GetMapping("/search")
	public ResponseEntity<Page<ProfessorDto>> searchProfessors(@RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page < 0 ? 0 : page, size);
		Page<ProfessorDto> students = professorService.searchProfessors(search, pageable);
		return new ResponseEntity<>(students, HttpStatus.OK);
	}


	@GetMapping("/findById/{id}")
	public ResponseEntity<ProfessorDto> findById(@PathVariable Long id) {

		ProfessorDto professorDto = professorService.findById(id);
		return new ResponseEntity<>(professorDto, HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<List<ProfessorDto>> getAll() {
		List<ProfessorDto> professorDtos = professorService.findAll();
		return new ResponseEntity<>(professorDtos, HttpStatus.OK);
	}

	@PostMapping("/save")
	public ResponseEntity<Boolean> create(@RequestBody @Valid AddEditProfessorDto addEditProfessorDto) {
		boolean result = professorService.save(addEditProfessorDto);
		return new ResponseEntity<>(result, HttpStatus.CREATED);

	}

	@PutMapping("/update/{idProfessor}")
	public boolean update(@PathVariable Long idProfessor, @RequestBody @Valid AddEditProfessorDto addEditProfessorDto) {
		return professorService.update(addEditProfessorDto, idProfessor);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Boolean> deleteById(@PathVariable Long id) throws Exception {
		boolean result = professorService.deleteById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);

	}
}