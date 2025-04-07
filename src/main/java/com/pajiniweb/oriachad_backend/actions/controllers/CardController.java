package com.pajiniweb.oriachad_backend.actions.controllers;


import com.pajiniweb.oriachad_backend.actions.domains.dtos.ScriptCard;
import com.pajiniweb.oriachad_backend.actions.domains.dtos.TCE002.TCEM002StudentCardDto;
import com.pajiniweb.oriachad_backend.actions.services.CardService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cards")
@AllArgsConstructor
public class CardController {

	final CardService cardService;

	@GetMapping("/student/{idStudent}/{idYear}")
	public List<ScriptCard> getStudentCardData(@PathVariable Long  idStudent,@PathVariable Long idYear)  {
		return cardService.getStudentCardData(idStudent,idYear);
	}
	@GetMapping("/classe/{idClasse}/{idYear}")
	public List<ScriptCard> getClasseCardData(@PathVariable Long  idClasse,@PathVariable Long idYear)  {
		return cardService.getClasseCardData(idClasse,idYear);
	}
	@GetMapping("/specialty/{idSpecialty}/{idLevel}/{idYear}")
	public List<ScriptCard> getSpecialtyCardData(@PathVariable Long  idSpecialty,@PathVariable Long idLevel,@PathVariable Long idYear)  {
		return cardService.getSpecialtyCardData(idSpecialty,idLevel,idYear);
	}
	@GetMapping("/establishment/{idYear}")
	public List<ScriptCard> getEstablishmentCardData(@PathVariable Long idYear)  {
		return cardService.getEstablishmentCardData(idYear);
	}
	@GetMapping("/subject/{idSubject}/{idYear}")
	public List<ScriptCard> getSubjectCardData(@PathVariable Long  idSubject,@PathVariable Long idYear)  {
		return cardService.getSubjectCardData(idSubject,idYear);
	}
	@GetMapping("/level/{idLevel}/{idYear}")
	public List<ScriptCard> getLevelCardData(@PathVariable Long  idLevel,@PathVariable Long idYear)  {
		return cardService.getLevelCardData(idLevel,idYear);
	}
	@GetMapping("/guardian/{idGuardian}/{idYear}")
	public List<ScriptCard> getGuardianCardData(@PathVariable Long  idGuardian,@PathVariable Long idYear)  {
		return cardService.getGuardianCardData(idGuardian,idYear);
	}
}