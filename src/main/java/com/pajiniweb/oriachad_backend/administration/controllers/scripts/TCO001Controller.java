package com.pajiniweb.oriachad_backend.administration.controllers.scripts;

import com.pajiniweb.oriachad_backend.administration.domains.dtos.scripts.TCO001.conditions.*;
import com.pajiniweb.oriachad_backend.administration.domains.dtos.scripts.TCO001.data.TCO001GuidanceSpecialityConfigDto;
import com.pajiniweb.oriachad_backend.administration.services.scripts.TCO001Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/scripts/settings/tco001")
public class TCO001Controller {

	@Autowired
	private TCO001Service tco001Service;

	/**
	 * student condition section
	 */
	@GetMapping("/student/condition/findById/{id}")
	public ResponseEntity<TCO001StudentConditionDto> getTCE002ConditionById(@PathVariable Long id) throws Exception {
		return ResponseEntity.ok(tco001Service.findStudentConditionById(id));
	}
	@PostMapping("/student/condition/create")
	public ResponseEntity<Boolean> createTCE002Condition(@RequestBody TCO001StudentConditionDto dto) {
		return ResponseEntity.ok(tco001Service.saveStudentCondition(dto));
	}
	@PutMapping("/student/condition/update/{id}")
	public ResponseEntity<Boolean> updateTCE002Condition(@PathVariable Long id, @RequestBody TCO001StudentConditionDto dto) {
		return ResponseEntity.ok(tco001Service.updateStudentCondition(id, dto));

	}
	@DeleteMapping("/student/condition/delete/{id}")
	public ResponseEntity<Boolean> deleteTCE002Condition(@PathVariable Long id) {
		return ResponseEntity.ok(tco001Service.deleteStudentConditionById(id));

	}
	@GetMapping("/student/condition/search")
	public ResponseEntity<Page<TCO001StudentConditionDto>> search(@RequestParam(name = "search", defaultValue = "") String search, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page < 0 ? 0 : page, size);
		return ResponseEntity.ok(tco001Service.searchStudentCondition(search, pageable));
	}


	/**
	 * Classe condition section
	 */
	@GetMapping("/classe/condition/findById/{id}")
	public ResponseEntity<TCO001ClasseConditionDto> getTCE002ClasseConditionById(@PathVariable Long id) throws Exception {
		return ResponseEntity.ok(tco001Service.findClasseConditionById(id));
	}

	@PostMapping("/classe/condition/create")
	public ResponseEntity<Boolean> createTCE002ClasseCondition(@RequestBody TCO001ClasseConditionDto dto) {
		return ResponseEntity.ok(tco001Service.saveClasseCondition(dto));
	}

	@PutMapping("/classe/condition/update/{id}")
	public ResponseEntity<Boolean> updateTCE002ClasseCondition(@PathVariable Long id, @RequestBody TCO001ClasseConditionDto dto) {
		return ResponseEntity.ok(tco001Service.updateClasseCondition(id, dto));
	}

	@DeleteMapping("/classe/condition/delete/{id}")
	public ResponseEntity<Boolean> deleteTCE002ClasseCondition(@PathVariable Long id) {
		return ResponseEntity.ok(tco001Service.deleteClasseConditionById(id));
	}

	@GetMapping("/classe/condition/search")
	public ResponseEntity<Page<TCO001ClasseConditionDto>> searchClasseConditions(@RequestParam(name = "search", defaultValue = "") String search,
																				 @RequestParam(defaultValue = "0") int page,
																				 @RequestParam(defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page < 0 ? 0 : page, size);
		return ResponseEntity.ok(tco001Service.searchClasseCondition(search, pageable));
	}


	/**
	 * subject condition section
	 */
	@GetMapping("/subject/condition/findById/{id}")
	public ResponseEntity<TCO001SubjectConditionDto> getTCE002SubjectConditionById(@PathVariable Long id) throws Exception {
		return ResponseEntity.ok(tco001Service.findSubjectConditionById(id));
	}

	@PostMapping("/subject/condition/create")
	public ResponseEntity<Boolean> createTCE002SubjectCondition(@RequestBody TCO001SubjectConditionDto dto) {
		return ResponseEntity.ok(tco001Service.saveSubjectCondition(dto));
	}

	@PutMapping("/subject/condition/update/{id}")
	public ResponseEntity<Boolean> updateTCE002SubjectCondition(@PathVariable Long id, @RequestBody TCO001SubjectConditionDto dto) {
		return ResponseEntity.ok(tco001Service.updateSubjectCondition(id, dto));
	}

	@DeleteMapping("/subject/condition/delete/{id}")
	public ResponseEntity<Boolean> deleteTCE002SubjectCondition(@PathVariable Long id) {
		return ResponseEntity.ok(tco001Service.deleteSubjectConditionById(id));
	}

	@GetMapping("/subject/condition/search")
	public ResponseEntity<Page<TCO001SubjectConditionDto>> searchSubjectConditions(@RequestParam(name = "search", defaultValue = "") String search,
																				 @RequestParam(defaultValue = "0") int page,
																				 @RequestParam(defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page < 0 ? 0 : page, size);
		return ResponseEntity.ok(tco001Service.searchSubjectCondition(search, pageable));
	}

	/**
	 * level condition section
	 */
	@GetMapping("/level/condition/findById/{id}")
	public ResponseEntity<TCO001LevelConditionDto> getTCE002LevelConditionById(@PathVariable Long id) throws Exception {
		return ResponseEntity.ok(tco001Service.findLevelConditionById(id));
	}

	@PostMapping("/level/condition/create")
	public ResponseEntity<Boolean> createTCE002LevelCondition(@RequestBody TCO001LevelConditionDto dto) {
		return ResponseEntity.ok(tco001Service.saveLevelCondition(dto));
	}

	@PutMapping("/level/condition/update/{id}")
	public ResponseEntity<Boolean> updateTCE002LevelCondition(@PathVariable Long id, @RequestBody TCO001LevelConditionDto dto) {
		return ResponseEntity.ok(tco001Service.updateLevelCondition(id, dto));
	}

	@DeleteMapping("/level/condition/delete/{id}")
	public ResponseEntity<Boolean> deleteTCE002LevelCondition(@PathVariable Long id) {
		return ResponseEntity.ok(tco001Service.deleteLevelConditionById(id));
	}

	@GetMapping("/level/condition/search")
	public ResponseEntity<Page<TCO001LevelConditionDto>> searchLevelConditions(@RequestParam(name = "search", defaultValue = "") String search,
																			   @RequestParam(defaultValue = "0") int page,
																			   @RequestParam(defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page < 0 ? 0 : page, size);
		return ResponseEntity.ok(tco001Service.searchLevelCondition(search, pageable));
	}

	/**
	 * speciality condition section
	 */
	@GetMapping("/speciality/condition/findById/{id}")
	public ResponseEntity<TCO001SpecialityConditionDto> getTCE002SpecialityConditionById(@PathVariable Long id) throws Exception {
		return ResponseEntity.ok(tco001Service.findSpecialityConditionById(id));
	}

	@PostMapping("/speciality/condition/create")
	public ResponseEntity<Boolean> createTCE002SpecialityCondition(@RequestBody TCO001SpecialityConditionDto dto) {
		return ResponseEntity.ok(tco001Service.saveSpecialityCondition(dto));
	}

	@PutMapping("/speciality/condition/update/{id}")
	public ResponseEntity<Boolean> updateTCE002SpecialityCondition(@PathVariable Long id, @RequestBody TCO001SpecialityConditionDto dto) {
		return ResponseEntity.ok(tco001Service.updateSpecialityCondition(id, dto));
	}

	@DeleteMapping("/speciality/condition/delete/{id}")
	public ResponseEntity<Boolean> deleteTCE002SpecialityCondition(@PathVariable Long id) {
		return ResponseEntity.ok(tco001Service.deleteSpecialityConditionById(id));
	}

	@GetMapping("/speciality/condition/search")
	public ResponseEntity<Page<TCO001SpecialityConditionDto>> searchSpecialityConditions(@RequestParam(name = "search", defaultValue = "") String search,
																						 @RequestParam(defaultValue = "0") int page,
																						 @RequestParam(defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page < 0 ? 0 : page, size);
		return ResponseEntity.ok(tco001Service.searchSpecialityCondition(search, pageable));
	}

	/**
	 * establishment condition section
	 */
	@GetMapping("/establishment/condition/findById/{id}")
	public ResponseEntity<TCO001EstablishmentConditionDto> getTCE002EstablishmentConditionById(@PathVariable Long id) throws Exception {
		return ResponseEntity.ok(tco001Service.findEstablishmentConditionById(id));
	}

	@PostMapping("/establishment/condition/create")
	public ResponseEntity<Boolean> createTCE002EstablishmentCondition(@RequestBody TCO001EstablishmentConditionDto dto) {
		return ResponseEntity.ok(tco001Service.saveEstablishmentCondition(dto));
	}

	@PutMapping("/establishment/condition/update/{id}")
	public ResponseEntity<Boolean> updateTCE002EstablishmentCondition(@PathVariable Long id, @RequestBody TCO001EstablishmentConditionDto dto) {
		return ResponseEntity.ok(tco001Service.updateEstablishmentCondition(id, dto));
	}

	@DeleteMapping("/establishment/condition/delete/{id}")
	public ResponseEntity<Boolean> deleteTCE002EstablishmentCondition(@PathVariable Long id) {
		return ResponseEntity.ok(tco001Service.deleteEstablishmentConditionById(id));
	}

	@GetMapping("/establishment/condition/search")
	public ResponseEntity<Page<TCO001EstablishmentConditionDto>> searchEstablishmentConditions(@RequestParam(name = "search", defaultValue = "") String search,
																						 @RequestParam(defaultValue = "0") int page,
																						 @RequestParam(defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page < 0 ? 0 : page, size);
		return ResponseEntity.ok(tco001Service.searchEstablishmentCondition(search, pageable));
	}


	/**
	 * Guidance Speciality Config  section
	 */
	@GetMapping("/guidanceSpecialityConfig/condition/findById/{id}")
	public ResponseEntity<TCO001GuidanceSpecialityConfigDto> getTCE002GuidanceSpecialityAveragesById(@PathVariable Long id) throws Exception {
		return ResponseEntity.ok(tco001Service.findGuidanceSpecialityConfigById(id));
	}

	@PostMapping("/guidanceSpecialityConfig/condition/create")
	public ResponseEntity<Boolean> createTCE002GuidanceSpecialityConfig(@RequestBody TCO001GuidanceSpecialityConfigDto dto) {
		return ResponseEntity.ok(tco001Service.saveGuidanceSpecialityConfig(dto));
	}

	@PutMapping("/guidanceSpecialityConfig/condition/update/{id}")
	public ResponseEntity<Boolean> updateTCE002GuidanceSpecialityConfig(@PathVariable Long id, @RequestBody TCO001GuidanceSpecialityConfigDto dto) {
		return ResponseEntity.ok(tco001Service.updateGuidanceSpecialityConfig(id, dto));
	}

	@DeleteMapping("/guidanceSpecialityConfig/condition/delete/{id}")
	public ResponseEntity<Boolean> deleteTCE002GuidanceSpecialityConfig(@PathVariable Long id) {
		return ResponseEntity.ok(tco001Service.deleteGuidanceSpecialityConfigById(id));
	}

	@GetMapping("/guidanceSpecialityConfig/condition/search")
	public ResponseEntity<Page<TCO001GuidanceSpecialityConfigDto>> searchGuidanceSpecialityConfigs(@RequestParam(name = "search", defaultValue = "") String search,
																							   @RequestParam(defaultValue = "0") int page,
																							   @RequestParam(defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page < 0 ? 0 : page, size);
		return ResponseEntity.ok(tco001Service.searchGuidanceSpecialityConfig(search, pageable));
	}
}
