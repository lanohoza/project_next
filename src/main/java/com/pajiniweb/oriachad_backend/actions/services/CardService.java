package com.pajiniweb.oriachad_backend.actions.services;

import com.pajiniweb.oriachad_backend.actions.domains.dtos.ScriptCard;
import com.pajiniweb.oriachad_backend.actions.repositories.UserScriptRepository;
import com.pajiniweb.oriachad_backend.actions.scripts.core.TCScriptService;
import com.pajiniweb.oriachad_backend.domains.entities.Establishment;
import com.pajiniweb.oriachad_backend.domains.entities.OriachadUser;
import com.pajiniweb.oriachad_backend.domains.entities.Year;
import com.pajiniweb.oriachad_backend.repositories.YearRepository;
import com.pajiniweb.oriachad_backend.services.HelperService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CardService {
	final TCScriptService tcScriptService;
	final UserScriptRepository userScriptRepository;
	final HelperService helperService;
	private final YearRepository yearRepository;

	public List<ScriptCard> getStudentCardData(Long idStudent, Long idYear) {
		Year currentYear = yearRepository.findById(idYear).orElseThrow();
		OriachadUser user = helperService.getCurrentUser().getOriachadUser();

		return tcScriptService.getScripts().stream()
				.filter(scriptImplementation ->scriptImplementation.hasStudentCardData())
				.filter(scriptImplementation -> userScriptRepository.findByUserIdAndCode(user.getId(),currentYear.getId(),scriptImplementation.getCode()).isPresent())
				.map(scriptImplementation -> ScriptCard.builder().code(scriptImplementation.getCode()).data(scriptImplementation.getStudentCardData(idStudent,currentYear.getId()))
				.build())
				.toList();
	}
	public List<ScriptCard> getClasseCardData(Long idClasse, Long idYear) {
		Year currentYear = yearRepository.findById(idYear).orElseThrow();
		OriachadUser user = helperService.getCurrentUser().getOriachadUser();

		return tcScriptService.getScripts().stream()
				.filter(scriptImplementation ->scriptImplementation.hasClassCardData())
				.filter(scriptImplementation -> userScriptRepository.findByUserIdAndCode(user.getId(),currentYear.getId(),scriptImplementation.getCode()).isPresent())
				.map(scriptImplementation -> ScriptCard.builder().code(scriptImplementation.getCode()).data(scriptImplementation.getClassCardData(idClasse,currentYear.getId()))
						.build())
				.toList();
	}
	public List<ScriptCard> getSpecialtyCardData(Long idSpecialty,Long idLevel, Long idYear) {
		Year currentYear = yearRepository.findById(idYear).orElseThrow();
		OriachadUser user = helperService.getCurrentUser().getOriachadUser();

		return tcScriptService.getScripts().stream()
				.filter(scriptImplementation ->scriptImplementation.hasSpecialtyCardData())
				.filter(scriptImplementation -> userScriptRepository.findByUserIdAndCode(user.getId(),currentYear.getId(),scriptImplementation.getCode()).isPresent())
				.map(scriptImplementation -> ScriptCard.builder().code(scriptImplementation.getCode()).data(scriptImplementation.getSpecialityCardData(idSpecialty,idLevel,currentYear.getId()))
						.build())
				.toList();
	}
	public List<ScriptCard> getEstablishmentCardData(Long idYear) {
		Year currentYear = yearRepository.findById(idYear).orElseThrow();
		OriachadUser user = helperService.getCurrentUser().getOriachadUser();
		Establishment establishment= helperService.getCurrentEstablishment();
		return tcScriptService.getScripts().stream()
				.filter(scriptImplementation ->scriptImplementation.hasEstablishmentCardData())
				.filter(scriptImplementation -> userScriptRepository.findByUserIdAndCode(user.getId(),currentYear.getId(),scriptImplementation.getCode()).isPresent())
				.map(scriptImplementation -> ScriptCard.builder().code(scriptImplementation.getCode()).data(scriptImplementation.getEstablishmentCardData(establishment.getId(),currentYear.getId()))
						.build())
				.toList();
	}
	public List<ScriptCard> getSubjectCardData(Long idSubject, Long idYear) {
		Year currentYear = yearRepository.findById(idYear).orElseThrow();
		OriachadUser user = helperService.getCurrentUser().getOriachadUser();

		return tcScriptService.getScripts().stream()
				.filter(scriptImplementation ->scriptImplementation.hasSubjectCardData())
				.filter(scriptImplementation -> userScriptRepository.findByUserIdAndCode(user.getId(),currentYear.getId(),scriptImplementation.getCode()).isPresent())
				.map(scriptImplementation -> ScriptCard.builder().code(scriptImplementation.getCode()).data(scriptImplementation.getSubjectCardData(idSubject,currentYear.getId()))
						.build())
				.toList();
	}
	public List<ScriptCard> getLevelCardData(Long idLevel, Long idYear) {
		Year currentYear = yearRepository.findById(idYear).orElseThrow();
		OriachadUser user = helperService.getCurrentUser().getOriachadUser();

		return tcScriptService.getScripts().stream()
				.filter(scriptImplementation ->scriptImplementation.hasLevelCardData())
				.filter(scriptImplementation -> userScriptRepository.findByUserIdAndCode(user.getId(),currentYear.getId(),scriptImplementation.getCode()).isPresent())
				.map(scriptImplementation -> ScriptCard.builder().code(scriptImplementation.getCode()).data(scriptImplementation.getLevelCardData(idLevel,currentYear.getId()))
						.build())
				.toList();
	}
	public List<ScriptCard> getGuardianCardData(Long idStudent, Long idYear) {
		Year currentYear = yearRepository.findById(idYear).orElseThrow();
		OriachadUser user = helperService.getCurrentUser().getOriachadUser();

		return tcScriptService.getScripts().stream()
				.filter(scriptImplementation ->scriptImplementation.hasGuardianCardData())
				.filter(scriptImplementation -> userScriptRepository.findByUserIdAndCode(user.getId(),currentYear.getId(),scriptImplementation.getCode()).isPresent())
				.map(scriptImplementation -> ScriptCard.builder().code(scriptImplementation.getCode()).data(scriptImplementation.getGuardianCardData(idStudent,currentYear.getId()))
						.build())
				.toList();
	}
}
